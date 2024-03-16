package com.kodeinc.authservice.controllers;

import com.kodeinc.authservice.models.dtos.requests.LoginRequest;
import com.kodeinc.authservice.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @Autowired
    private AuthService service;


    @PostMapping("/login")
    public String formLogin(@RequestParam String username, @RequestParam String password) {

        try{
            LoginRequest loginRequest = new LoginRequest();
            loginRequest.setUsername(username);
            loginRequest.setPassword(password);

             service.authenticate(loginRequest);


            return "redirect:/dashboard";
        }
        catch (Exception er){
            throw er;
        }

    }


    @GetMapping("/redirectedPage")
    public String redirectedPage() {
        // Perform some logic if needed
        return "redirectedPage"; // This should match the template name or the URL path
    }


}
