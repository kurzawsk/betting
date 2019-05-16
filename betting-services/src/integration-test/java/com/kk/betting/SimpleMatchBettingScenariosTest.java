package com.kk.betting;

import com.kk.betting.domain.*;
import com.kk.betting.dto.MatchBetProposalRawDTO;
import com.kk.betting.dto.MatchFinishEventDTO;
import com.kk.betting.services.admin.action.AdminOperationInvokerRemote;
import com.kk.betting.services.bettorhandling.common.action.TimedBetOddSourceInvoker;
import com.kk.betting.services.bettorhandling.common.service.RawBettingProposalSender;
import com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic;
import com.kk.betting.services.bettorhandling.common.service.bettorlogic.SupportedOddRangesProvider;
import com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider;
import com.kk.betting.services.bettorhandling.pinnacledummy.service.ProposeAllPinnacleMatchesLogic;
import com.kk.betting.services.common.service.ObjectMapperFactory;
import com.kk.betting.services.common.util.CommonConstants;
import com.kk.betting.services.matchdatacollection.service.MatchFinishedEventSender;
import com.kk.betting.test.util.DeploymentGenerator;
import com.kk.betting.test.util.RollbackableEntityPersister;
import com.kk.betting.test.util.TimedValidator;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.Map;

/**
 * Created by KK on 2017-05-26.
 */
//TODO refactor duplicated validators to a class
@RunWith(Arquillian.class)
public class SimpleMatchBettingScenariosTest {

    private static final Log log = LogFactory.getLog(SimpleMatchBettingScenariosTest.class);

    @Deployment
    public static Archive createDeployment() throws IOException {
        return DeploymentGenerator.createDeployment();
    }

