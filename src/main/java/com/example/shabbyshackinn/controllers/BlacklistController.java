package com.example.shabbyshackinn.controllers;


import com.example.shabbyshackinn.models.BlackListedCustomer;
import com.example.shabbyshackinn.services.BlacklistService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/shabbyShackInn")
public class BlacklistController {

    private final BlacklistService blacklistService;

    public BlacklistController(BlacklistService blacklistService) {
        this.blacklistService = blacklistService;
    }

    @RequestMapping("/manageBlacklist")
    public String manageBlacklist(){
        return "manageBlacklist";
    }

    @PostMapping("/updateBlacklist")
    public String updateBlacklist(@RequestParam String updateFirstName, @RequestParam String updateLastName,
                                      @RequestParam String updateEmail, @RequestParam boolean okInBlacklist, RedirectAttributes redirectAttributes) {
        BlackListedCustomer blackListedCustomer = BlackListedCustomer.builder().name(updateFirstName + " " + updateLastName).email(updateEmail).ok(okInBlacklist).group("shabbyShackInn").build();
        String feedback = blacklistService.updateBlacklistedCustomer(blackListedCustomer);
        redirectAttributes.addFlashAttribute("feedback", feedback);
        return "redirect:/shabbyShackInn/index";
    }

    @PostMapping("/addToBlacklist")
    public String addToBlacklist(@RequestParam String addFirstName, @RequestParam String addLastName,
                                 @RequestParam String addEmail, @RequestParam boolean okInBlacklist, RedirectAttributes redirectAttributes){
        BlackListedCustomer blackListedCustomer = BlackListedCustomer.builder().name(addFirstName + " " + addLastName).email(addEmail).ok(okInBlacklist).group("shabbyShackInn").build();
        String feedback = blacklistService.addBlackListedCustomer(blackListedCustomer);
        redirectAttributes.addFlashAttribute("feedback", feedback);
        return "redirect:/shabbyShackInn/index";
    }
}
