package com.example.shabbyshackinn.controllers;

import com.example.shabbyshackinn.controllers.util.GenericResponse;
import com.example.shabbyshackinn.models.PasswordResetToken;
import com.example.shabbyshackinn.repos.PasswordResetTokenRepo;
import com.example.shabbyshackinn.security.User;
import com.example.shabbyshackinn.services.Impl.UserServiceImpl;
import com.example.shabbyshackinn.services.UserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;
import java.util.UUID;


//import static com.sun.org.apache.xml.internal.serializer.utils.Utils.messages;
//JavaMailSender
@Controller
public class UserController {

    @Autowired
    private UserDetailsService userService;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private MessageSource messages;

    @Autowired
    private PasswordResetTokenRepo passwordResetTokenRepo;

    @Autowired
    private Environment env;


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

    @GetMapping("/forgotPassword")
    public String showForgotPasswordPage() {
        return "forgotPassword";
    }

    @PostMapping("/shabbyShackInn/resetPassword")
    public GenericResponse resetPassword(HttpServletRequest request,
                                         @RequestParam("email") String userEmail) {
        User user = userService.findByUsername(userEmail);
        if (user == null) {
            //throw new UserNotFoundException();
            System.out.println("User not found in resetPassword");
        }
        //String token = UUID.randomUUID().toString();
        String token;
        do {
            token = UUID.randomUUID().toString();
        } while (passwordResetTokenRepo.existsByToken(token));
        userService.createPasswordResetTokenForUser(user, token);
        mailSender.send(constructResetTokenEmail(getAppUrl(request),
                request.getLocale(), token, user));
        return new GenericResponse(
                messages.getMessage("message.resetPasswordEmail", null,
                        request.getLocale()));
    }

    public void createPasswordResetTokenForUser(User user, String token) {
        PasswordResetToken myToken = new PasswordResetToken(token, user);
        passwordResetTokenRepo.save(myToken);
    }

    private SimpleMailMessage constructResetTokenEmail(
            String contextPath, Locale locale, String token, User user) {
        String url = contextPath + "/user/changePassword?token=" + token;
        String message = messages.getMessage("message.resetPassword",
                null, locale);
        return constructEmail("Reset Password", message + " \r\n" + url, user);
    }

    private SimpleMailMessage constructEmail(String subject, String body,
                                             User user) {
        SimpleMailMessage email = new SimpleMailMessage();
        email.setSubject(subject);
        email.setText(body);
        email.setTo(user.getUsername());
        //email.setTo("Kalle.johansson@hotmail.com");
        email.setFrom(env.getProperty("support.email"));
        return email;
    }

    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

}
