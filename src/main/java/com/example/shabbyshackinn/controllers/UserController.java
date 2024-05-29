package com.example.shabbyshackinn.controllers;

import com.example.shabbyshackinn.security.Role;
import com.example.shabbyshackinn.security.RoleRepo;
import com.example.shabbyshackinn.security.UserDetailsServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
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

    @RequestMapping(path = "/updateUserForm/{username}")
    @PreAuthorize("hasAuthority('Admin')")
    public String updateUserForm(Model model,@PathVariable String username) {
        UserDetails user = userDetailsService.loadUserByUsername(username);
        List<String> allRoles = roleRepo.findAllRoleNames();
        model.addAttribute("add", "Admin");
        model.addAttribute("allRoles", allRoles);
        model.addAttribute("userRoles",  user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));
        return "updateUser";
    }

    @PostMapping("/addUser")
    @PreAuthorize("hasAuthority('Admin')")
    public String addUser(@RequestParam String username,@RequestParam String password,
                          @RequestParam ArrayList<String> roles, RedirectAttributes redirectAttributes){

        ArrayList<Role> roleList = roles.stream()
                .map(roleRepo::findByName)
                .collect(Collectors.toCollection(ArrayList::new));
        String feedback = userDetailsService.addUser(username,password,roleList);
        redirectAttributes.addFlashAttribute("feedback", feedback);
        return "redirect:/shabbyShackInn/manageUsers";
    }

    @PostMapping("/updateUser")
    @PreAuthorize("hasAuthority('Admin')")
    public String updateUser(@RequestParam String oldUsername, @RequestParam String username, @RequestParam String password,
                             @RequestParam ArrayList<String> roles, RedirectAttributes redirectAttributes, Principal principal){

        String currentUser = principal.getName();
        if (oldUsername.equals(currentUser) && !roles.contains("Admin")){
            redirectAttributes.addFlashAttribute("feedback", "Can't remove your own admin");
            return "redirect:/shabbyShackInn/manageUsers";
        }
        ArrayList<Role> roleList = roles.stream()
                .map(roleRepo::findByName)
                .collect(Collectors.toCollection(ArrayList::new));
        String feedback = userDetailsService.updateUser(oldUsername,username,password,roleList);
        redirectAttributes.addFlashAttribute("feedback", feedback);
        return "redirect:/shabbyShackInn/manageUsers";
    }

}
