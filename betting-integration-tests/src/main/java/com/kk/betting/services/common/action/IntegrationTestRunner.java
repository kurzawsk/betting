package com.kk.betting.services.common.action;

import com.google.common.base.Strings;
import com.kk.betting.services.common.util.IntegrationTestsUtil;
import junit.framework.TestCase;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

/**
 * Created by KK on 2016-12-28.
 */
public class IntegrationTestRunner {

    private static final Log log = LogFactory.getLog(IntegrationTestRunner.class);
    private static final String APP_CONFIG_FILE_PROPERTY = "application.config.file";
    private static final String INTEGARTION_TEST_CLASSES_PROPERTY = "integration.tests.classes";

    static {
        loadProperties();
    }

    public static void main(String[] args) {
        try {
            Class[] testClasses = getTestClasses();
            long start = System.currentTimeMillis();
            JUnitCore junit = new JUnitCore();
            Result result = junit.run(testClasses);
            processResult(result);
            log.info("Running all integration tests took: " + (System.currentTimeMillis() - start) / 1000 + " [s]");
            System.exit(0);
        } catch (Exception e) {
            log.error("Error occurred during running integration tests", e);
            System.exit(1);
        }
    }

    private static Class[] getTestClasses() {
        String integrationTestsClasses = System.getProperty(INTEGARTION_TEST_CLASSES_PROPERTY);
        if (Strings.isNullOrEmpty(integrationTestsClasses)) {
            throw new RuntimeException("No integration tests provided");
        }

        return Arrays.stream(integrationTestsClasses.split(",")).map(c -> {
            try {
                return Class.forName(c);
            } catch (Exception e) {
                throw new RuntimeException("Exception occurred while loading integration test classes", e);
            }
        }).toArray(Class[]::new);

    }

    private static void loadProperties() {
        try {
            Properties properties = new Properties();
            InputStream is;
            if (System.getProperty(APP_CONFIG_FILE_PROPERTY) != null) {
                is = new FileInputStream(System.getProperty(APP_CONFIG_FILE_PROPERTY));
            } else {
                is = IntegrationTestRunner.class.getClassLoader().getResourceAsStream("integration-tests.properties");
            }
            properties.load(is);

            properties.forEach((key, value) -> System.setProperty(key.toString(), value.toString()));
        } catch (IOException e) {
            throw new RuntimeException("Could not read properties.", e);
        }
    }


    private static void processResult(Result result) {
        if (!result.wasSuccessful()) {
            log.error("Integration tests result: NOT OK");
            StringBuilder messageBuilder = new StringBuilder();
            messageBuilder.append("One or more integration test failed:\n");
            for (Failure failure : result.getFailures()) {
                messageBuilder.append(failureToString(failure));
            }

            log.info(messageBuilder.toString());
            IntegrationTestsUtil.sendEmail("Integration tests report", messageBuilder.toString());
        } else {
            log.info("Integration tests result: OK");
        }
    }

    private static String failureToString(Failure failure) {
        String sb = "---- Failure ----\n" +
                "\n- description: " + failure.getDescription() +
                "\n- message: " + failure.getMessage() +
                "\n- trace: " + failure.getTrace() +
                "\n- full stack trace: " + ExceptionUtils.getFullStackTrace(failure.getException()) +
                "\n---- ----\n";
        return sb;
    }

    private static class RunIntegrationTestsTask implements Runnable {

        private List<Class<? extends TestCase>> testClasses;

        private RunIntegrationTestsTask(List<Class<? extends TestCase>> testClasses) {
            this.testClasses = testClasses;
        }

        @Override
        public void run() {
            long start = System.currentTimeMillis();
            JUnitCore junit = new JUnitCore();
            Result result = junit.run(testClasses.toArray(new Class[testClasses.size()]));
            processResult(result);
            log.info("Running all integration tests took: " + (System.currentTimeMillis() - start) / 1000 + " [s]");

        }


    }


}
