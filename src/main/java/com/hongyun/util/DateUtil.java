package com.hongyun.util;


import com.hongyun.constants.NormalConstants;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class DateUtil {

    private final SimpleDateFormat sdf = new SimpleDateFormat(NormalConstants.YYYY_MM_DD);

    public String getYYYY_MM_DD_DateByNow() {
        Date date = new Date();
        return sdf.format(date);
    }
}
