package com.engripaye.task_management_microservice.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController{

    @GetMapping("/")
    public String home() {
        return "\uD83D\uDE04\n Welcome to Task Management Microservice API!";
    }
}
