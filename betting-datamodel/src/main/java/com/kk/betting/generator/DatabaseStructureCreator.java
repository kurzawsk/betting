package com.kk.betting.generator;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

public class DatabaseStructureCreator {

    public static void main(String[] args) {

        Map<String, String> properties = new HashMap<>();

        properties.put("hibernate.dialect", System.getProperty("db.dialect"));
        properties.put("hibernate.connection.url", System.getProperty("db.url"));
        properties.put("hibernate.connection.driver_class", System.getProperty("db.driver"));
        properties.put("hibernate.connection.username", System.getProperty("db.username"));
        properties.put("hibernate.connection.password", System.getProperty("db.password") );
        Persistence.generateSchema(args[0], properties);
        System.exit(0);
    }
}
