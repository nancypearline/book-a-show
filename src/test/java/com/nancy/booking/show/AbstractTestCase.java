package com.nancy.booking.show;

import com.nancy.booking.show.config.BookingShowConfig;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@TestPropertySource(locations = "classpath:application-test.properties")
@RunWith(SpringRunner.class)
@ContextConfiguration(classes= BookingShowConfig.class)
public class AbstractTestCase {
}
