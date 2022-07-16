package com.nancy.booking.show.model;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserBookingDTO implements Serializable {

    private static final long serialVersionUID = 1214901263459778100L;

    private Long id;

    private LocalDateTime createdDateTime;

    private LocalDateTime lastUpdatedDateTime;

    private ShowDTO showId;

    private UserDTO userId;

    private long phoneNo;

    private List<String> usrBookedSeats;

    private boolean isCancelled;

    private LocalDateTime bookingDateTime;

    private LocalDateTime cancellationDateTime;

}
