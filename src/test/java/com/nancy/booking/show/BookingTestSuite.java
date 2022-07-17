package com.nancy.booking.show;

import com.nancy.booking.show.controller.ShowControllerTestCase;
import com.nancy.booking.show.service.ShowServiceTestCase;
import com.nancy.booking.show.service.UserBookingServiceTestCase;
import com.nancy.booking.show.service.UserServiceTestCase;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        UserServiceTestCase.class,
        ShowControllerTestCase.class,
        ShowServiceTestCase.class,
        UserBookingServiceTestCase.class
})
public class BookingTestSuite {
}
