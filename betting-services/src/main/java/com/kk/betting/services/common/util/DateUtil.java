package com.kk.betting.services.common.util;

import javax.ejb.ScheduleExpression;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {

    public static final int secondsInDay = 86400;

    public static LocalDateTime getDate(String day, String month, String year, String hour, String minute) {

        int iYear = Integer.parseInt(year);
        int iMonth = Integer.parseInt(month);
        int iDay = Integer.parseInt(day);
        int iHour = Integer.parseInt(hour);
        int iMinute = Integer.parseInt(minute);

        if (iDay < 1 || iDay > 31) {
            throw new IllegalArgumentException("Provided day of month is invalid: " + day);
        }

        if (iMonth < 1 || iMonth > 12) {
            throw new IllegalArgumentException("Provided  month is invalid: " + month);
        }

        if (iHour < 0 || iHour > 24) {
            throw new IllegalArgumentException("Provided hour is invalid: " + hour);
        }

        if (iMinute < 0 || iMinute > 60) {
            throw new IllegalArgumentException("Provided day of minute is invalid: " + minute);
        }

        return LocalDateTime.of(iYear, iMonth, iDay, iHour, iMinute);

    }

    public static String getCurrentDateInFormat(String format) {
        return getDateInFormat(LocalDate.now(), format);
    }

    public static String getDateInFormat(LocalDate date, String format) {
        return date.format(DateTimeFormatter.ofPattern(format));
    }

    public static ScheduleExpression getScheduleExpression(String sourceExpression) {
        ScheduleExpression scheduleExpression = new ScheduleExpression();
        for (String pair : sourceExpression.split(";")) {
            String[] value = pair.split("=");
            SchedulerExpressionComponent component = SchedulerExpressionComponent.valueOf(value[0]);
            component.enrichSchedulerExpression(scheduleExpression, value[1]);
        }

        return scheduleExpression;
    }

    private enum SchedulerExpressionComponent {

        SECOND {
            public void enrichSchedulerExpression(ScheduleExpression scheduleExpression, String value) {
                scheduleExpression.second(value);
            }
        }, MINUTE {
            public void enrichSchedulerExpression(ScheduleExpression scheduleExpression, String value) {
                scheduleExpression.minute(value);
            }
        }, HOUR {
            public void enrichSchedulerExpression(ScheduleExpression scheduleExpression, String value) {
                scheduleExpression.hour(value);
            }
        }, DAY_OF_WEEK {
            public void enrichSchedulerExpression(ScheduleExpression scheduleExpression, String value) {
                scheduleExpression.dayOfWeek(value);
            }
        }, DAY_OF_MONTH {
            public void enrichSchedulerExpression(ScheduleExpression scheduleExpression, String value) {
                scheduleExpression.dayOfMonth(value);
            }
        }, YEAR {
            public void enrichSchedulerExpression(ScheduleExpression scheduleExpression, String value) {
                scheduleExpression.year(value);
            }
        };

        public void enrichSchedulerExpression(ScheduleExpression scheduleExpression, String value) {
            scheduleExpression.second(value);
        }
    }


}
