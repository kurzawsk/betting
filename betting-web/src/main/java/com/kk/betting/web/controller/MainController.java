package com.kk.betting.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by KK on 2017-07-04.
 */
@Controller
public class MainController {

    @RequestMapping(value = "/main",  method = RequestMethod.GET)
    public ModelAndView viewAll() {
        return new ModelAndView("main");
    }
}
