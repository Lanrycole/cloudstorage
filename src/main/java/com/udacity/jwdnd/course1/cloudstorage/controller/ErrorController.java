package com.udacity.jwdnd.course1.cloudstorage.controller;


import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErrorController  implements org.springframework.boot.web.servlet.error.ErrorController {

    @RequestMapping("/error")
    public String handleError()
    {

        return "<h3>Page Not Found return <a href='/home'>Home</a></h3>";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
