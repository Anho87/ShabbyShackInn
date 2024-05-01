package com.example.shabbyshackinn.controllers;


import com.example.shabbyshackinn.dtos.DetailedRoomDto;
import com.example.shabbyshackinn.services.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/shabbyShackInn")
public class RoomController {
    private final RoomService roomService;

    public RoomController(RoomService roomService){
        this.roomService = roomService;
    }


    @RequestMapping("/search")
    public String search(Model model, @RequestParam LocalDate startDate,
                         @RequestParam LocalDate endDate, @RequestParam int numberOfGuests, RedirectAttributes redirectAttributes){
        if (startDate == null || endDate == null || numberOfGuests >= 0){
            redirectAttributes.addFlashAttribute("feedback", "All search fields must be filled");
            return "redirect:/shabbyShackInn/index";
        }
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("numberOfGuests", numberOfGuests);
        List<DetailedRoomDto> availableRooms = roomService.findAvailableRooms(startDate,endDate,numberOfGuests);
        model.addAttribute("searchResults", availableRooms);
        return "searchResults";
    }
}
