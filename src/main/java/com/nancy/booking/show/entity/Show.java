package com.nancy.booking.show.entity;

import javax.persistence.*;

import com.nancy.booking.show.model.Movie;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "SHOW")
@Data
public class Show extends BaseEntity{

    @Column(name = "SHOW_NO", nullable = false)
    private int showNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "MOVIE_NAME")
    private Movie movieName;

    @Column(name = "NB_ROWS", nullable = false)
    private int nbOfRows;

    @Column(name = "NB_SEATS_PER_ROW", nullable = false)
    private int nbSeatsPerRow;

    @ElementCollection
    @CollectionTable(name = "BOOKED_SEATS")
    @Column(name = "BOOKED_SEATS")
    private List<String> bookedSeats;

    @ElementCollection
    @CollectionTable(name = "AVAILABLE_SEATS")
    @Column(name = "AVAILABLE_SEATS")
    private List<String> availableSeats;

    @Column(name = "SHOW_DATE")
    private LocalDate showDate;

    @Column(name = "SHOW_TIME")
    private LocalTime showTime;

    @OneToMany(mappedBy = "showId", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<UserBooking> bookingList = new ArrayList<>();
}
