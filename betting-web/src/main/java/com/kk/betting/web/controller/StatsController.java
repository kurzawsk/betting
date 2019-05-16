package com.kk.betting.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.time.LocalDateTime;

/**
 * Created by KK on 2017-07-02.
 */
@Controller
@RequestMapping("/stats")
public class StatsController {

    @RequestMapping(method = RequestMethod.GET)
    public String view(ModelMap model) {

        model.addAttribute("time", LocalDateTime.now());
        return "stats";

    }

}
