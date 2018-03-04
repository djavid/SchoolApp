package com.djavid.schoolapp.model.notifications;

import android.annotation.SuppressLint;

import com.annimon.stream.Stream;

import java.util.HashSet;
import java.util.Set;


/**
 * Created by Andrei on 25-Feb-18.
 */

public class RepeatInfo {

    public Mode mode;
    public int hour = 12;
    public int minute;
    public Set<Integer> daysOfWeek = new HashSet<>();
    public Set<Integer> daysOfMonth = new HashSet<>();

    public enum Mode {
        Never,
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
