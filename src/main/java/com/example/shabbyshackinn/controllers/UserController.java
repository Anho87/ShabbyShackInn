package com.example.shabbyshackinn.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserController {
    
    @GetMapping(path = "/manageUsers")
    @PreAuthorize("hasAuthority('Admin')")
    public String empty(Model model) {
        model.addAttribute("manageUsers", "Admin");
        return "admin";
    }
    
    @GetMapping(path = "/addUser")
    @PreAuthorize("hasAuthority('Admin')")
    public String addUser(Model model) {
        model.addAttribute("add", "Admin");
        return "index";
    }
}
