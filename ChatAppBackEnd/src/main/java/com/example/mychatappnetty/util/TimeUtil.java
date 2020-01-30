package com.example.mychatappnetty.util;

import com.example.mychatappnetty.enums.TimeEnum;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class TimeUtil {

  public static String getTime() {
    System.out.println("getTime...util...");

    // SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Calendar calendar = Calendar.getInstance();
    Long date = calendar.getTime().getTime(); // get time in ms

    return date.toString();
  }

  public static String getTime(Date date) {

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    return sdf.format(date);
  }

  public static boolean isDifferenceGreaterThanTarget(String time, TimeEnum timeEnum) {

    Long preTime = Long.parseLong(time);

    // Get current time
    Calendar calendar = Calendar.getInstance();

    // get time in millisecond;
    Long curTime = calendar.getTime().getTime();

    return curTime - preTime >= timeEnum.getMs();
  }

  public static boolean isDifferenceGreaterThanTarget(Date date, TimeEnum timeEnum) {

    Long preTime = date.getTime();

    // Get current time
    Calendar calendar = Calendar.getInstance();

    Long curTime = calendar.getTime().getTime(); // get time in millisecond;

    return curTime - preTime >= timeEnum.getMs();
  }

  /**
   * local TimeZone to UTC
   *
   * @param localTime
   * @return
   */
  public static Date localToUTC(String localTime) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date localDate = null;
    try {
      localDate = sdf.parse(localTime);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    long localTimeInMillis = localDate.getTime();

    Calendar calendar = Calendar.getInstance();
    calendar.setTimeInMillis(localTimeInMillis);

    int zoneOffset = calendar.get(java.util.Calendar.ZONE_OFFSET);

    int dstOffset = calendar.get(java.util.Calendar.DST_OFFSET);

    calendar.add(java.util.Calendar.MILLISECOND, -(zoneOffset + dstOffset));

    Date utcDate = new Date(calendar.getTimeInMillis());

    return utcDate;
  }

  public static Date convertToUTC(Date date) throws ParseException {
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    String strDate = dateFormat.format(date);
    return dateFormat.parse(strDate);
  }

  public static void main(String[] args) {
    System.out.println(TimeUtil.getTime());
  }

  /**
   * Compare two Date based on their Year-Month-Day HOUR:MINUTE
   *
   * @param d1
   * @param d2
   * @return
   */
  public static boolean isSameTime(Date d1, Date d2) {
    SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    return fmt.format(d1).equals(fmt.format(d2));
  }
}
