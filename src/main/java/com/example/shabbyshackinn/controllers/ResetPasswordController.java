package com.example.shabbyshackinn.controllers;


import com.example.shabbyshackinn.security.User;
import com.example.shabbyshackinn.security.UserDetailsServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ResetPasswordController {
private final UserDetailsServiceImpl userDetailsService;

    public ResetPasswordController(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/resetPassword/{token}")
    public String showResetPasswordForm(@PathVariable("token") String token, Model model) {
        User user = userDetailsService.getUserByResetToken(token);
        if (user == null) {
            // Tokenet är ogiltigt eller har gått ut, visa en felmeddelande-sida eller omdirigera till en felhanteringssida
            return "invalid-token";
        }
        System.out.println(user.getUsername());
        model.addAttribute("token", token);
        return "resetPassword";
    }

    @PostMapping("/updatedPassword")
    public String updatedPassword(@RequestParam("token") String token, @RequestParam("password") String password, RedirectAttributes redirectAttributes){
        String feedback =  userDetailsService.updatePassword(token, password); 

        redirectAttributes.addFlashAttribute("feedback", feedback);
        return "redirect:/login";
    }
}
