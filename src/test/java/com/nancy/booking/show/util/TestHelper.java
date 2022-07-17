package com.nancy.booking.show.util;

import com.nancy.booking.show.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TestHelper {

    public final static long PHONE_NO = 98765432l;

    public static UserDTO setupUserData() {
        UserDTO userDTO = new UserDTO();
        userDTO.setActive(true);
        userDTO.setPassword("alex");
        userDTO.setUsername("alex");
        userDTO.setRole(Role.ADMIN);
        return userDTO;
    }

    public static UserBookingDTO setUpUserBookingData() {
        ShowDTO showDTO = setupShowData();
        List<String> selectedSeatList = new ArrayList<>();
        selectedSeatList.add("A0");
        selectedSeatList.add("A1");
        selectedSeatList.add("A2");

        // User booking data
        UserBookingDTO userBookingDTO = UserBookingDTO.builder().userId(TestHelper.setupUserData()).usrBookedSeats(selectedSeatList)
                .createdDateTime(LocalDateTime.now()).lastUpdatedDateTime(LocalDateTime.now()).isCancelled(false)
                .showId(showDTO).phoneNo(PHONE_NO).bookingDateTime(LocalDateTime.now()).build();
        return userBookingDTO;
    }

    public static ShowDTO setupShowData() {
        ShowDTO showDTO = ShowDTO.builder().showNumber(1).movieName(Movie.MOTHERING_SUNDAY).nbOfRows(1).nbSeatsPerRow(10)
                .showDate(LocalDate.now()).showTime(LocalTime.now()).bookedSeats(new ArrayList<>()).build();
        String[] seatArr = {"A0", "A1", "A2", "A3", "A4", "A5", "A6", "A7", "A8", "A9"};
        List<String> availableSeatList = Arrays.asList(seatArr);
        showDTO.setAvailableSeats(availableSeatList);
        return showDTO;
    }

    public static UserBookingDTO setUpWrongBookingData() {
        ShowDTO showDTO = setupShowData();
        List<String> selectedSeatList = new ArrayList<>();
        selectedSeatList.add("Z0");
        selectedSeatList.add("Z1");
        selectedSeatList.add("Z2");

        // User booking data
        UserBookingDTO userBookingDTO = UserBookingDTO.builder().userId(TestHelper.setupUserData()).usrBookedSeats(selectedSeatList)
                .createdDateTime(LocalDateTime.now()).lastUpdatedDateTime(LocalDateTime.now()).isCancelled(false)
                .showId(showDTO).phoneNo(PHONE_NO).bookingDateTime(LocalDateTime.now()).build();
        return userBookingDTO;
    }
}
