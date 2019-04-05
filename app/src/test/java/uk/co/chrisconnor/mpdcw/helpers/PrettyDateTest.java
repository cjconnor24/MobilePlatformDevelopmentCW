package uk.co.chrisconnor.mpdcw.helpers;

import android.util.Log;

import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.TimeZone;

import static org.junit.Assert.*;

public class PrettyDateTest {

    private static final String TAG = "PrettyDateTest";

    @Test
    public void getTimeAgo_afewmoments() {
        assertEquals("A few moments ago",PrettyDate.getTimeAgo(new Date()));
    }

    @Test
    public void getTimeAgo_aminuteago() {
        Date aMinuteAgo = new Date(System.currentTimeMillis() - (60 * 1000));
        assertEquals("A minute ago",PrettyDate.getTimeAgo(aMinuteAgo));
    }

    @Test
    public void getTimeAgo_inTheFuture() {
        Date theFuture = new Date(System.currentTimeMillis() + (60 * 1000));
        assertEquals("in the future",PrettyDate.getTimeAgo(theFuture));
    }

    @Test
    public void getTimeAgo_xMinutesAgo() {
        Date theFuture = new Date(System.currentTimeMillis() - (5 * 60 * 1000));
        assertEquals("5 minutes ago",PrettyDate.getTimeAgo(theFuture));
    }


}