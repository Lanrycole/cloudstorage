package com.eazybooks.controller;

import com.eazybooks.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/login")
public class LogInController {

    UserService userService;

    public LogInController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String userLogIn() {

        return "login";
    }

    }