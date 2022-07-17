package com.nancy.booking.show.service;

import com.nancy.booking.show.dao.BaseDAO;
import com.nancy.booking.show.dao.UserBookingDAO;
import com.nancy.booking.show.entity.Show;
import com.nancy.booking.show.entity.UserBooking;
import com.nancy.booking.show.util.EntityTransformer;
import com.nancy.booking.show.model.ShowDTO;
import com.nancy.booking.show.model.UserBookingDTO;
import com.nancy.booking.show.model.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.ValidationException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class UserBookingServiceImpl extends BaseService<UserBooking> implements UserBookingService{
    public UserBookingServiceImpl(BaseDAO<UserBooking> dao) {
        super(dao);
    }

    Logger logger = LoggerFactory.getLogger(UserBookingServiceImpl.class);

    @Autowired
    ShowService showService;

    @Autowired
    UserBookingDAO userBookingDAO;

    @Value("${bookShow.cancellation-window}")
    private long cancellationWindow;

    @Transactional
    @Override
    public UserBookingDTO bookTicket(int showNumber, long phoneNo, List<String> selectedSeats, UserDTO loggedInUser) throws ValidationException {
        if(!isDuplicateBooking(showNumber, phoneNo)) {
            List<String> availableSeats;
            if (selectedSeats != null && !selectedSeats.isEmpty()) {
                ShowDTO showDTO = showService.getShow(showNumber);
                availableSeats = showDTO.getAvailableSeats();
                if (availableSeats.containsAll(selectedSeats)) {

                    UserBookingDTO userBookingDTO = UserBookingDTO.builder().userId(loggedInUser).usrBookedSeats(selectedSeats)
                            .createdDateTime(LocalDateTime.now()).lastUpdatedDateTime(LocalDateTime.now()).isCancelled(false)
                            .showId(showDTO).phoneNo(phoneNo).bookingDateTime(LocalDateTime.now()).build();

                    UserBooking userBooking = userBookingDAO.save(EntityTransformer.toEntity(userBookingDTO, new UserBooking()));
                    return showService.updateSeatBooking(userBooking, showDTO, selectedSeats);
                } else {
                    logger.error("Please select available seats. One/More of the selected seat/s is unavailable");
                    throw new ValidationException("Please select available seats. One/More of the selected seat/s is unavailable");
                }
            }
        } else {
            logger.error("Only one booking per phone# is allowed per show");
            throw new ValidationException("Only one booking per phone# is allowed per show");
        }
        return null;
    }

    private boolean isDuplicateBooking(int showNumber, long phoneNo) {
        Show show = EntityTransformer.toEntity(showService.getShow(showNumber), new Show());
        return userBookingDAO.existsByShowIdAndPhoneNo(show, phoneNo);
    }

    @Transactional
    @Override
    public void cancelTicket(long ticketNo, long phoneNo) throws ValidationException {
        Optional<UserBooking> userBookingOpt = userBookingDAO.findUserBookingByIdAndPhoneNo(ticketNo, phoneNo);
        if(userBookingOpt.isPresent()) {
            UserBooking userBooking = userBookingOpt.get();
            if(isEligibleToCancel(userBooking)) {
                showService.updateSeatCancellation(userBooking.getShowId(), userBooking.getUsrBookedSeats());
                userBooking.setCancelled(true);
                userBooking.setCancellationDateTime(LocalDateTime.now());
                userBooking.setLastUpdatedDateTime(LocalDateTime.now());
                userBooking.setUsrBookedSeats(null);
                userBookingDAO.save(userBooking);
            } else {
                logger.error("Booking cannot be cancelled after the cancellation window of {} minutes", cancellationWindow);
                throw new ValidationException("Booking cannot be cancelled after the cancellation window.");
            }
        } else {
            logger.error("Booking details not found. Please enter correct details.");
            throw new ValidationException("Booking details not found. Please enter correct details.");
        }
    }

    private boolean isEligibleToCancel(UserBooking userBooking) {
        if(!userBooking.isCancelled()) {
            LocalDateTime bookingDateTime = userBooking.getBookingDateTime();
            Duration timeInBetween = Duration.between(bookingDateTime, LocalDateTime.now());
            long cancellationMins = timeInBetween.toMinutes();
            return cancellationMins <= cancellationWindow;
        } return false;
    }
}
