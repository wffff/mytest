package cn.gomro.mid.core.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by yaodw on 2016/6/29.
 */
public class DateUtils {

    public static String getFormatedDate(Date date) {
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(date);
    }

    public static Date getDateFromString(String datePattern) {
        if (datePattern != null) {
            try {
                return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(datePattern);
            } catch (ParseException e) {
                try {
                    return new SimpleDateFormat("yyyy-MM-dd").parse(datePattern);
                } catch (ParseException e1) {
                    try {
                        return new SimpleDateFormat("M/d/yyyy hh:mm:ss").parse(datePattern);
                    } catch (ParseException e2) {

                    }
                }
            }
        }
        return null;
    }
}
