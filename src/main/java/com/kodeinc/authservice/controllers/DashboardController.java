package com.kodeinc.authservice.controllers;

import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Muyinda Rogers
 * @Date 2023-11-28
 * @Email moverr@gmail.com
 */
public class DashboardController {

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

}
