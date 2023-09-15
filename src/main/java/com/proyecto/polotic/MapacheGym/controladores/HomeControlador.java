package com.proyecto.polotic.MapacheGym.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeControlador {

    @GetMapping(value = { "/", "/home", "/inicio", "/index" })
    public ModelAndView home() {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("title", "Inicio");
        maw.addObject("view", "index/home");
        boolean showHeader = true;
        maw.addObject("showHeader", showHeader);
        return maw;
    }

    @GetMapping(value = { "/error" })
        public ModelAndView error() {
        ModelAndView maw = new ModelAndView();
        maw.setViewName("fragments/base");
        maw.addObject("title", "Inicio");
        maw.addObject("view", "index/error");
        boolean showHeader = true;
        maw.addObject("showHeader", showHeader);
        return maw;
    }
}