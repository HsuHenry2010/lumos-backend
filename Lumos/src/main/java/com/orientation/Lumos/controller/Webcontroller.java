package com.orientation.Lumos.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Webcontroller {

    // 首頁
    @GetMapping("/")
    public String index() {
        return "index";
    }

    // 活動介紹
    @GetMapping("/activities")
    public String activities() {
        return "activities";
    }

    // 校園地圖
    @GetMapping("/map")
    public String map() {
        return "map";
    }

    // 學長姐
    @GetMapping("/seniors")
    public String seniors() {
        return "seniors";
    }

    // 記分板
    @GetMapping("/scoreboard")
    public String scoreboard() {
        return "scoreboard";
    }

    // 大地遊戲
    @GetMapping("/games")
    public String games() {
        return "games";
    }
}