package com.kk.betting;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.kk.betting.domain.*;
import com.kk.betting.dto.MatchBetProposalRawDTO;
import com.kk.betting.services.bettorhandling.common.service.RawBettingProposalSender;
import com.kk.betting.services.bettorhandling.common.service.bettorlogic.ParametrizedBettorLogic;
import com.kk.betting.services.bettorhandling.common.service.bettorlogic.SupportedOddRangesProvider;
import com.kk.betting.services.bettorhandling.common.service.riskamountselection.ResourcesPartProvider;
import com.kk.betting.services.bettorhandling.pinnacledummy.service.ProposeAllPinnacleMatchesLogic;
import com.kk.betting.services.common.dao.BettorDao;
import com.kk.betting.services.common.dao.BookmakerDao;
import com.kk.betting.services.common.service.ObjectMapperFactory;
import com.kk.betting.services.common.util.CommonConstants;
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

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by KK on 2017-06-13.
 */
@RunWith(Arquillian.class)
public class ComplexMatchBettingScenariosTest {

    private static final Log log = LogFactory.getLog(ComplexMatchBettingScenariosTest.class);
    private static final String PINNACLE_BOOKMAKER_URL = "http://affiliates.pinnaclesports.com";

    @Deployment
    public static Archive createDeployment() throws IOException {
        return DeploymentGenerator.createDeployment();
    }

