package com.example.security.Controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String hello(){
        return "<h1>Hello Welcome to  Spring Demo</h1>";
    }
}
