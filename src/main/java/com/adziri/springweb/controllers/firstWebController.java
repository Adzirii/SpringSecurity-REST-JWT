package com.adziri.springweb.controllers;

import com.adziri.springweb.entities.User;
import com.adziri.springweb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.Optional;

@RestController
public class firstWebController {
    @Autowired
    private UserService userService;

    @GetMapping("user/{userId}")
    public String getUser(@PathVariable Long userId){
        Optional<User> user = userService.getUserById(userId);

        return user.isPresent()? user.get().getUsername() : "error";
    }


    @GetMapping("/unsecured")
    public String unsecuredData(){
        return "Unsecured data";
    }

    @GetMapping("/secured")
    public String securedData(){
        return "Secured data";
    }

    @GetMapping("/admin")
    public String adminData(){
        return "Admin data";
    }


    @GetMapping("/info")
    public String adminData(Principal principal){
        return principal.getName();
    }
}
