package com.example.shabbyshackinn.controllers;

import com.example.shabbyshackinn.controllers.util.GenericResponse;
import com.example.shabbyshackinn.dtos.PasswordDto;
import com.example.shabbyshackinn.models.PasswordResetToken;
import com.example.shabbyshackinn.repos.PasswordResetTokenRepo;
import com.example.shabbyshackinn.security.User;
import com.example.shabbyshackinn.security.UserRepo;
import com.example.shabbyshackinn.services.Impl.UserServiceImpl;
import com.example.shabbyshackinn.services.UserDetailsService;
import com.example.shabbyshackinn.services.UserSecurityService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.Locale;
import java.util.Optional;
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

    @Autowired
    private UserSecurityService userSecurityService;



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

        String token = UUID.randomUUID().toString();
//        String token;
//        do {
//            token = UUID.randomUUID().toString();
//        } while (passwordResetTokenRepo.existsByToken(token));
        userService.createPasswordResetTokenForUser(user, token);
        //createPasswordResetTokenForUser(user, token);


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

    @GetMapping("/user/changePassword")
    public ModelAndView showChangePasswordPage(final ModelMap model, @RequestParam("token") final String token) {
        final String result = userSecurityService.validatePasswordResetToken(token);

        if(result != null) {
            String messageKey = "auth.message." + result;
            model.addAttribute("messageKey", messageKey);
            return new ModelAndView("redirect:/login", model);
        } else {
            model.addAttribute("token", token);
            return new ModelAndView("redirect:/updatePassword");
        }
    }

    @GetMapping("/updatePassword")
    public ModelAndView updatePassword(final HttpServletRequest request, final ModelMap model, @RequestParam("messageKey" ) final Optional<String> messageKey) {
        Locale locale = request.getLocale();
        model.addAttribute("lang", locale.getLanguage());
        messageKey.ifPresent( key -> {
                    String message = messages.getMessage(key, null, locale);
                    model.addAttribute("message", message);
                }
        );

        return new ModelAndView("updatePassword", model);
    }

    @PostMapping("/user/savePassword")
    public GenericResponse savePassword(final Locale locale, PasswordDto passwordDto) {

        System.out.println("user/savePassword  token: " + passwordDto.getToken());

        final String result = userSecurityService.validatePasswordResetToken(passwordDto.getToken());

        if(result != null) {
            return new GenericResponse(messages.getMessage("auth.message." + result, null, locale));
        }

        Optional<User> user = userSecurityService.getUserByPasswordResetToken(passwordDto.getToken());
        if(user.isPresent()) {
            userSecurityService.changeUserPassword(user.get(), passwordDto.getNewPassword());
            return new GenericResponse(messages.getMessage("message.resetPasswordSuc", null, locale));
        } else {
            return new GenericResponse(messages.getMessage("auth.message.invalid", null, locale));
        }
    }

}
