package util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by edwin_w on 12/28/14.
 */
public class TimeUtil {

    public static String getCurrentTime(){
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    public static void main(String args[]){
        System.out.print(TimeUtil.getCurrentTime());

    }
}
