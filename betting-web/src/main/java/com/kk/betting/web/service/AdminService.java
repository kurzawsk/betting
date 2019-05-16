package com.kk.betting.web.service;

import com.kk.betting.web.config.BettingServicesConfig;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;

/**
 * Created by KK on 2017-07-11.
 */
@Service
public class AdminService {

    private BettingServicesConfig bettingServicesConfig;

    @Autowired
    public AdminService(BettingServicesConfig bettingServicesConfig) {
        this.bettingServicesConfig = bettingServicesConfig;
    }

    public List<String> getBettingApplicationLogFileNames() {
        return Arrays.asList(new File(bettingServicesConfig.getBettingApplicationLogDirectory()).list());
    }

    public InputStream getBettingApplicationLogFile(String fileName) {
        try {
            return new FileInputStream(new File(bettingServicesConfig.getBettingApplicationLogDirectory() + fileName));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }


    public String getFreeDiskSpace() {
        return getSystemHealthParam(SystemHealthParam.FREE_DISK_SPACE);
    }

    public String getCpuTemperature() {
        return getSystemHealthParam(SystemHealthParam.CPU_TEMPERATURE);
    }

    public String getGpuTemperature() {
        return getSystemHealthParam(SystemHealthParam.GPU_TEMPERATURE);
    }

    public String getBettingAppCpuUsage() {
        return getSystemHealthParam(SystemHealthParam.BETTING_APP_CPU_USAGE);
    }

    public String getBettingAppRamUsage() {
        return getSystemHealthParam(SystemHealthParam.BETTING_APP_RAM_USAGE);
    }

    private String getSystemHealthParam(SystemHealthParam systemHealthParam) {
        try {
            return FileUtils.readFileToString(new File(bettingServicesConfig.getSystemHealthLogDirectory() + systemHealthParam.getParamFileName()), Charset.defaultCharset());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    enum SystemHealthParam {

        FREE_DISK_SPACE("freeDiskSpace.txt"),
        CPU_TEMPERATURE("cpuTemperature.txt"),
        GPU_TEMPERATURE("gpuTemperature.txt"),
        BETTING_APP_CPU_USAGE("bettingAppCpuUsage.txt"),
        BETTING_APP_RAM_USAGE("bettingAppRamUsage.txt");

        private String paramFileName;

        SystemHealthParam(String paramFileName) {
            this.paramFileName = paramFileName;
        }

        public String getParamFileName() {
            return paramFileName;
        }
    }


}
