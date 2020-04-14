package com.sejong.eatnow.web;

import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Log
public class HomeController {

    @GetMapping("/")
    public String home(){
        log.info("Welcome to home....");
        return "home";
    }
}
