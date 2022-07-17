package com.nancy.booking.show.controller;

import com.nancy.booking.show.model.Movie;
import com.nancy.booking.show.model.ShowDTO;
import com.nancy.booking.show.service.ShowService;
import com.nancy.booking.show.util.BookingUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.xml.bind.ValidationException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static com.nancy.booking.show.util.BookingUtil.MAX_ROWS_IN_SHOW;
import static com.nancy.booking.show.util.BookingUtil.MAX_SEATS_PER_ROW;

@Controller
@Slf4j
public class ShowController {
    private final Logger logger = LoggerFactory.getLogger(ShowController.class);

    @Autowired
    private ShowService showService;

    /**
     * Setup a show by ADMIN user
     * @param movieName
     * @param nBRows
     * @param nbSeatsPerRow
     * @param showNo
     * @return
     * @throws ValidationException
     */
    @PostMapping(value = "/setupShow")
    public ShowDTO setupShow(Movie movieName, int nBRows, int nbSeatsPerRow, int showNo) throws ValidationException {
        if(BookingUtil.isNotEmpty(movieName.name()) && nBRows > 0 && nbSeatsPerRow > 0) {
            if(nBRows > MAX_ROWS_IN_SHOW) {
                logger.error("Number of rows in show cannot exceed maximum rows {}", MAX_ROWS_IN_SHOW);
                throw new ValidationException("Number of rows in show cannot exceed maximum rows " + MAX_ROWS_IN_SHOW);
            }
            if(nbSeatsPerRow > MAX_SEATS_PER_ROW) {
                logger.error("Number of seats per row cannot exceed maximum seats {}", MAX_SEATS_PER_ROW);
                throw new ValidationException("Number of seats per row cannot exceed maximum seats " + MAX_SEATS_PER_ROW);
            }
            logger.debug("Started setting up show {}", showNo);
           List<String> allocatedSeats = allocateSeats(nBRows,nbSeatsPerRow);
            ShowDTO showDTO = ShowDTO.builder().showNumber(showNo).movieName(movieName).nbOfRows(nBRows).nbSeatsPerRow(nbSeatsPerRow)
                            .showDate(LocalDate.now()).showTime(LocalTime.NOON).createdDateTime(LocalDateTime.now()).availableSeats(allocatedSeats)
                            .lastUpdatedDateTime(LocalDateTime.now()).build();
            showService.setupShow(showDTO);
            logger.debug("Setup completed for show no {}", showNo);
            return showDTO;
        } else {
            logger.error("Error occured while setting up show {}", showNo);
        }
        return null;
    }

    /**
     * Allocate seats for a show
     * @param nBRows
     * @param nbSeatsPerRow
     * @return
     */
    private List<String> allocateSeats(int nBRows, int nbSeatsPerRow) {
        List<String> totalSeatList = new ArrayList<>();
        int endAscii = nBRows + 65;
        char maxSeat = ((char)endAscii);
        for(char alphabet = 'A'; alphabet < maxSeat ; alphabet++)
        {
            for(int i=0; i<nbSeatsPerRow ; i++) {
                totalSeatList.add(String.valueOf(alphabet)+i);
            }
        }
        return totalSeatList;
    }

    /**
     * Retrieve show details
     * @param showNo
     * @return
     */
    @GetMapping(value="/getShowDetails")
    public ShowDTO retrieveShowDetails(int showNo) {
        if(showNo > 0) {
            logger.debug("Retrieving details of the show...");
            return showService.getShow(showNo);
        }
        logger.error("Invalid show number");
        return null;
    }

    /**
     * Provides list of all shows
     * @return
     */
    @GetMapping(value = "/findAllShows")
    public List<ShowDTO> listAllShows() {
        logger.debug("Retrieving details of all the shows");
        return showService.findAll();
    }

    /**
     * Check if show is setup in system already
     * @param showNo
     * @return
     */
    public boolean checkifShowExists(int showNo) {
        if(showNo > 0) {
            return showService.isShowExists(showNo);
        }
        return false;
    }

}
