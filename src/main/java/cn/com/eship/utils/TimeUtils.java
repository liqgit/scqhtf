package cn.com.eship.utils;

import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by simon on 16/9/12.
 */
public class TimeUtils {
    public static String convertToDateString(String date) throws Exception {
        return StringUtils.isNotBlank(date) ? new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("MM/dd/yyyy").parse(date)) : "";
    }
    public static String convertToChineseDateString(String date) throws Exception {
        return StringUtils.isNotBlank(date) ? new SimpleDateFormat("yyyy年M月d日").format(new SimpleDateFormat("yyyy-MM-dd").parse(date)) : "";
    }

    public static Map<String,String> getFirstAndLastDayOfThisWeek(Date date){
        Calendar cDate = Calendar.getInstance();
        cDate.setFirstDayOfWeek(Calendar.MONDAY);
        cDate.setTime(date);

        Calendar firstDate = Calendar.getInstance();
        firstDate.setFirstDayOfWeek(Calendar.MONDAY);
        firstDate.setTime(date);

        Calendar lastDate = Calendar.getInstance();
        lastDate.setFirstDayOfWeek(Calendar.MONDAY);
        lastDate.setTime(date);

        if (cDate.get(Calendar.WEEK_OF_YEAR) == 1 && cDate.get(Calendar.MONTH) == 11) {
            firstDate.set(Calendar.YEAR, cDate.get(Calendar.YEAR) + 1);
            lastDate.set(Calendar.YEAR, cDate.get(Calendar.YEAR) + 1);
        }
        int typeNum = cDate.get(Calendar.WEEK_OF_YEAR);
        firstDate.set(Calendar.WEEK_OF_YEAR, typeNum);
        firstDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        String beginDate = new SimpleDateFormat("yyyy-MM-dd").format(firstDate.getTime());
        lastDate.set(Calendar.WEEK_OF_YEAR, typeNum);
        lastDate.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        String endDate = new SimpleDateFormat("yyyy-MM-dd").format(lastDate.getTime());
        Map<String,String> timeMap = new HashMap<>();
        timeMap.put("beginDate",beginDate);
        timeMap.put("endDate",endDate);
        return timeMap;
    }

    public static Map<String,String> getFirstAndLastDayOfLastWeek(Date date){
        Calendar cDate = Calendar.getInstance();
        cDate.setFirstDayOfWeek(Calendar.MONDAY);
        cDate.setTime(date);

        Calendar firstDate = Calendar.getInstance();
        firstDate.setFirstDayOfWeek(Calendar.MONDAY);
        firstDate.setTime(date);

        Calendar lastDate = Calendar.getInstance();
        lastDate.setFirstDayOfWeek(Calendar.MONDAY);
        lastDate.setTime(date);

        if (cDate.get(Calendar.WEEK_OF_YEAR) == 1 && cDate.get(Calendar.MONTH) == 11) {
            firstDate.set(Calendar.YEAR, cDate.get(Calendar.YEAR) + 1);
            lastDate.set(Calendar.YEAR, cDate.get(Calendar.YEAR) + 1);
        }

        int typeNum = cDate.get(Calendar.WEEK_OF_YEAR)-1;

        firstDate.set(Calendar.WEEK_OF_YEAR, typeNum);
        firstDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        String beginDate = new SimpleDateFormat("yyyy-MM-dd").format(firstDate.getTime());
        lastDate.set(Calendar.WEEK_OF_YEAR, typeNum);
        lastDate.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        String endDate = new SimpleDateFormat("yyyy-MM-dd").format(lastDate.getTime());
        Map<String,String> timeMap = new HashMap<>();
        timeMap.put("beginDate",beginDate);
        timeMap.put("endDate",endDate);
        return timeMap;
    }

    public static String getDateOfSomeDaysAgo(int dateInterval){
        Calendar date = Calendar.getInstance();
        date.setTime(new Date());
        date.set(Calendar.DATE, date.get(Calendar.DATE) - dateInterval);
        return new SimpleDateFormat("yyyy-MM-dd").format(date.getTime());
    }
}
