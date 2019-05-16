package com.kk.betting;

import com.kk.betting.services.common.util.CommonConstants;
import com.kk.betting.services.report.action.ReportCreationInvoker;
import com.kk.betting.test.util.DeploymentGenerator;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import java.io.IOException;

/**
 * Created by KK on 2017-06-10.
 */
@RunWith(Arquillian.class)
public class MatchReportGenerationTest {

    @Deployment
    public static Archive createDeployment() throws IOException {
        return DeploymentGenerator.createDeployment();
    }

    @PersistenceContext(unitName = CommonConstants.PERSISTENCE_UNIT_NAME)
    private EntityManager entityManager;

    @Inject
    ReportCreationInvoker matchDataReportInvoker;

    @Inject
    UserTransaction utx;

    @Test
    public void testPrepareReport() throws Exception {
        try {
            utx.begin();
            entityManager.joinTransaction();
            matchDataReportInvoker.prepareMatchReport();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            utx.commit();
        }
    }


}
