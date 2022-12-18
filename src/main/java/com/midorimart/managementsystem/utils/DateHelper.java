package com.midorimart.managementsystem.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateHelper {
    // convert date to format dd-MM-yyyy HH:mm:ss
    public static String convertDate(Date date){
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return format.format(date);
    }
}
