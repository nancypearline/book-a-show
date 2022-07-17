package com.nancy.booking.show.service;

import com.nancy.booking.show.entity.Show;
import com.nancy.booking.show.entity.UserBooking;
import com.nancy.booking.show.model.ShowDTO;
import com.nancy.booking.show.model.UserBookingDTO;

import java.util.List;

public interface ShowService {

    ShowDTO setupShow(ShowDTO showDTO);

    ShowDTO getShow(int showNo);

    boolean isShowExists(int showNo);

    List<ShowDTO> findAll();

    UserBookingDTO updateSeatBooking(UserBooking userBooking, ShowDTO showDTO, List<String> selectedSeats);

    void updateSeatCancellation(Show show, List<String> cancelledSeats);
}
