package com.djavid.schoolapp.util;

import com.djavid.schoolapp.App;
import com.djavid.schoolapp.R;


public class Codes {

    public static String getWeekDayTitle(int weekday) {

        if (weekday >= 1 && weekday <= 7) {
            return App.getContext().getResources().getStringArray(R.array.week_days)[weekday];
        } else {
            return App.getContext().getResources().getStringArray(R.array.week_days)[0];
        }

    }
}
