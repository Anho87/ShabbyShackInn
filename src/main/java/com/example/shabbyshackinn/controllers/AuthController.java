package com.example.shabbyshackinn.controllers;

import com.example.shabbyshackinn.security.User;
import com.example.shabbyshackinn.security.UserDetailsServiceImpl;
import com.example.shabbyshackinn.utilities.TokenGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class AuthController {

    private final UserDetailsServiceImpl userDetailsService;

    public AuthController(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Autowired
    private JavaMailSender javaMailSender;

    @GetMapping("/login")
    public String showLoginForm() {
        return "/login";
    }

    @RequestMapping("/resetPasswordLink")
    public String showResetPasswordForm() {
        return "resetPasswordLink";
    }

    @PostMapping("/handleResetPassword")
    public String handleResetPassword(@RequestParam("email") String email, RedirectAttributes redirectAttributes) {
        // Generera en token för återställning av lösenord
        String resetToken = TokenGenerator.generateToken(32); // Ange önskad längd för token här
//        System.out.println(email);
        User user = userDetailsService.getUserByEmail(email);
        user.setResetToken(resetToken);
        userDetailsService.saveUserToken(user);
        // Skapa återställningslänk med token
        String resetLink = "http://localhost:8080/resetPassword/" + resetToken;

        // Skicka e-postmeddelande med återställningslänk
        SimpleMailMessage feedback = new SimpleMailMessage();
        feedback.setTo(email);
        feedback.setSubject("Återställning av Lösenord");
        feedback.setText("För att återställa ditt lösenord, klicka på länken nedan:\n" + resetLink);
        javaMailSender.send(feedback);

        // Skicka tillbaka en bekräftelse till användaren och omdirigera till inloggningssidan
        redirectAttributes.addFlashAttribute("message", "En återställningslänk har skickats till " + email);
        return "redirect:/login";
    }
}
