package com.djavid.schoolapp.view.adapter;

import android.content.Context;
import android.widget.TextView;

import com.djavid.schoolapp.R;
import com.djavid.schoolapp.model.schedule.ScheduleLesson;
import com.djavid.schoolapp.util.Codes;
import com.mindorks.placeholderview.PlaceHolderView;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;

import java.util.List;


@NonReusable
@Layout(R.layout.schedule_day_layout)
public class ScheduleDayItem {

    @View(R.id.tv_day)
    private TextView tv_day;
    @View(R.id.lesson_container)
    private PlaceHolderView lesson_container;

    private final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private PlaceHolderView mPlaceHolderView;
    private List<ScheduleLesson> lessons;
    private int weekday;


    public ScheduleDayItem(Context mContext, PlaceHolderView mPlaceHolderView,
                           List<ScheduleLesson> lessons, int weekday) {
        this.mContext = mContext;
        this.mPlaceHolderView = mPlaceHolderView;
        this.lessons = lessons;
        this.weekday = weekday;
    }

    @Resolve
    private void onResolved() {

        try {

            tv_day.setText(Codes.getWeekDayTitle(weekday));

            for (ScheduleLesson lesson : lessons) {
                lesson_container.addView(new LessonItem(mContext, mPlaceHolderView, lesson));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }



}
