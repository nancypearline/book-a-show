package com.nancy.booking.show.dao;

import com.nancy.booking.show.entity.Show;
import com.nancy.booking.show.entity.UserBooking;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserBookingDAO extends BaseDAO<UserBooking> {
    boolean existsByShowIdAndPhoneNo(Show showId, long phoneNo);

    boolean existsByIdAndAndPhoneNo(Long id, long phoneNo);

    Optional<UserBooking> findUserBookingByIdAndPhoneNo(Long id, long phoneNo);
}
