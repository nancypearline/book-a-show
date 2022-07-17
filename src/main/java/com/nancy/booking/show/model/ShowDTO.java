package com.nancy.booking.show.model;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShowDTO implements Serializable {

    private static final long serialVersionUID = -3391875639020185965L;

    private Long showId;

    private int showNumber;

    private LocalDateTime createdDateTime;

    private LocalDateTime lastUpdatedDateTime;

    private Movie movieName;

    private int nbOfRows;

    private int nbSeatsPerRow;

    private List<String> bookedSeats = new ArrayList<>();

    private List<String> availableSeats;

    private LocalDate showDate;

    private LocalTime showTime;

    private List<UserBookingDTO> bookingList = new ArrayList<>();

}
