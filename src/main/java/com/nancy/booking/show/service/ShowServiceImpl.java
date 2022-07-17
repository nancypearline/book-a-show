package com.nancy.booking.show.service;

import com.nancy.booking.show.dao.BaseDAO;
import com.nancy.booking.show.dao.ShowDAO;
import com.nancy.booking.show.dao.UserBookingDAO;
import com.nancy.booking.show.entity.Show;
import com.nancy.booking.show.entity.UserBooking;
import com.nancy.booking.show.util.EntityTransformer;
import com.nancy.booking.show.model.ShowDTO;
import com.nancy.booking.show.model.UserBookingDTO;
import com.nancy.booking.show.util.BookingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShowServiceImpl extends BaseService<Show> implements ShowService {
    @Autowired
    ShowDAO showDAO;

    @Autowired
    UserBookingDAO userBookingDAO;

    public ShowServiceImpl(BaseDAO<Show> dao) {
        super(dao);
    }

    @Override
    public ShowDTO setupShow(ShowDTO showDTO) {
        if(showDTO != null && BookingUtil.isNotEmpty(showDTO.getMovieName().name())) {
            Show show = EntityTransformer.toEntity(showDTO, new Show());
            return EntityTransformer.toDto(showDAO.save(show), new ShowDTO());
        }
        return null;
    }

    @Transactional
    @Override
    public ShowDTO getShow(int showNo) {
        if(showNo > 0) {
            Optional<Show> showOptional = showDAO.findByShowNumber(showNo);
            if(showOptional.isPresent()) {
                return EntityTransformer.toDto(showOptional.get(), new ShowDTO());
            }
            return null;
        }
        return null;
    }

    @Override
    public boolean isShowExists(int showNo) {
        if(showNo > 0) {
            return showDAO.existsByShowNumber(showNo);
        }
        return false;
    }

    @Transactional
    @Override
    public List<ShowDTO> findAll() {
        List<ShowDTO> showDTOList = new ArrayList<>();
        showDAO.findAll().forEach(show -> showDTOList.add(EntityTransformer.toDto(show, new ShowDTO())));
        return showDTOList;
    }

    @Transactional
    @Override
    public UserBookingDTO updateSeatBooking(UserBooking userBooking, ShowDTO showDTO, List<String> selectedSeats) {

        Show show = EntityTransformer.toEntity(showDTO, new Show());

        // Remove selected seats from available seats
        show.getAvailableSeats().removeAll(selectedSeats);
        // Add selected seats to booked seats
        show.getBookedSeats().addAll(selectedSeats);

        if(show.getBookingList() != null && !show.getBookingList().isEmpty())
        {
            show.getBookingList().add(userBooking);
        }
        else {
            List<UserBooking> userBookingList = new ArrayList<>();
            userBookingList.add(userBooking);
            show.setBookingList(userBookingList);
        }
        showDAO.save(show);

        return EntityTransformer.toDto(userBooking, new UserBookingDTO());
    }


    @Override
    public void updateSeatCancellation(Show show, List<String> cancelledSeats) {
        if(cancelledSeats != null) {
            // Release cancelled seats so that other users can book the seats
            show.getBookedSeats().removeAll(cancelledSeats);
            // Add cancelled seats to available seats
            show.getAvailableSeats().addAll(cancelledSeats);
            showDAO.save(show);
        }
    }
}
