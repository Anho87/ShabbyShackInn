package com.example.shabbyshackinn.controllers;

import com.example.shabbyshackinn.security.Role;
import com.example.shabbyshackinn.security.RoleRepo;
import com.example.shabbyshackinn.security.UserDetailsServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/shabbyShackInn")
public class UserController {
    private final UserDetailsServiceImpl userDetailsService;
    private final RoleRepo roleRepo;

    public UserController(UserDetailsServiceImpl userDetailsService, RoleRepo roleRepo) {
        this.userDetailsService = userDetailsService;
        this.roleRepo = roleRepo;
    }

    @RequestMapping(path = "/manageUsers")
    @PreAuthorize("hasAuthority('Admin')")
    public String empty(Model model) {
        List<UserDetails> allUsers = userDetailsService.getAllUsers();
        model.addAttribute("manageUsers", "Admin");
        model.addAttribute("allUsers", allUsers);
        return "manageUsers";
    }
    
    @RequestMapping(path = "/addUserForm")
    @PreAuthorize("hasAuthority('Admin')")
    public String addUserForm(Model model) {
        List<String> allRoles = roleRepo.findAllRoleNames();
        model.addAttribute("add", "Admin");
        model.addAttribute("allRoles", allRoles);
        return "addUser";
    }

    @PostMapping("/addUser")
    @PreAuthorize("hasAuthority('Admin')")
    public String addUser(@RequestParam String username,@RequestParam String password, @RequestParam ArrayList<String> roles,
    RedirectAttributes redirectAttributes){
        System.out.println(username);
        System.out.println(password);
        roles.forEach(s -> System.out.println(s));

        ArrayList<Role> roleList = roles.stream()
                .map(roleRepo::findByName)
                .collect(Collectors.toCollection(ArrayList::new));
        String feedback = userDetailsService.addUser(username,password,roleList);
        redirectAttributes.addFlashAttribute("feedback", feedback);
        return "redirect:/shabbyShackInn/manageUsers";
    }

}
