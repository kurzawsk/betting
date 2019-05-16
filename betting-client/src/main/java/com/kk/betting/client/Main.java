package com.kk.betting.client;

import java.io.IOException;

import javax.naming.NamingException;

import com.kk.betting.services.admin.action.AdminOperationInvoker;
import com.kk.betting.services.admin.action.AdminOperationInvokerRemote;
import com.kk.betting.client.ejb.EJBProvider;
import com.kk.betting.client.gui.MainWindow;

public class Main {

    public static void main(String[] args) throws NamingException, ClassNotFoundException, InstantiationException,
            IllegalAccessException, NoSuchFieldException, IOException {

        AdminOperationInvokerRemote collectorBean = EJBProvider.lookupRemoteBean(AdminOperationInvokerRemote.class,
                AdminOperationInvoker.class, EJBProvider.BETTING_APPLICATION_NAME, "betting-services-0.0.1-SNAPSHOT");

        MainWindow window = new MainWindow(collectorBean);
        window.showMe();
    }

}
