package com.orientation.Lumos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Webcontroller {

    @GetMapping("/")
    public String index() {
        // 這會對應去找 templates/index.html
        return "index";
    }
}