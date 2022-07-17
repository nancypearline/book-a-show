package com.nancy.booking.show.controller;

import com.nancy.booking.show.AbstractTestCase;
import com.nancy.booking.show.model.Movie;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.xml.bind.ValidationException;


public class ShowControllerTestCase extends AbstractTestCase {

    @Autowired
    private ShowController showController;

    @Test(expected = ValidationException.class)
    public void testIncorrectNbRows() throws ValidationException {
        showController.setupShow(Movie.MOTHERING_SUNDAY, 30, 10, 1);
    }

    @Test(expected = ValidationException.class)
    public void testIncorrectNbSeatsInRows() throws ValidationException {
        showController.setupShow(Movie.MOTHERING_SUNDAY, 26, 11, 1);
    }
}
