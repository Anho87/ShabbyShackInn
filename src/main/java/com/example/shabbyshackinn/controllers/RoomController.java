package com.example.shabbyshackinn.controllers;


import com.example.shabbyshackinn.dtos.DetailedBookingDto;
import com.example.shabbyshackinn.dtos.DetailedRoomDto;
import com.example.shabbyshackinn.models.RoomEvent;
import com.example.shabbyshackinn.services.BookingService;
import com.example.shabbyshackinn.services.RoomEventService;
import com.example.shabbyshackinn.services.RoomService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/shabbyShackInn")
public class RoomController {
    private final RoomService roomService;
    private final RoomEventService roomEventService;
    private final BookingService bookingService;

    public RoomController(RoomService roomService, RoomEventService roomEventService, BookingService bookingService) {
        this.roomService = roomService;
        this.roomEventService = roomEventService;
        this.bookingService = bookingService;
    }


    @RequestMapping("/search")
    public String search(Model model, @RequestParam LocalDate startDate,
                         @RequestParam LocalDate endDate, @RequestParam int numberOfGuests) {
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("numberOfGuests", numberOfGuests);
        List<DetailedRoomDto> toSmallroomsList = roomService.findBigEnoughRoomsForNumberOfGuests(numberOfGuests);
        List<DetailedBookingDto> alreadyBookedRooms = bookingService.findBookingByDates(startDate, endDate);
        if (!alreadyBookedRooms.isEmpty()) {
            System.out.println("list not empty");
            List<Long> roomsAlreadyBookedIdList = alreadyBookedRooms.stream().map(room -> room.getMiniRoomDto().getId()).toList();
            List<DetailedRoomDto> roomsNotAlreadyBooked = roomService.findAvailableRooms(roomsAlreadyBookedIdList);
            List<DetailedRoomDto> availableRooms = toSmallroomsList.stream()
                    .filter(roomsNotAlreadyBooked::contains)
                    .toList();
            model.addAttribute("searchResults", availableRooms);
        } else {
            System.out.println("list is empty");
            List<DetailedRoomDto> availableRooms = toSmallroomsList;
            model.addAttribute("searchResults", availableRooms);
        }
        return "searchResults";
    }

    @RequestMapping("/allRooms")
    public String allRooms(Model model) {
        List<DetailedRoomDto> roomList = roomService.getAllRooms();
        model.addAttribute("roomList", roomList);
        return "roomEventUI";
    }

    @GetMapping("/roomLog/{id}")
    public String roomLog(@PathVariable Long id, Model model) {
        List<RoomEvent> roomEventList = roomEventService.getAllRoomEvents(id);
        model.addAttribute("roomEventList", roomEventList);
        return "roomLog";
    }
}
