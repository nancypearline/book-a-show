package com.nancy.booking.show.util;

public class BookingUtil {

    public static final int MAX_SEATS_PER_ROW = 10;

    public static final int MAX_ROWS_IN_SHOW = 26;

    public static boolean isNotEmpty(String inputStr) {
        return inputStr != null && !inputStr.isEmpty();
    }
}
