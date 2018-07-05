package com.cup.worldcup.util;

import org.joda.time.DateTime;

import java.util.Date;

public class DateTimeUtil {

    public static Date now() {
        return DateTime.now().toDate();
    }

    public static Date plusDays(Date date, int number) {
        if (date == null) {
            return null;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.plusDays(number).toDate();
    }

    public static Date plusWeeks(Date date, int number) {
        if (date == null) {
            return null;
        }
        DateTime dateTime = new DateTime(date);
        return dateTime.plusWeeks(number).toDate();
    }

    public static int compareTo(Date firstDate, Date secondDate) {
        return firstDate.compareTo(secondDate);
    }
}
