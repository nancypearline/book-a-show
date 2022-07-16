package com.nancy.booking.show.controller;

import com.nancy.booking.show.model.UserBookingDTO;
import com.nancy.booking.show.model.UserDTO;
import com.nancy.booking.show.service.UserBookingService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import javax.xml.bind.ValidationException;
import java.util.Arrays;
import java.util.List;

@Controller
@Slf4j
public class UserBookingController {

    @Autowired
    UserBookingService userBookingService;

    Logger logger = LoggerFactory.getLogger(UserBookingController.class);

    @PostMapping(value = "/bookTicket")
    public UserBookingDTO bookTicket(int showNumber, long phoneNo, String[] selectedSeats, UserDTO loggedInUser) throws ValidationException {
        List<String> selectedSeatsList = Arrays.asList(selectedSeats);

        return userBookingService.bookTicket(showNumber, phoneNo, selectedSeatsList, loggedInUser);
    }

    @PostMapping(value = "/cancelTicket")
    public void cancelTicket(long ticketNo, long phoneNo) throws ValidationException {
        userBookingService.cancelTicket(ticketNo, phoneNo);
    }
}


