package com.djavid.schoolapp.model.notifications;

import android.annotation.SuppressLint;

import com.annimon.stream.Stream;

import java.util.List;


/**
 * Created by Andrei on 25-Feb-18.
 */

public class RepeatInfo {

    public Mode mode;
    public int hour;
    public int minute;
    public List<Integer> daysOfWeek;
    public List<Integer> daysOfMonth;

    public enum Mode {
        DailyWeek,
        DailyMonth,
    }

    @SuppressLint("DefaultLocale")
    @Override
    public String toString() {
        switch (mode) {
            case DailyWeek:
                return
                        String.format("%d %d * * %s",
                                hour, minute,
                                Stream.of(daysOfWeek).reduce("", (a, t) -> a + "," + t.toString()).substring(1));
            case DailyMonth:
                return String.format("%d %d %s * *",
                        hour, minute,
                        Stream.of(daysOfMonth).reduce("", (a, t) -> a + "," + t.toString()).substring(1));

        }
        return "";
    }
}
