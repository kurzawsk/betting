package com.kk.betting.services.common.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Properties;

public class ConfigurationProvider {

    private static LocalDateTime lastFileReadDate = null;
    private static Properties properties = new Properties();
    private static String path = System.getProperty("jboss.server.config.dir") + File.separator + "configuration.properties";

    public static String getProperty(String name) throws IOException {

        File file = new File(path);
        synchronized (properties) {
            if (lastFileReadDate == null) {

                lastFileReadDate = LocalDateTime.now();
                properties = new Properties();
                properties.load(new FileInputStream(path));

            } else {
                if (file.lastModified() > lastFileReadDate.atZone(ZoneId.systemDefault()).toEpochSecond()) {
                    properties = new Properties();
                    properties.load(new FileInputStream(path));
                }
            }

            return properties.getProperty(name);
        }
    }

}
