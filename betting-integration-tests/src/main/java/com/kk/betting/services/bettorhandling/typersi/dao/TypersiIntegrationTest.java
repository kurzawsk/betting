package com.kk.betting.services.bettorhandling.typersi.dao;

import com.google.common.collect.Lists;
import com.kk.betting.domain.BettingProposalSource;
import com.kk.betting.dto.MatchBetProposalRawDTO;
import com.kk.betting.services.bettorhandling.typersi.service.*;
import com.kk.betting.services.common.dao.*;
import com.kk.betting.services.common.matcher.service.MappingService;
import com.kk.betting.services.common.matcher.service.MatchMatcher;
import com.kk.betting.services.common.matcher.service.TeamMatcher;
import com.kk.betting.services.common.matcher.service.cleanse.Cleanser;
import com.kk.betting.services.common.service.AbstractEntityManagerBasedTest;
import com.kk.betting.services.common.service.WebPageBrowser;
import com.kk.betting.services.matchdatacollection.sourcesystems.betexplorer.dao.BetExplorerCollector;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.Spy;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Created by KK on 2017-08-07.
 */
public class TypersiIntegrationTest extends AbstractEntityManagerBasedTest {

    private static Log log = LogFactory.getLog(TypersiIntegrationTest.class);
    private static final long TYPERSI_HISTORY_LENGTH_IN_MONTHS = 60L;
    private static final int MINIMAL_NUMBER_OF_FOUND_TYPERSI_PROPOSALS = 3;

    @Spy
    private Cleanser cleanser;

    @Spy
    private WebPageBrowser browser;

    @Spy
    private TypersiBetProposalParser typersiBetProposalParser;

    @Spy
    private TypersiHistoryParser typersiHistoryParser;

    @InjectMocks
    private TypersiHistoryEntryDao typersiHistoryEntryDao = Mockito.spy(TypersiHistoryEntryDao.class);

    @InjectMocks
    private TypersiRawDao typersiRawDao = Mockito.spy(TypersiRawDao.class);

    @InjectMocks
    private MatchDao matchDao = Mockito.spy(MatchDao.class);

    @InjectMocks
    private BettingProposalSourceDao bettingProposalSourceDao = Mockito.spy(BettingProposalSourceDao.class);

    @InjectMocks
    private BettingProposalSourceMatchDao bettingProposalSourceMatchDao = Mockito.spy(BettingProposalSourceMatchDao.class);

    @InjectMocks
    private BookmakerDao bookmakerDao = Mockito.spy(BookmakerDao.class);

    @InjectMocks
    private MatchOddDao matchOddDao = Mockito.spy(MatchOddDao.class);

    @InjectMocks
    private TeamDao teamDao = Mockito.spy(TeamDao.class);

    @InjectMocks
    private ProposedMatchMappingDao proposedMatchMappingDao = Mockito.spy(ProposedMatchMappingDao.class);

    @InjectMocks
    private TypersiBetProposalService typersiBetProposalService = Mockito.spy(TypersiBetProposalService.class);

    @InjectMocks
    private MappingService mappingService = Mockito.spy(MappingService.class);

    @InjectMocks
    private TeamMatcher teamMatcher = Mockito.spy(TeamMatcher.class);

    @InjectMocks
    private MatchMatcher matchMatcher = Mockito.spy(MatchMatcher.class);

    @InjectMocks
    private TypersiHistoryAnalyzer typersiHistoryAnalyzer = Mockito.spy(TypersiHistoryAnalyzer.class);

    @InjectMocks
    private TypersiHistoryService typersiHistoryService = Mockito.spy(TypersiHistoryService.class);

    @InjectMocks
    private BetExplorerCollector betExplorerCollector = Mockito.spy(BetExplorerCollector.class);

    @InjectMocks
    private TypersiLogic typersiLogic = new TypersiLogic();

    @Before
    public void init() throws IOException, URISyntaxException {
        startTransaction();
        BettingProposalSource bps = new BettingProposalSource();
        bps.setLogicImpClass(TypersiLogic.class.getName());
        bps.setActive(true);
        bps.setScheduleExpression("-");
        bettingProposalSourceDao.persist(bps);
        typersiLogic.init(Lists.newArrayList(), bps);
        typersiHistoryService.updateTypersiHistory(TYPERSI_HISTORY_LENGTH_IN_MONTHS);
        loadKnownTeams();
        betExplorerCollector.findAndInsertNewMatches();
        mappingService.loadTeams();
        commitTransaction();
    }

    @Test
    public void testProposalSending() {
        startTransaction();
        //Act
        List<MatchBetProposalRawDTO> proposals = typersiLogic.getBettingProposals();
        log.info("Proposals,size: " + proposals.size() + ", content -> " + proposals);

        //Assert
        assertThat("Minimal ratio of found bettor odds", proposals.size(), greaterThanOrEqualTo(MINIMAL_NUMBER_OF_FOUND_TYPERSI_PROPOSALS));
        proposals.stream().forEach(this::validateMatchBetProposalRawDTO);

        commitTransaction();
    }

    private void loadKnownTeams() throws URISyntaxException, IOException {
        Path path = Paths.get(getClass().getClassLoader().getResource("sql/teams.sql").toURI());
        Files.lines(path).forEach(line-> entityManager.createNativeQuery(line).executeUpdate());
    }

    private void validateMatchBetProposalRawDTO(MatchBetProposalRawDTO dto) {
        assertNotNull("dto.getMatchId is null " + dto, dto.getMatchId());
        assertNotNull("dto.getProposalSource is null " + dto, dto.getProposalSource());
        assertNotNull("dto.getOdd is null " + dto, dto.getOdd());
        assertNotNull("dto.getExpectedMatchResult is null " + dto, dto.getExpectedMatchResult());
    }
}
