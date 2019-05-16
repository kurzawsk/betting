package com.kk.betting.services.common.util;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.kk.betting.services.common.service.WebPageBrowser;
import com.kk.betting.domain.Bookmaker;
import com.kk.betting.domain.BookmakerName;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import javax.persistence.EntityManager;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;

public class TestUtil {

    public static WebPageBrowser mockWebBrowserToIdentity(WebPageBrowser browser) {

        when(browser.doGet(anyString())).thenAnswer(new Answer<String>() {
            @Override
            public String answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                return Strings.nullToEmpty((String) args[0]);
            }
        });


        when(browser.doGet(anyString(), anyMap())).thenAnswer(new Answer<String>() {
            @Override
            public String answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                return Strings.nullToEmpty((String) args[0]);
            }
        });

        return browser;

    }

    public static WebPageBrowser mockWebBrowserToMapBased(WebPageBrowser browser, Map<String, String> browserResponses) {

        when(browser.doGet(anyString())).thenAnswer(new Answer<String>() {
            @Override
            public String answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                return Strings.nullToEmpty(browserResponses.get(args[0]));
            }
        });


        when(browser.doGet(anyString(), anyMap())).thenAnswer(new Answer<String>() {
            @Override
            public String answer(InvocationOnMock invocation) throws Throwable {
                Object[] args = invocation.getArguments();
                return Strings.nullToEmpty(browserResponses.get(args[0]));
            }
        });

        return browser;

    }

    public static BigDecimal getRandom(BigDecimal min, BigDecimal max, int scale) {
        BigDecimal randomBigDecimal = min.add(new BigDecimal(Math.random()).multiply(max.subtract(min)));
        return randomBigDecimal.setScale(scale, BigDecimal.ROUND_HALF_UP);
    }


    public static void generateBookmakers(EntityManager entityManager) {
        Bookmaker pinnacle = new Bookmaker();
        List<BookmakerName> pinnacleNames = Lists.newArrayList();
        BookmakerName pinnacleName = new BookmakerName();
        pinnacleName.setName("25");
        pinnacleName.setSystem("www.betexplorer.com");
        pinnacleName.setBookmaker(pinnacle);
        pinnacleNames.add(pinnacleName);
        pinnacle.setNames(pinnacleNames);

        pinnacle.setLabel("Pinnacle");
        pinnacle.setWebPageUrl("http://affiliates.pinnaclesports.com");
        pinnacle.setMinimalAmountToBet(new BigDecimal("1.00"));
        entityManager.persist(pinnacle);

        Bookmaker betAtHome = new Bookmaker();
        List<BookmakerName> betAtHomeNames = Lists.newArrayList();

        BookmakerName betAtHomeName = new BookmakerName();
        betAtHomeName.setName("102");
        betAtHomeName.setBookmaker(betAtHome);
        betAtHomeName.setSystem("www.betexplorer.com");
        betAtHomeNames.add(betAtHomeName);
        betAtHome.setNames(betAtHomeNames);

        betAtHome.setLabel("Bet At Home");
        betAtHome.setWebPageUrl("https://wlbetathome.adsrv.eacdn.com");
        betAtHome.setMinimalAmountToBet(new BigDecimal("1.00"));
        entityManager.persist(betAtHome);
        betAtHomeNames.stream().forEach(entityManager::persist);

        Bookmaker average = new Bookmaker();
        List<BookmakerName> averageNames = Lists.newArrayList();

        BookmakerName averageName = new BookmakerName();
        averageName.setName("Average");
        averageName.setSystem("www.betexplorer.com");
        averageName.setBookmaker(average);
        averageNames.add(averageName);
        average.setNames(averageNames);

        average.setLabel("Average");
        average.setWebPageUrl("Average");
        average.setMinimalAmountToBet(new BigDecimal("1.00"));
        entityManager.persist(average);
    }


}
