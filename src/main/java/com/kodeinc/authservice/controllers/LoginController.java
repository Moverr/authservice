package com.kodeinc.authservice.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Muyinda Rogers
 * @Date 2023-11-28
 * @Email moverr@gmail.com
 */
@Controller
public class LoginController {

    @GetMapping("/login")
    public String login() {
        return "login";
    }


}
