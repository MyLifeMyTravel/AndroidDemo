package com.littlejie.mediascan;

import java.text.SimpleDateFormat;

/**
 * Created by littlejie on 2016/12/28.
 */

public class TimeUtil {

    public static String parse2TimeDetail(long timeInMills) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String dateString = formatter.format(timeInMills);
        return dateString;
    }
}
