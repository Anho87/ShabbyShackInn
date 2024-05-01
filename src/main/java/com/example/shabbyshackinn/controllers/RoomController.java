package com.example.shabbyshackinn.controllers;


import com.example.shabbyshackinn.dtos.DetailedRoomDto;
import com.example.shabbyshackinn.services.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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
                         @RequestParam LocalDate endDate, @RequestParam int amountOfPersons){
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("amountOfPersons", amountOfPersons);
        List<DetailedRoomDto> availableRooms = roomService.findAvailableRooms(startDate,endDate,amountOfPersons);
        model.addAttribute("searchResults", availableRooms);
        return "searchResults";
    }
}
