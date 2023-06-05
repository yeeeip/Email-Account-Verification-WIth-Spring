package com.nuzhd.springemailverification.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class HelloController {
    @GetMapping("/hello")
    public String helloEndpoint(){
        return "Hello from the secured API";
    }
}
