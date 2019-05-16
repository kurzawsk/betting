package com.kk.betting.web.controller;

import com.kk.betting.web.service.AdminService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class AdminController {

    public static final String ADMIN_VIEW = "admin";
    public static final String LOG_FILE_NAMES_ATTR = "logFileNames";
    public static final String FILENAME_PARAM = "filename";

    private AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @RequestMapping(path = Paths.Admin.ADMIN_ROOT, method = RequestMethod.GET)
    public String view(ModelMap model) {
        model.addAttribute(LOG_FILE_NAMES_ATTR, adminService.getBettingApplicationLogFileNames());
        return ADMIN_VIEW;
    }

    @RequestMapping(method = RequestMethod.GET, path = Paths.Admin.ADMIN_DOWNLOAD_FILE)
    public void download(HttpServletResponse response, @RequestParam(value = FILENAME_PARAM) String fileName) throws
            IOException {
        InputStream inputStream = adminService.getBettingApplicationLogFile(fileName);
        response.setContentType("application/download");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        IOUtils.copy(inputStream, response.getOutputStream());
        response.flushBuffer();
        inputStream.close();
    }

}