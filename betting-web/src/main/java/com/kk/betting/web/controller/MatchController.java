package com.kk.betting.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by KK on 2017-07-04.
 */
@Controller
public class MatchController {

    @RequestMapping(value = "/match",  method = RequestMethod.GET)
    public String viewAll(ModelMap model) {
        return  "match";
    }
}
