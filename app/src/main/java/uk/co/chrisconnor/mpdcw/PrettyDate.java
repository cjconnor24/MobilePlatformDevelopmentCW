package uk.co.chrisconnor.mpdcw;

import android.util.Log;

import java.util.Calendar;
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

        Log.d(TAG, "getTimeAgo: MINUTES " + diff / MINUTES);
        Log.d(TAG, "getTimeAgo: HOURS " + diff / HOURS);
        Log.d(TAG, "getTimeAgo: DAYS " + diff / DAYS);
        Log.d(TAG, "getTimeAgo: WEEKS " + diff / WEEKS);
        Log.d(TAG, "getTimeAgo: MONTHS " + (diff / WEEKS) /4);
        Log.d(TAG, "getTimeAgo: --------");

        // MONTHS
//        if((diff / WEEKS) /4 > 1){
//            return (diff / WEEKS) /4 + " months ago";
//        } else if (diff / WEEKS > 1){
//            return diff / WEEKS + " weeks ago";
//        } else if (diff / DAYS > 1) {
//            return diff / DAYS + " days ago";
//        } else if (diff / HOURS > 1) {
//            return diff / HOURS + " hours ago";
//        } else if (diff / MINUTES > 1) {
//            return diff / MINUTES + " minutes ago";
//        } else {
//            return " few moments ago";
//        }

//        return "DEFAULT";

//        TODO: NEEDS WORK...BIT OF A MESS


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
