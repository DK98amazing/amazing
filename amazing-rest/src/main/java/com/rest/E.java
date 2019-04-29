package com.rest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * E.
 *
 * @author liguoyao
 */
public class E {
    public static void main(String args[]) throws ParseException {
        String str = "2059-03-30T20:00:00+02:00";
        String str2 = "2018-08-13T15:58:34+02:00";
        String FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
        String FORMAT2 = "yyyy-MM-dd'T'HH:mm:ss'Z'";
        final SimpleDateFormat dateFormat = new SimpleDateFormat(FORMAT);
        final SimpleDateFormat dateFormat2 = new SimpleDateFormat(FORMAT2);
        System.out.println(dateFormat2.parse(dateFormat2.format(dateFormat.parse(str))).getTime());
        System.out.println(Long.MAX_VALUE);
        System.out.println(converterTimeZone(dateFormat.parse(str), 1));
        System.out.println(converterTimeZone(dateFormat.parse(str), 1).getTime());
        //        System.out.println(new Date());


        System.out.println("-----------------------------------------------");

        if (str.contains("+")) {
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            String[] strs = str.split("\\+");
            String timeZone = "GMT-" + strs[1];
            dateFormat2.setTimeZone(TimeZone.getTimeZone(timeZone));
            String ss = dateFormat2.format(dateFormat.parse(str));
            Date date = new SimpleDateFormat(FORMAT2).parse(ss);
            System.out.println("++++++++++++++++++++++++++++++++");
            System.out.println(date);
            System.out.println(date.getTime());
            System.out.println(new Date());
            System.out.println(date.before(new Date()));

        } else if (str.contains("Z")) {
            System.out.println(dateFormat2.format(dateFormat.parse(str)));
        } else {
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            String[] strs = str.split("-");
            if (strs.length == 4) {
                String timeZone = "GMT+" + strs[3];
                dateFormat2.setTimeZone(TimeZone.getTimeZone(timeZone));
                System.out.println(dateFormat2.format(dateFormat.parse(str)));
            } else {
                System.out.println("date error");
            }
        }


        Date date = new Date(1231313131);
        final SimpleDateFormat dateFormatII = new SimpleDateFormat(FORMAT2);
        System.out.println(dateFormatII.format(date));
        dateFormatII.setTimeZone(TimeZone.getTimeZone("UTC"));
        System.out.println(dateFormatII.format(date));

        final String SYNC_TIMER =
            "E:\\plugin\\HZ08900_view\\TRM_gyli\\karaf\\target\\assembly\\configuration\\save_config.txt";

        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(SYNC_TIMER, true);
            OutputStreamWriter writer = new OutputStreamWriter(outputStream, "utf-8");
            writer.write("12313131");
            writer.flush();
        } catch (IOException e) {
            System.err.println(e);
        } finally {
            if (null != outputStream) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    System.err.println(e);
                }
            }
        }


    }

    public static Date converterTimeZone(Date date, int type) {

        SimpleDateFormat dfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Calendar cal = Calendar.getInstance();

        cal.setTime(date);

        int zoneOffset = cal.get(Calendar.ZONE_OFFSET);

        int dstOffset = cal.get(Calendar.DST_OFFSET); // 取得夏令时差：

        if (type == 1) {
            cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset)); // 从本地时间里扣除这些差量，即可以取得UTC时间
        } else {
            cal.add(Calendar.MILLISECOND, (zoneOffset + dstOffset)); // 从本地时间里加上这些差量，即可以取得CST时间
        }


        return cal.getTime();
    }

}
