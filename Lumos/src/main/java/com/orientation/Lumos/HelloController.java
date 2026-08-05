package com.orientation.Lumos;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {
    @GetMapping("/api/hello") // 改成帶有 /api/hello 之類的獨立路徑
    public String hello() {
        return "Hello, World!";
    }
}