    @PersistenceContext(unitName = CommonConstants.PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;

    @EJB
    private AdminOperationInvokerRemote adminOperationInvokerRemote;

    @Inject
    private RawBettingProposalSender rawBettingProposalSender;

    @Inject
    private MatchFinishedEventSender matchFinishedEventSender;

    @Inject
    private TimedBetOddSourceInvoker timedBetOddSourceInvoker;

    @Inject
    private RollbackableEntityPersister persister;

    @Inject
    private UserTransaction utx;

    @Test
    public void testMatchBet() throws Exception {
        Team t1 = new Team("t1");
        Team t2 = new Team("t2");
        Match match = new Match("TestMatch", t1, t2, LocalDateTime.now().minusHours(4));
        Bookmaker bookmaker = new Bookmaker("testBookmaker", "testBookmaker");
        Bettor bettor = new Bettor();
        bettor.addToResources(new BigDecimal("100"));
        bettor.setDescription("Test instance of bettor");
        bettor.setBookmaker(bookmaker);
        bettor.setActive(true);
        bettor.setLogicParameters(getDummyBettorLogicParams());

        BigDecimal odd = new BigDecimal("1.25");
        MatchOdd matchOdd = new MatchOdd();
        matchOdd.setOdd1(odd);
        matchOdd.setOddX(odd);
        matchOdd.setOdd2(odd);
        matchOdd.setBookmaker(bookmaker);
        matchOdd.setMatch(match);
        matchOdd.setTimestamp(LocalDateTime.now().minusMinutes(14));

        try {
            utx.begin();
            entityManager.joinTransaction();
            persister.persistAndScheduleForRemoval(t1);
            persister.persistAndScheduleForRemoval(t2);
            persister.persistAndScheduleForRemoval(match);
            persister.persistAndScheduleForRemoval(bookmaker);
            persister.persistAndScheduleForRemoval(bettor);
            persister.persistAndScheduleForRemoval(matchOdd);
            utx.commit();


            MatchBetProposalRawDTO dto = MatchBetProposalRawDTO.builder().setMatchId(match.getId())
                    .setExpectedMatchResult(MatchOdd.Type.ODD1.toString()).setOdd(odd).setProposalSource(ProposeAllPinnacleMatchesLogic.class.getSimpleName()).build();
            rawBettingProposalSender.sendMessage(dto);

            TimedValidator bettorResourcesValidatorAfterBetDone = new TimedValidator(10, 1000, () -> {
                Bettor freshBettor = null;
                int noOfBettingEvents;
                try {
                    utx.begin();
                    entityManager.joinTransaction();
                    freshBettor = entityManager.find(Bettor.class, bettor.getId());

                    noOfBettingEvents = freshBettor.getBettingEvents().size();
                    utx.commit();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                Assert.assertEquals("Expected bettor resources after booking a match are different from expected.", new BigDecimal("90.00"), freshBettor.getAvailableResources());
                Assert.assertEquals("Number of expected betting events is different from obtained", 1, noOfBettingEvents);


            });

            bettorResourcesValidatorAfterBetDone.validate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            utx.begin();
            entityManager.joinTransaction();
            persister.removeScheduledEntities();
            utx.commit();
        }
    }

    @Test
    public void testMatchBetAndFinishedSuccessful() throws Exception {
        Team t1 = new Team("t1");
        Team t2 = new Team("t2");
        Match match = new Match("TestMatch", t1, t2, LocalDateTime.now().minusHours(4));
        Bookmaker bookmaker = new Bookmaker("testBookmaker", "testBookmaker");
        Bettor bettor = new Bettor();
        bettor.addToResources(new BigDecimal("100"));
        bettor.setDescription("Test instance of bettor");
        bettor.setBookmaker(bookmaker);
        bettor.setActive(true);
        bettor.setLogicParameters(getDummyBettorLogicParams());

        BigDecimal odd = new BigDecimal("1.25");
        MatchOdd matchOdd = new MatchOdd();
        matchOdd.setOdd1(odd);
        matchOdd.setOddX(odd);
        matchOdd.setOdd2(odd);
        matchOdd.setBookmaker(bookmaker);
        matchOdd.setMatch(match);
        matchOdd.setTimestamp(LocalDateTime.now().minusMinutes(14));

        try {
            utx.begin();
            entityManager.joinTransaction();
            persister.persistAndScheduleForRemoval(t1);
            persister.persistAndScheduleForRemoval(t2);
            persister.persistAndScheduleForRemoval(match);
            persister.persistAndScheduleForRemoval(bookmaker);
            persister.persistAndScheduleForRemoval(bettor);
            persister.persistAndScheduleForRemoval(matchOdd);
            utx.commit();

            MatchBetProposalRawDTO dto = MatchBetProposalRawDTO.builder().setMatchId(match.getId()).setExpectedMatchResult(MatchOdd.Type.ODD1.toString())
                    .setOdd(odd).setProposalSource(ProposeAllPinnacleMatchesLogic.class.getSimpleName()).build();
            rawBettingProposalSender.sendMessage(dto);

            TimedValidator bettorResourcesValidatorAfterBetDone = new TimedValidator(10, 1000, () -> {
                Bettor freshBettor = null;
                int noOfBettingEvents;
                try {
                    utx.begin();
                    entityManager.joinTransaction();
                    freshBettor = entityManager.find(Bettor.class, bettor.getId());
                    noOfBettingEvents = freshBettor.getBettingEvents().size();
                    utx.commit();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                Assert.assertEquals("Expected bettor resources after booking a match are different from expected.", new BigDecimal("90.00"), freshBettor.getAvailableResources());
                Assert.assertEquals("Number of expected betting events is different from obtained", 1, noOfBettingEvents);
            });

            bettorResourcesValidatorAfterBetDone.validate();

            MatchFinishEventDTO matchFinishedDto = new MatchFinishEventDTO(match.getId(), "2:1");
            matchFinishedEventSender.sendMessage(matchFinishedDto);
            TimedValidator bettorResourcesValidatorAfterMatchFinished = new TimedValidator(30, 1000, () -> {
                Bettor freshBettor = null;
                int noOfBettingEvents;
                try {
                    utx.begin();
                    entityManager.joinTransaction();
                    freshBettor = entityManager.find(Bettor.class, bettor.getId());
                    noOfBettingEvents = freshBettor.getBettingEvents().size();
                    utx.commit();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                Assert.assertEquals("Expected bettor resources after a match is successfully finished are different from expected.", new BigDecimal("102.50"), freshBettor.getAvailableResources());
                Assert.assertEquals("Number of expected betting events is different from obtained", 2, noOfBettingEvents);
            });

            bettorResourcesValidatorAfterMatchFinished.validate();

        } finally {
            utx.begin();
            entityManager.joinTransaction();
            persister.removeScheduledEntities();
            utx.commit();
        }
    }

    @Test
    public void testMatchBetAndFinishedUnsuccessful() throws Exception {
        Team t1 = new Team("t1");
        Team t2 = new Team("t2");
        Match match = new Match("TestMatch", t1, t2, LocalDateTime.now().minusHours(4));
        Bookmaker bookmaker = new Bookmaker("testBookmaker", "testBookmaker");
        Bettor bettor = new Bettor();
        bettor.addToResources(new BigDecimal("100"));
        bettor.setDescription("Test instance of bettor");
        bettor.setBookmaker(bookmaker);
        bettor.setActive(true);
        bettor.setLogicParameters(getDummyBettorLogicParams());

        BigDecimal odd = new BigDecimal("1.25");
        MatchOdd matchOdd = new MatchOdd();
        matchOdd.setOdd1(odd);
        matchOdd.setOddX(odd);
        matchOdd.setOdd2(odd);
        matchOdd.setOdd1X(odd);
        matchOdd.setBookmaker(bookmaker);
        matchOdd.setMatch(match);
        matchOdd.setTimestamp(LocalDateTime.now().minusMinutes(14));

        try {
            utx.begin();
            entityManager.joinTransaction();
            persister.persistAndScheduleForRemoval(t1);
            persister.persistAndScheduleForRemoval(t2);
            persister.persistAndScheduleForRemoval(match);
            persister.persistAndScheduleForRemoval(bookmaker);
            persister.persistAndScheduleForRemoval(bettor);
            persister.persistAndScheduleForRemoval(matchOdd);
            utx.commit();

            MatchBetProposalRawDTO dto = MatchBetProposalRawDTO.builder().setMatchId(match.getId()).setExpectedMatchResult(MatchOdd.Type.ODD1X.toString())
                    .setOdd(odd).setProposalSource(ProposeAllPinnacleMatchesLogic.class.getSimpleName()).build();
            rawBettingProposalSender.sendMessage(dto);

            TimedValidator bettorResourcesValidatorAfterBetDone = new TimedValidator(10, 1000, () -> {
                Bettor freshBettor = null;
                int noOfBettingEvents;
                try {
                    utx.begin();
                    entityManager.joinTransaction();
                    freshBettor = entityManager.find(Bettor.class, bettor.getId());
                    noOfBettingEvents = freshBettor.getBettingEvents().size();
                    utx.commit();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                Assert.assertEquals("Expected bettor resources after booking a match are different from expected.", new BigDecimal("90.00"), freshBettor.getAvailableResources());
                Assert.assertEquals("Number of expected betting events is different from obtained", 1, noOfBettingEvents);
            });

            bettorResourcesValidatorAfterBetDone.validate();

            MatchFinishEventDTO matchFinishedDto = new MatchFinishEventDTO(match.getId(), "2:3");
            matchFinishedEventSender.sendMessage(matchFinishedDto);
            TimedValidator bettorResourcesValidatorAfterMatchFinished = new TimedValidator(30, 1000, () -> {
                Bettor freshBettor = null;
                int noOfBettingEvents;
                try {
                    utx.begin();
                    entityManager.joinTransaction();
                    freshBettor = entityManager.find(Bettor.class, bettor.getId());
                    noOfBettingEvents = freshBettor.getBettingEvents().size();
                    utx.commit();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                Assert.assertEquals("Expected bettor resources after a match is successfully finished are different from expected.", new BigDecimal("90.00"), freshBettor.getAvailableResources());
                Assert.assertEquals("Number of expected betting events is different from obtained", 2, noOfBettingEvents);
            });

            bettorResourcesValidatorAfterMatchFinished.validate();

        } catch (Exception e) {
            log.error(e);
        } finally {
            utx.begin();
            entityManager.joinTransaction();
            persister.removeScheduledEntities();
            utx.commit();
        }
    }

    private Map<String, Object> getDummyBettorLogicParams() {
        BigDecimal _10Percent = new BigDecimal("0.1");
        ParametrizedBettorLogic logic = new ParametrizedBettorLogic();
        ResourcesPartProvider riskAmountProvider = new ResourcesPartProvider();
        riskAmountProvider.setPart(_10Percent);
        SupportedOddRangesProvider supportedOddRangesProvider = new SupportedOddRangesProvider();
        supportedOddRangesProvider.setLowerLimit(BigDecimal.ONE);
        supportedOddRangesProvider.setUpperLimit(new BigDecimal("1000000.00"));
        logic.setRiskAmountProvider(riskAmountProvider);
        logic.setSupportedOddRangesProvider(supportedOddRangesProvider);
        logic.setSupportedOddTypes(EnumSet.allOf(MatchOdd.Type.class));
        logic.setSupportedProposalsSources(Arrays.asList(ProposeAllPinnacleMatchesLogic.class.getSimpleName()));

        return ObjectMapperFactory.get().convertValue(logic, Map.class);
    }

}
