package com.gamepoint.example;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("")
public class HomeController {

    @RequestMapping("/up")
    public String up() {
        return "UP";
    }
}
