package com.nancy.booking.show.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "USER_BOOKING")
@Data
public class UserBooking extends BaseEntity{

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "FK_SHOW_ID", nullable = false)
    private Show showId;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(name = "FK_USER_ID", nullable = false)
    private User userId;

    @Column(name = "PHONE_NO")
    private long phoneNo;

    @ElementCollection
    @CollectionTable(name = "USR_BOOKED_SEATS")
    @Column(name = "USR_BOOKED_SEATS")
    private List<String> usrBookedSeats;

    @Column(name = "IS_CANCELLED")
    private boolean isCancelled;

    @Column(name = "BOOKING_DTTM")
    private LocalDateTime bookingDateTime;

    @Column(name = "CANCELLATION_DTTM")
    private LocalDateTime cancellationDateTime;

}
