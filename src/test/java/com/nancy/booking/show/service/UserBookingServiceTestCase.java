package com.nancy.booking.show.service;

import com.nancy.booking.show.AbstractTestCase;
import com.nancy.booking.show.dao.ShowDAO;
import com.nancy.booking.show.dao.UserBookingDAO;
import com.nancy.booking.show.entity.Show;
import com.nancy.booking.show.entity.UserBooking;
import com.nancy.booking.show.model.ShowDTO;
import com.nancy.booking.show.model.UserBookingDTO;
import com.nancy.booking.show.util.EntityTransformer;
import com.nancy.booking.show.util.TestHelper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.bind.ValidationException;
import java.time.LocalDateTime;
import java.util.Optional;

import static com.nancy.booking.show.util.TestHelper.PHONE_NO;
import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class UserBookingServiceTestCase extends AbstractTestCase {

    @Autowired
    private UserBookingDAO userBookingDAO;

    @Autowired
    private ShowDAO showDAO;

    private ShowDTO showDTO;

    private Show show;

    private UserBookingDTO userBookingDTO;

    @Autowired
    private UserBookingService userBookingService;

    @Before
    public void setup() {
        showDTO = TestHelper.setupShowData();
        show = EntityTransformer.toEntity(showDTO, new Show());
        show.setId(1l);
    }

    @Test
    public void testBookTicket() throws ValidationException {

        userBookingDTO = TestHelper.setUpUserBookingData(); // user input

        Show updatedShow = EntityTransformer.toEntity(showDTO, new Show()); //mocked output
        updatedShow.getBookedSeats().addAll(userBookingDTO.getUsrBookedSeats());
        updatedShow.getAvailableSeats().removeAll(userBookingDTO.getUsrBookedSeats());

        UserBookingDTO savedUserBooking = TestHelper.setUpUserBookingData(); // mocked output
        savedUserBooking.setShowId(EntityTransformer.toDto(updatedShow, new ShowDTO()));

        when(showDAO.findByShowNumber(1)).thenReturn(Optional.ofNullable(show)); // mock dao calls
        when(userBookingDAO.existsByShowIdAndPhoneNo(show,PHONE_NO)).thenReturn(false);
        when(userBookingDAO.save(any(UserBooking.class))).thenReturn(EntityTransformer.toEntity(savedUserBooking, new UserBooking()));
        when(showDAO.save(any(Show.class))).thenReturn(updatedShow);

        savedUserBooking = userBookingService.bookTicket(1, PHONE_NO, userBookingDTO.getUsrBookedSeats(), TestHelper.setupUserData());

        assertNotNull(savedUserBooking); // Verify response
        verify(userBookingDAO, times(1)).existsByShowIdAndPhoneNo(show,PHONE_NO);
        verify(userBookingDAO, times(1)).save(any(UserBooking.class));
        assertEquals(3,savedUserBooking.getUsrBookedSeats().size());
        assertEquals(3,savedUserBooking.getShowId().getBookedSeats().size());
        assertEquals(7,savedUserBooking.getShowId().getAvailableSeats().size());
    }

    @Test
    public void testCancel() throws ValidationException {

        userBookingDTO = TestHelper.setUpUserBookingData(); // User input
        userBookingDTO.setShowId(EntityTransformer.toDto(show, new ShowDTO()));
        userBookingDTO.setId(1l);
        Show show = EntityTransformer.toEntity(showDTO, new Show());
        show.getBookedSeats().addAll(userBookingDTO.getUsrBookedSeats());
        show.getAvailableSeats().removeAll(userBookingDTO.getUsrBookedSeats());
        UserBooking userBooking = EntityTransformer.toEntity(userBookingDTO, new UserBooking());

        Show updatedShow = EntityTransformer.toEntity(showDTO, new Show()); //mocked output
        updatedShow.getBookedSeats().removeAll(userBookingDTO.getUsrBookedSeats());
        updatedShow.getAvailableSeats().addAll(userBookingDTO.getUsrBookedSeats());

        UserBooking updatedUserBooking = EntityTransformer.toEntity(userBookingDTO, new UserBooking()); //mocked output
        updatedUserBooking.setCancelled(true);
        updatedUserBooking.setUsrBookedSeats(null);

        //mocked dao calls
        when(userBookingDAO.findUserBookingByIdAndPhoneNo(1l,PHONE_NO)).thenReturn(Optional.ofNullable(userBooking));
        when(showDAO.save(any(Show.class))).thenReturn(updatedShow);
        when(userBookingDAO.save(any(UserBooking.class))).thenReturn(updatedUserBooking);

        userBookingService.cancelTicket(1, PHONE_NO);

        verify(userBookingDAO, times(1)).findUserBookingByIdAndPhoneNo(1l,PHONE_NO);
        verify(userBookingDAO, times(1)).save(any(UserBooking.class));
        verify(showDAO, times(1)).save(any());
    }

    @Test(expected = ValidationException.class)
    public void testBookTicketIncorrectSeats() throws ValidationException {
        userBookingDTO = TestHelper.setUpWrongBookingData(); // user input

        when(showDAO.findByShowNumber(1)).thenReturn(Optional.ofNullable(show)); // mock dao calls
        when(userBookingDAO.existsByShowIdAndPhoneNo(show,PHONE_NO)).thenReturn(false);

        userBookingService.bookTicket(1, PHONE_NO, userBookingDTO.getUsrBookedSeats(), TestHelper.setupUserData());
    }

    @Test(expected = ValidationException.class)
    public void testDuplicateCancel() throws ValidationException {

        userBookingDTO = TestHelper.setUpUserBookingData(); // User input
        userBookingDTO.setShowId(EntityTransformer.toDto(show, new ShowDTO()));
        userBookingDTO.setId(1l);
        userBookingDTO.setCancelled(true);
        userBookingDTO.setCancellationDateTime(LocalDateTime.now());
        UserBooking userBooking = EntityTransformer.toEntity(userBookingDTO, new UserBooking());

        //mocked dao calls
        when(userBookingDAO.findUserBookingByIdAndPhoneNo(1l,PHONE_NO)).thenReturn(Optional.ofNullable(userBooking));

        userBookingService.cancelTicket(1, PHONE_NO);
    }
}