    @PersistenceContext(unitName = CommonConstants.PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;

    @Inject
    private BettorDao bettorDao;

    @Inject
    private BookmakerDao bookmakerDao;

    @Inject
    private UserTransaction utx;

    @Inject
    private RawBettingProposalSender rawBettingProposalSender;

    @Inject
    private RollbackableEntityPersister persister;

    @Test
    public void testCreateBettorsBasic() throws Exception {
        Map<BigDecimal, BigDecimal> limits = Maps.newHashMap();
        Bookmaker targetBookmaker = bookmakerDao.findAll().stream().filter(b -> PINNACLE_BOOKMAKER_URL.equals(b.getWebPageUrl())).findFirst().get();

        try {
            limits.put(new BigDecimal("1.00"), new BigDecimal("1.15"));
            limits.put(new BigDecimal("1.00"), new BigDecimal("1.25"));
            limits.put(new BigDecimal("1.25"), new BigDecimal("1.50"));
            limits.put(new BigDecimal("1.50"), new BigDecimal("1.75"));
            limits.put(new BigDecimal("1.75"), new BigDecimal("2.00"));
            limits.put(new BigDecimal("2.00"), new BigDecimal("2.50"));

            List<EnumSet<MatchOdd.Type>> supportedOddTypes = Lists.newArrayList();
            supportedOddTypes.add(Sets.newEnumSet(Lists.newArrayList(MatchOdd.Type.ODD1, MatchOdd.Type.ODDX, MatchOdd.Type.ODD2), MatchOdd.Type.class));

            List<BigDecimal> riskParts = Lists.newArrayList(new BigDecimal("0.05"));

            createBettors(limits, supportedOddTypes, riskParts, targetBookmaker);

            utx.begin();
            entityManager.joinTransaction();
            List<? extends Bettor> bettors = bettorDao.findAll();
            System.out.println("bettors.size = " + bettors.size());
            utx.commit();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            utx.begin();
        }
    }

    @Test
    public void testCreateBettors() throws Exception {

        Bookmaker targetBookmaker = bookmakerDao.findAll().stream().filter(b -> PINNACLE_BOOKMAKER_URL.equals(b.getWebPageUrl())).findFirst().get();

        try {
            Map<BigDecimal, BigDecimal> limits = Maps.newHashMap();

            limits.put(new BigDecimal("1.00"), new BigDecimal("1.50"));
            limits.put(new BigDecimal("1.50"), new BigDecimal("2.00"));
            limits.put(new BigDecimal("2.00"), new BigDecimal("2.50"));

            limits.put(new BigDecimal("1.00"), new BigDecimal("1.25"));
            limits.put(new BigDecimal("1.25"), new BigDecimal("1.50"));
            limits.put(new BigDecimal("1.50"), new BigDecimal("1.75"));
            limits.put(new BigDecimal("1.75"), new BigDecimal("2.00"));
            limits.put(new BigDecimal("2.00"), new BigDecimal("2.25"));
            limits.put(new BigDecimal("2.25"), new BigDecimal("2.50"));
            limits.put(new BigDecimal("2.50"), new BigDecimal("2.75"));
            limits.put(new BigDecimal("2.75"), new BigDecimal("3.00"));
            limits.put(new BigDecimal("3.00"), new BigDecimal("3.25"));
            limits.put(new BigDecimal("3.25"), new BigDecimal("3.50"));
            limits.put(new BigDecimal("3.50"), new BigDecimal("3.75"));
            limits.put(new BigDecimal("3.75"), new BigDecimal("4.00"));
            limits.put(new BigDecimal("4.00"), new BigDecimal("4.25"));
            limits.put(new BigDecimal("4.25"), new BigDecimal("4.50"));
            limits.put(new BigDecimal("5.50"), new BigDecimal("5.00"));
            limits.put(new BigDecimal("5.50"), new BigDecimal("6.00"));
            limits.put(new BigDecimal("6.50"), new BigDecimal("7.00"));
            limits.put(new BigDecimal("7.00"), new BigDecimal("8.00"));
            limits.put(new BigDecimal("8.00"), new BigDecimal("9.00"));
            limits.put(new BigDecimal("9.00"), new BigDecimal("10.00"));
            limits.put(new BigDecimal("10.00"), new BigDecimal("20.00"));

            List<EnumSet<MatchOdd.Type>> supportedOddTypes = Lists.newArrayList();
            supportedOddTypes.add(Sets.newEnumSet(Lists.newArrayList(MatchOdd.Type.ODD1), MatchOdd.Type.class));
            supportedOddTypes.add(Sets.newEnumSet(Lists.newArrayList(MatchOdd.Type.ODDX), MatchOdd.Type.class));
            supportedOddTypes.add(Sets.newEnumSet(Lists.newArrayList(MatchOdd.Type.ODD2), MatchOdd.Type.class));
            supportedOddTypes.add(Sets.newEnumSet(Lists.newArrayList(MatchOdd.Type.ODD12), MatchOdd.Type.class));
            supportedOddTypes.add(Sets.newEnumSet(Lists.newArrayList(MatchOdd.Type.ODDX2), MatchOdd.Type.class));
            supportedOddTypes.add(Sets.newEnumSet(Lists.newArrayList(MatchOdd.Type.ODD1X), MatchOdd.Type.class));

            supportedOddTypes.add(Sets.newEnumSet(Lists.newArrayList(MatchOdd.Type.ODD1, MatchOdd.Type.ODDX, MatchOdd.Type.ODD2), MatchOdd.Type.class));
            supportedOddTypes.add(Sets.newEnumSet(Lists.newArrayList(MatchOdd.Type.ODD1, MatchOdd.Type.ODDX), MatchOdd.Type.class));
            supportedOddTypes.add(Sets.newEnumSet(Lists.newArrayList(MatchOdd.Type.ODDX, MatchOdd.Type.ODD2), MatchOdd.Type.class));

            supportedOddTypes.add(Sets.newEnumSet(Lists.newArrayList(MatchOdd.Type.ODDBTSN, MatchOdd.Type.ODDBTSY), MatchOdd.Type.class));
            supportedOddTypes.add(Sets.newEnumSet(Lists.newArrayList(MatchOdd.Type.ODDBTSN), MatchOdd.Type.class));
            supportedOddTypes.add(Sets.newEnumSet(Lists.newArrayList(MatchOdd.Type.ODDBTSY), MatchOdd.Type.class));

            supportedOddTypes.add(Sets.newEnumSet(Lists.newArrayList(MatchOdd.Type.ODDO05), MatchOdd.Type.class));
            supportedOddTypes.add(Sets.newEnumSet(Lists.newArrayList(MatchOdd.Type.ODDU05), MatchOdd.Type.class));

            supportedOddTypes.add(Sets.newEnumSet(Lists.newArrayList(MatchOdd.Type.ODDO15), MatchOdd.Type.class));
            supportedOddTypes.add(Sets.newEnumSet(Lists.newArrayList(MatchOdd.Type.ODDU15), MatchOdd.Type.class));

            supportedOddTypes.add(Sets.newEnumSet(Lists.newArrayList(MatchOdd.Type.ODDO25), MatchOdd.Type.class));
            supportedOddTypes.add(Sets.newEnumSet(Lists.newArrayList(MatchOdd.Type.ODDU25), MatchOdd.Type.class));

            supportedOddTypes.add(Sets.newEnumSet(Lists.newArrayList(MatchOdd.Type.ODDO35), MatchOdd.Type.class));
            supportedOddTypes.add(Sets.newEnumSet(Lists.newArrayList(MatchOdd.Type.ODDU35), MatchOdd.Type.class));

            supportedOddTypes.add(Sets.newEnumSet(Lists.newArrayList(MatchOdd.Type.ODDO45), MatchOdd.Type.class));
            supportedOddTypes.add(Sets.newEnumSet(Lists.newArrayList(MatchOdd.Type.ODDU45), MatchOdd.Type.class));

            supportedOddTypes.add(Sets.newEnumSet(Lists.newArrayList(MatchOdd.Type.ODDO55), MatchOdd.Type.class));
            supportedOddTypes.add(Sets.newEnumSet(Lists.newArrayList(MatchOdd.Type.ODDU55), MatchOdd.Type.class));

            supportedOddTypes.add(Sets.newEnumSet(Lists.newArrayList(MatchOdd.Type.ODDO65), MatchOdd.Type.class));
            supportedOddTypes.add(Sets.newEnumSet(Lists.newArrayList(MatchOdd.Type.ODDU65), MatchOdd.Type.class));


            supportedOddTypes.add(Sets.newEnumSet(Lists.newArrayList(MatchOdd.Type.ODDO05, MatchOdd.Type.ODDU05), MatchOdd.Type.class));
            supportedOddTypes.add(Sets.newEnumSet(Lists.newArrayList(MatchOdd.Type.ODDO15, MatchOdd.Type.ODDU15), MatchOdd.Type.class));
            supportedOddTypes.add(Sets.newEnumSet(Lists.newArrayList(MatchOdd.Type.ODDO25, MatchOdd.Type.ODDU25), MatchOdd.Type.class));

            supportedOddTypes.add(Sets.newEnumSet(Lists.newArrayList(MatchOdd.Type.ODDO05, MatchOdd.Type.ODDU05, MatchOdd.Type.ODDO15, MatchOdd.Type.ODDU15), MatchOdd.Type.class));
            supportedOddTypes.add(Sets.newEnumSet(Lists.newArrayList(MatchOdd.Type.ODDO15, MatchOdd.Type.ODDU15, MatchOdd.Type.ODDO25, MatchOdd.Type.ODDU25), MatchOdd.Type.class));
            supportedOddTypes.add(Sets.newEnumSet(Lists.newArrayList(MatchOdd.Type.ODDO05, MatchOdd.Type.ODDU05, MatchOdd.Type.ODDO15, MatchOdd.Type.ODDU15, MatchOdd.Type.ODDO25, MatchOdd.Type.ODDU25), MatchOdd.Type.class));

            List<BigDecimal> riskParts = Lists.newArrayList(new BigDecimal("0.025"), new BigDecimal("0.05"), new BigDecimal("0.08"), new BigDecimal("0.12"));

            createBettors(limits, supportedOddTypes, riskParts, targetBookmaker);

            utx.begin();
            entityManager.joinTransaction();

            List<? extends Bettor> bettors = bettorDao.findAll();
            System.out.println("bettors.size = " + bettors.size());
            utx.commit();


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            utx.begin();
        }
    }


    @Test
    public void testBetMatchMultipleTimes() throws Exception {

        try {
            Team t1 = new Team("t1");
            Team t2 = new Team("t2");
            Match match = new Match("TestMatch", t1, t2, LocalDateTime.now().plusHours(44));

            Team t3 = new Team("t3");
            Team t4 = new Team("t4");
            Match match2 = new Match("TestMatch2", t1, t2, LocalDateTime.now().plusHours(54));

            Bookmaker bookmaker = new Bookmaker("testBookmaker", "testBookmaker");

            Bettor bettor = new Bettor();
            ParametrizedBettorLogic logic = new ParametrizedBettorLogic();
            ResourcesPartProvider riskAmountProvider = new ResourcesPartProvider();
            riskAmountProvider.setPart(new BigDecimal("0.10"));
            SupportedOddRangesProvider supportedOddRangesProvider = new SupportedOddRangesProvider();
            supportedOddRangesProvider.setLowerLimit(BigDecimal.ONE);
            supportedOddRangesProvider.setUpperLimit(BigDecimal.TEN);
            logic.setRiskAmountProvider(riskAmountProvider);
            logic.setSupportedOddRangesProvider(supportedOddRangesProvider);
            logic.setSupportedOddTypes(EnumSet.allOf(MatchOdd.Type.class));
            logic.setSupportedProposalsSources(Arrays.asList(ProposeAllPinnacleMatchesLogic.class.getSimpleName()));

            //PinnacleOddRangeAndTypeDrivenBettor(BigDecimal.ONE, BigDecimal.TEN, Sets.newEnumSet(Arrays.asList(MatchOdd.Type.values()), MatchOdd.Type.class), new BigDecimal("0.10"));
            bettor.setActive(true);
            bettor.addToResources(new BigDecimal("200.00"));
            bettor.setDescription("TEST BETTOR FOR INTEGRATION TESTS");
            bettor.setBookmaker(bookmaker);
            bettor.setLogicParameters(ObjectMapperFactory.get().convertValue(logic,Map.class));

            BigDecimal odd1 = new BigDecimal("1.50");
            MatchOdd matchOdd1 = new MatchOdd();
            matchOdd1.setOdd1(odd1);
            matchOdd1.setOddX(odd1);
            matchOdd1.setOdd2(odd1);
            matchOdd1.setOddBTSN(odd1);
            matchOdd1.setBookmaker(bookmaker);
            matchOdd1.setMatch(match);
            matchOdd1.setTimestamp(LocalDateTime.now().minusMinutes(14));

            BigDecimal odd2 = new BigDecimal("1.70");
            MatchOdd matchOdd2 = new MatchOdd();
            matchOdd2.setOdd1(odd2);
            matchOdd2.setOddX(odd2);
            matchOdd2.setOdd2(odd2);
            matchOdd2.setBookmaker(bookmaker);
            matchOdd2.setMatch(match2);
            matchOdd2.setTimestamp(LocalDateTime.now().minusMinutes(11));

            utx.begin();
            entityManager.joinTransaction();
            persister.persistAndScheduleForRemoval(t1);
            persister.persistAndScheduleForRemoval(t2);
            persister.persistAndScheduleForRemoval(match);
            persister.persistAndScheduleForRemoval(t3);
            persister.persistAndScheduleForRemoval(t4);
            persister.persistAndScheduleForRemoval(match2);
            persister.persistAndScheduleForRemoval(bookmaker);
            persister.persistAndScheduleForRemoval(bettor);
            persister.persistAndScheduleForRemoval(matchOdd1);
            persister.persistAndScheduleForRemoval(matchOdd2);

            utx.commit();

            MatchBetProposalRawDTO dtom1_1 = MatchBetProposalRawDTO.builder().setExpectedMatchResult(MatchOdd.Type.ODD1.toString()).setOdd(odd1).setMatchId(match.getId()).setProposalSource("ProposeAllPinnacleMatchesLogic").build();
            MatchBetProposalRawDTO dtom2_1 = MatchBetProposalRawDTO.builder().setExpectedMatchResult(MatchOdd.Type.ODDX.toString()).setOdd(odd1).setMatchId(match2.getId()).setProposalSource("ProposeAllPinnacleMatchesLogic").build();

            MatchBetProposalRawDTO dtom1_2 = MatchBetProposalRawDTO.builder().setExpectedMatchResult(MatchOdd.Type.ODD2.toString()).setOdd(odd1).setMatchId(match.getId()).setProposalSource("ProposeAllPinnacleMatchesLogic").build();
            MatchBetProposalRawDTO dtom1_3 = MatchBetProposalRawDTO.builder().setExpectedMatchResult(MatchOdd.Type.ODDX.toString()).setOdd(odd1).setMatchId(match.getId()).setProposalSource("ProposeAllPinnacleMatchesLogic").build();

            MatchBetProposalRawDTO dtom1_4 = MatchBetProposalRawDTO.builder().setExpectedMatchResult(MatchOdd.Type.ODDBTSN.toString()).setOdd(odd1).setMatchId(match.getId()).setProposalSource("ProposeAllPinnacleMatchesLogic").build();
            MatchBetProposalRawDTO dtom1_5 = MatchBetProposalRawDTO.builder().setExpectedMatchResult(MatchOdd.Type.ODD1.toString()).setOdd(odd1).setMatchId(match.getId()).setProposalSource("ProposeAllPinnacleMatchesLogic").build();


            rawBettingProposalSender.sendMessage(dtom1_1);
            rawBettingProposalSender.sendMessage(dtom1_2);
            rawBettingProposalSender.sendMessage(dtom1_3);
            rawBettingProposalSender.sendMessage(dtom2_1);
            rawBettingProposalSender.sendMessage(dtom1_4);
            rawBettingProposalSender.sendMessage(dtom1_5);

            TimeUnit.MILLISECONDS.sleep(10000);

            TimedValidator bettorResourcesValidatorAfterBetDone = new TimedValidator(30, 1000, () -> {
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
                Assert.assertEquals("Number of expected betting events is different from obtained", 2, noOfBettingEvents);
            });

            bettorResourcesValidatorAfterBetDone.validate();

        } catch (Exception e) {
            log.error(e);
        } finally {
            utx.begin();
            entityManager.joinTransaction();
            persister.removeScheduledEntities();
            utx.commit();
        }
    }


    @Test
    public void testBetMatchMultipleTimes2() throws Exception {

        try {
            Team t1 = new Team("t1");
            Team t2 = new Team("t2");
            Match match = new Match("TestMatch", t1, t2, LocalDateTime.now().plusHours(14));
            Match match2 = new Match("TestMatch2", t2, t1, LocalDateTime.now().plusHours(54));

            Bookmaker bookmaker = new Bookmaker("testBookmaker", "testBookmaker");

            //PinnacleOddRangeAndTypeDrivenBettor bettor = new PinnacleOddRangeAndTypeDrivenBettor(BigDecimal.ONE, BigDecimal.TEN, Sets.newEnumSet(Arrays.asList(MatchOdd.Type.values()), MatchOdd.Type.class), new BigDecimal("0.20"));
            Bettor bettor = new Bettor();
            ParametrizedBettorLogic logic = new ParametrizedBettorLogic();
            ResourcesPartProvider riskAmountProvider = new ResourcesPartProvider();
            riskAmountProvider.setPart(new BigDecimal("0.20"));
            SupportedOddRangesProvider supportedOddRangesProvider = new SupportedOddRangesProvider();
            supportedOddRangesProvider.setLowerLimit(BigDecimal.ONE);
            supportedOddRangesProvider.setUpperLimit(BigDecimal.TEN);
            logic.setRiskAmountProvider(riskAmountProvider);
            logic.setSupportedOddRangesProvider(supportedOddRangesProvider);
            logic.setSupportedOddTypes(EnumSet.allOf(MatchOdd.Type.class));
            logic.setSupportedProposalsSources(Arrays.asList(ProposeAllPinnacleMatchesLogic.class.getSimpleName()));

            bettor.setActive(true);
            bettor.addToResources(new BigDecimal("100.00"));
            bettor.setDescription("TEST BETTOR FOR INTEGRATION TESTS");
            bettor.setBookmaker(bookmaker);
            bettor.setLogicParameters(ObjectMapperFactory.get().convertValue(logic,Map.class));

            BigDecimal odd1 = new BigDecimal("1.50");
            MatchOdd matchOdd1 = new MatchOdd();
            matchOdd1.setOdd1(odd1);
            matchOdd1.setOddX(odd1);
            matchOdd1.setOdd2(odd1);
            matchOdd1.setOddBTSN(odd1);
            matchOdd1.setBookmaker(bookmaker);
            matchOdd1.setMatch(match);
            matchOdd1.setTimestamp(LocalDateTime.now().minusMinutes(14));

            BigDecimal odd2 = new BigDecimal("1.70");
            MatchOdd matchOdd2 = new MatchOdd();
            matchOdd2.setOdd1(odd2);
            matchOdd2.setOddX(odd2);
            matchOdd2.setOdd2(odd2);
            matchOdd2.setBookmaker(bookmaker);
            matchOdd2.setMatch(match2);
            matchOdd2.setTimestamp(LocalDateTime.now().minusMinutes(11));

            utx.begin();
            entityManager.joinTransaction();
            persister.persistAndScheduleForRemoval(t1);
            persister.persistAndScheduleForRemoval(t2);
            persister.persistAndScheduleForRemoval(match);
            persister.persistAndScheduleForRemoval(match2);
            persister.persistAndScheduleForRemoval(bookmaker);
            persister.persistAndScheduleForRemoval(bettor);
            persister.persistAndScheduleForRemoval(matchOdd1);
            persister.persistAndScheduleForRemoval(matchOdd2);

            utx.commit();

            MatchBetProposalRawDTO dtom1_1 = MatchBetProposalRawDTO.builder().setExpectedMatchResult(MatchOdd.Type.ODD1.toString()).setOdd(odd1).setMatchId(match.getId()).setProposalSource("ProposeAllPinnacleMatchesLogic").build();
            MatchBetProposalRawDTO dtom2_1 = MatchBetProposalRawDTO.builder().setExpectedMatchResult(MatchOdd.Type.ODD1.toString()).setOdd(odd2).setMatchId(match2.getId()).setProposalSource("ProposeAllPinnacleMatchesLogic").build();
            MatchBetProposalRawDTO dtom2_2 = MatchBetProposalRawDTO.builder().setExpectedMatchResult(MatchOdd.Type.ODDX.toString()).setOdd(odd2).setMatchId(match2.getId()).setProposalSource("ProposeAllPinnacleMatchesLogic").build();

            MatchBetProposalRawDTO dtom1_2 = MatchBetProposalRawDTO.builder().setExpectedMatchResult(MatchOdd.Type.ODD2.toString()).setOdd(odd1).setMatchId(match.getId()).setProposalSource("ProposeAllPinnacleMatchesLogic").build();
            MatchBetProposalRawDTO dtom1_3 = MatchBetProposalRawDTO.builder().setExpectedMatchResult(MatchOdd.Type.ODDX.toString()).setOdd(odd1).setMatchId(match.getId()).setProposalSource("ProposeAllPinnacleMatchesLogic").build();

            MatchBetProposalRawDTO dtom1_4 = MatchBetProposalRawDTO.builder().setExpectedMatchResult(MatchOdd.Type.ODDBTSN.toString()).setOdd(odd1).setMatchId(match.getId()).setProposalSource("ProposeAllPinnacleMatchesLogic").build();
            MatchBetProposalRawDTO dtom1_5 = MatchBetProposalRawDTO.builder().setExpectedMatchResult(MatchOdd.Type.ODD1.toString()).setOdd(odd1).setMatchId(match.getId()).setProposalSource("ProposeAllPinnacleMatchesLogic").build();
            MatchBetProposalRawDTO dtom2_3 = MatchBetProposalRawDTO.builder().setExpectedMatchResult(MatchOdd.Type.ODDX2.toString()).setOdd(odd2).setMatchId(match2.getId()).setProposalSource("ProposeAllPinnacleMatchesLogic").build();


            rawBettingProposalSender.sendMessage(dtom1_1);
            rawBettingProposalSender.sendMessage(dtom1_2);
            rawBettingProposalSender.sendMessage(dtom1_2);
            rawBettingProposalSender.sendMessage(dtom1_3);
            rawBettingProposalSender.sendMessage(dtom2_1);
            rawBettingProposalSender.sendMessage(dtom1_4);
            rawBettingProposalSender.sendMessage(dtom1_5);
            rawBettingProposalSender.sendMessage(dtom2_2);
            rawBettingProposalSender.sendMessage(dtom2_3);

            TimeUnit.MILLISECONDS.sleep(5000);

            TimedValidator bettorResourcesValidatorAfterBetDone = new TimedValidator(30, 1000, () -> {
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
                Assert.assertEquals("Number of expected betting events is different from obtained", 2, noOfBettingEvents);
                Assert.assertEquals("Bettor available resource is different from expected", new BigDecimal("64.00"), freshBettor.getAvailableResources());
            });

            bettorResourcesValidatorAfterBetDone.validate();


        } catch (Exception e) {
            log.error(e);
        } finally {
            utx.begin();
            entityManager.joinTransaction();
            persister.removeScheduledEntities();
            utx.commit();
        }
    }


    private void createBettors(Map<BigDecimal, BigDecimal> limits, List<EnumSet<MatchOdd.Type>> acceptedOddTypes, List<BigDecimal> riskParts, Bookmaker targetBookmaker) throws Exception {
        List<Bettor> bettorsToAdd = Lists.newArrayListWithCapacity(limits.size() * acceptedOddTypes.size() * riskParts.size());

        limits.forEach((lower, upper) -> {
            acceptedOddTypes.stream().forEach(ot -> {
                riskParts.stream().forEach(rp -> {
                    try {
                        Bettor bettor = new Bettor();
                        //new PinnacleOddRangeAndTypeDrivenBettor(lower, upper, ot, rp);

                        ParametrizedBettorLogic logic = new ParametrizedBettorLogic();
                        ResourcesPartProvider riskAmountProvider = new ResourcesPartProvider();
                        riskAmountProvider.setPart(rp);
                        SupportedOddRangesProvider supportedOddRangesProvider = new SupportedOddRangesProvider();
                        supportedOddRangesProvider.setLowerLimit(lower);
                        supportedOddRangesProvider.setUpperLimit(upper);
                        logic.setRiskAmountProvider(riskAmountProvider);
                        logic.setSupportedOddRangesProvider(supportedOddRangesProvider);
                        logic.setSupportedOddTypes(EnumSet.copyOf(ot));
                        logic.setSupportedProposalsSources(Arrays.asList(ProposeAllPinnacleMatchesLogic.class.getSimpleName()));

                        bettor.setActive(false);
                        bettor.addToResources(new BigDecimal("200.00"));
                        bettor.setBookmaker(targetBookmaker);
                        bettor.setLogicParameters(ObjectMapperFactory.get().convertValue(logic, Map.class));
                        bettorsToAdd.add(bettor);
                    }catch(Exception e){
                        throw new RuntimeException(e);
                    }
                });
            });
        });


        utx.begin();
        entityManager.joinTransaction();
        bettorsToAdd.stream().forEach(b -> {
            entityManager.persist(entityManager.merge(b));
        });
        utx.commit();
    }
}
