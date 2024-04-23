package com.example.medication.vinhquang.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtil {
    public static String toDateString(Date dateTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(dateTime);
    }

    public static String toTimeString(Date dateTime) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return timeFormat.format(dateTime);
    }
    public static String dateTimeToString(Date dateTime) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        return timeFormat.format(dateTime);
    }

    public static Date stringToDateTime(String dateTime) throws ParseException {
        SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
        return timeFormat.parse(dateTime);
    }
}
