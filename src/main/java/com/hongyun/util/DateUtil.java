package com.hongyun.util;


import com.hongyun.constants.NormalConstants;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@Component
public class DateUtil {

    private final SimpleDateFormat sdf = new SimpleDateFormat(NormalConstants.YYYY_MM_DD);

    public String getYYYY_MM_DD_DateByNow() {
        Date date = new Date();
        return sdf.format(date);
    }

    public int getCurrentWeekDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == Calendar.SUNDAY) {
            return 7;
        } else {
            return dayOfWeek - 1;
        }
    }
}
