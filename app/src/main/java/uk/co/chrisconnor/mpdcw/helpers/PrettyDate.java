package uk.co.chrisconnor.mpdcw.helpers;

import java.util.Date;

public class PrettyDate {

    private static final String TAG = "PrettyDate";

    private static final int SECONDS = 1000;
    private static final int MINUTES = 60 * SECONDS;
    private static final int HOURS = 60 * MINUTES;
    private static final int DAYS = 24 * HOURS;
    private static final int WEEKS = 7 * DAYS;

    /**
     * Returns a human readable version of the date/time
     *
     * @param date The date to be used for comparison
     * @return String formatted string based on the two dates
     */
    public static String getTimeAgo(Date date) {

        long now = new Date().getTime();
        long then = date.getTime() - (HOURS * 17);

        if (then > now || then <= 0) {
            return "in the future";
        }

        final long diff = now - then;

        if (diff < MINUTES) {
            return "A few moments ago";
        } else if (diff < 2 * MINUTES) {
            return "A minute ago";
        } else if (diff < 50 * MINUTES) {
            return diff / MINUTES + " minutes ago";
        } else if (diff < 90 * MINUTES) {
            return "An hour ago";
        } else if (diff < 24 * HOURS) {
            return diff / HOURS + " hours ago";
        } else if (diff < 48 * HOURS) {
            return "Yesterday";
        } else if (diff < 14 * DAYS) {
            return diff / DAYS + " days ago";
        } else {
            return diff / DAYS + " days ago";
        }
    }

}
