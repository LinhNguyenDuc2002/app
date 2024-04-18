package com.example.medication.util;

import android.os.Build;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public final class DateUtil {
    public static String transferToString(LocalTime time) {
        DateTimeFormatter formatter = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            formatter = DateTimeFormatter.ofPattern("HH:mm");
            return time.format(formatter);
        }

        return null;
    }
}
