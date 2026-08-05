package com.orientation.Lumos;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String home() {
        return "<div style='text-align: center; margin-top: 50px; font-family: sans-serif;'>"
                + "  <h1>git --version歡迎來到 Lumos 系統！</h1>"
                + "  <p>Spring Boot + Supabase 資料庫連線測試成功！</p>"
                + "</div>";
    }
}