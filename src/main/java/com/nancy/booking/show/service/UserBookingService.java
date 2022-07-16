package com.nancy.booking.show.service;

import com.nancy.booking.show.model.UserBookingDTO;
import com.nancy.booking.show.model.UserDTO;

import javax.xml.bind.ValidationException;
import java.util.List;

public interface UserBookingService {
    UserBookingDTO bookTicket(int showNumber, long phoneNo, List<String> selectedSeats, UserDTO loggedInUser) throws ValidationException;

    void cancelTicket(long ticketNo, long phoneNo) throws ValidationException;
}
