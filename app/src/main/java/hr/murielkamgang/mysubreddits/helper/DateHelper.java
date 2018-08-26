package hr.murielkamgang.mysubreddits.helper;

import android.content.Context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import hr.murielkamgang.mysubreddits.R;

public class DateHelper {

    private final Logger logger = LoggerFactory.getLogger(DateHelper.class);
    private final Context context;
    private final SimpleDateFormat simpleDateFormat;

    public DateHelper(Context context) {
        this.context = context;
        this.simpleDateFormat = new SimpleDateFormat("DDD MMM yyyy", context.getResources().getConfiguration().locale);
    }

    //I probably could have wrote this better with using just time in millis
    //But I wanted to play a bit with Calendar
    public String showDatePretty(long date) {
        logger.debug("process date: {}", date);
        Calendar now = Calendar.getInstance(context.getResources().getConfiguration().locale);
        Calendar input = Calendar.getInstance(context.getResources().getConfiguration().locale);
        input.setTimeInMillis(date * 1000);

        final int inputDayOfYear = input.get(Calendar.DAY_OF_YEAR);
        final int nowDayOfYear = now.get(Calendar.DAY_OF_YEAR);
        final int inputHourOfDay = input.get(Calendar.HOUR_OF_DAY);
        final int nowHourOfDay = now.get(Calendar.HOUR_OF_DAY);
        final int inputMinute = input.get(Calendar.MINUTE);
        final int nowMinute = now.get(Calendar.MINUTE);

        final int dayDiff = nowDayOfYear - inputDayOfYear;
        if (dayDiff <= 1) {
            int hourToGetToNow;
            if (dayDiff == 0) {
                hourToGetToNow = nowHourOfDay - inputHourOfDay;
            } else {
                hourToGetToNow = (24 - inputHourOfDay) + nowHourOfDay;
            }

            if (hourToGetToNow < 1) {
                final int minDiff = nowMinute - inputMinute;
                if (minDiff >= 1) {
                    return minDiff == 1 ? context.getString(R.string.place_holder_min, 1)
                            : context.getString(R.string.place_holder_mins, minDiff);
                } else {
                    final int secondDiff = now.get(Calendar.SECOND) - input.get(Calendar.SECOND);
                    return context.getString(R.string.place_holder_sec, secondDiff);
                }

            } else if (hourToGetToNow < 24) {
                return hourToGetToNow == 1 ? context.getString(R.string.place_holder_hour, hourToGetToNow)
                        : context.getString(R.string.place_holder_hours, hourToGetToNow);
            } else {
                return context.getString(R.string.text_yesterday);
            }
        }

        if (dayDiff < 10) {
            if (dayDiff < 7) {
                return context.getString(R.string.place_holder_days, dayDiff);
            } else {
                return context.getString(R.string.text_week_ago);
            }
        }

        return simpleDateFormat.format(input.getTime());
    }
}
