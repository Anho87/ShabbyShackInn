package com.example.shabbyshackinn.controllers;

import com.example.shabbyshackinn.utilities.HtmlFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/shabbyShackInn")
public class HtmlEditorController {

    private final HtmlFileService htmlFileService;

    @Autowired
    public HtmlEditorController(HtmlFileService htmlFileService) {
        this.htmlFileService = htmlFileService;
    }


    @RequestMapping("/changeMailLayout")
    @PreAuthorize("hasAuthority('Admin')")
    public String showUpdateHtmlPage(Model model) {
        try {
            String fileContent = new String(FileCopyUtils.copyToByteArray(new File("src/main/resources/templates/bookingConfiramtionEmail.html")));

            model.addAttribute("fileContent", fileContent);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "changeMailLayout";
    }
    
    @PostMapping("/updateOrChangeMailLayout")
    @PreAuthorize("hasAuthority('Admin')")
    public String updateHtml(@RequestParam("htmlCode") String htmlCode) {
        System.out.println("hej");
        htmlFileService.saveHtmlToFile(htmlCode);
        return "redirect:/shabbyShackInn/index";
    }
}
    


