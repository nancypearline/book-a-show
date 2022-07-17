package com.nancy.booking.show.config;

import com.nancy.booking.show.dao.BaseDAO;
import com.nancy.booking.show.dao.ShowDAO;
import com.nancy.booking.show.dao.UserBookingDAO;
import com.nancy.booking.show.dao.UserDAO;
import com.nancy.booking.show.service.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@ComponentScan(basePackages = { "com.nancy.booking.show.*" })
public class BookingShowConfig {

    @MockBean
    private BaseDAO baseDAO;

    @MockBean
    private UserDAO userDAO;

    @MockBean
    private ShowDAO showDAO;

    @MockBean
    private UserBookingDAO userBookingDAO;

    @Value("${bookShow.cancellation-window}")
    private String cancellationWindow;

    @Bean
    public UserService userService () {
        return new UserServiceImpl(baseDAO);
    }

    @Bean
    public ShowService showService() { return new ShowServiceImpl(baseDAO); }

    @Bean
    public UserBookingService userBookingService() { return new UserBookingServiceImpl(baseDAO); }

}
