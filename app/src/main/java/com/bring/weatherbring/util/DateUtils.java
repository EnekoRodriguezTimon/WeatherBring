package com.bring.weatherbring.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static String formatFromMilis(long millis) {
        try {
            Date date = new Date(millis);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

            return sdf.format(date);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void main(String[] args) {
        long timestampInMillis = System.currentTimeMillis();
        String formattedDate = formatFromMilis(timestampInMillis);
        System.out.println(formattedDate);
    }
}
