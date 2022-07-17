package com.nancy.booking.show.service;


import com.nancy.booking.show.AbstractTestCase;
import com.nancy.booking.show.dao.ShowDAO;
import com.nancy.booking.show.entity.Show;
import com.nancy.booking.show.model.Movie;
import com.nancy.booking.show.model.ShowDTO;
import com.nancy.booking.show.util.EntityTransformer;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class ShowServiceTestCase extends AbstractTestCase {

    @Autowired
    private ShowService showService;

    @Autowired
    private ShowDAO showDAO;

    private ShowDTO showDTO, showDTO1, showDTO2;

    private Show show;

    @Before
    public void setup() {
        showDTO = ShowDTO.builder().showNumber(1).movieName(Movie.MOTHERING_SUNDAY).nbOfRows(10).nbSeatsPerRow(10)
                .showDate(LocalDate.now()).showTime(LocalTime.now()).build();
        showDTO1 = ShowDTO.builder().showNumber(2).movieName(Movie.PONNIYIN_SELVAN).nbOfRows(26).nbSeatsPerRow(10)
                .showDate(LocalDate.now()).showTime(LocalTime.now()).build();
        showDTO2 = ShowDTO.builder().showNumber(3).movieName(Movie.MOTHERING_SUNDAY).nbOfRows(12).nbSeatsPerRow(5)
                .showDate(LocalDate.now()).showTime(LocalTime.now()).build();
        show = EntityTransformer.toEntity(showDTO, new Show());
        show.setId(1l);
    }

    @Test
    public void testShowSetup() {
        when(showDAO.save(show)).thenReturn(show);
        ShowDTO savedShow = showService.setupShow(showDTO);
        verify(showDAO, times(1)).save(show);
        assertNotNull(savedShow);
        assertNotNull(savedShow.getShowId());
        assertEquals(savedShow.getShowNumber(), showDTO.getShowNumber());
        assertEquals(savedShow.getMovieName().name(), showDTO.getMovieName().name());
        assertEquals(savedShow.getShowDate(), showDTO.getShowDate());
        assertEquals(savedShow.getShowTime(), showDTO.getShowTime());
        assertEquals(savedShow.getNbOfRows(),showDTO.getNbOfRows());
        assertEquals(savedShow.getNbSeatsPerRow(), showDTO.getNbSeatsPerRow());
    }

    @Test
    public void testFindAll() {
        List<Show> showList = new ArrayList<>();
        showList.add(EntityTransformer.toEntity(showDTO, new Show()));
        showList.add(EntityTransformer.toEntity(showDTO1, new Show()));
        showList.add(EntityTransformer.toEntity(showDTO2, new Show()));
        when(showDAO.findAll()).thenReturn(showList);
        List<ShowDTO> listShowDTO = showService.findAll();
        assertNotNull(listShowDTO);
        assertEquals(3, listShowDTO.size());
        verify(showDAO,times(1)).findAll();
        assertTrue(listShowDTO.contains(showDTO));
        assertTrue(listShowDTO.contains(showDTO1));
        assertTrue(listShowDTO.contains(showDTO2));
    }

    @Test
    public void testGetShow() {
        when(showDAO.findByShowNumber(1)).thenReturn(Optional.ofNullable(show));
        ShowDTO dbShow = showService.getShow(1);
        verify(showDAO, times(1)).findByShowNumber(1);
        assertNotNull(dbShow);
        assertNotNull(dbShow.getShowId());
        assertEquals(dbShow.getMovieName(), showDTO.getMovieName());
        assertEquals(dbShow.getShowNumber(), showDTO.getShowNumber());
        assertEquals(dbShow.getShowDate(), showDTO.getShowDate());
        assertEquals(dbShow.getShowTime(), showDTO.getShowTime());
        assertEquals(dbShow.getNbOfRows(),showDTO.getNbOfRows());
        assertEquals(dbShow.getNbSeatsPerRow(), showDTO.getNbSeatsPerRow());
    }

}
