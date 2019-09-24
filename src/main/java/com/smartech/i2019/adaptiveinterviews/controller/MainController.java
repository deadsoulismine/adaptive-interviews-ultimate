package com.smartech.i2019.adaptiveinterviews.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/")
public class MainController {

    @GetMapping("/")
    void handleFoo(HttpServletResponse response) throws IOException {
        response.sendRedirect("/employees");
    }
}
