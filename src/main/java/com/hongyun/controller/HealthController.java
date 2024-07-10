package com.hongyun.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/health")
public class HealthController {

    @GetMapping(value = "/test")
    public String test(){
        System.out.println("hello world");
        return "I'm ok";
    }
}
