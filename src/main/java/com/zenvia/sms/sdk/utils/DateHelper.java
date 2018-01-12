package com.zenvia.sms.sdk.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

public class DateHelper {
    public static String dateToIsoFormat(Date inputDate) {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        df.setTimeZone(tz);
        return df.format(inputDate);
    }
}
