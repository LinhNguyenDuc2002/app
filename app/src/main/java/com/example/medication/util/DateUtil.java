package com.example.medication.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateUtil {
    public static String utilDateToString(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(date);
    }
}
