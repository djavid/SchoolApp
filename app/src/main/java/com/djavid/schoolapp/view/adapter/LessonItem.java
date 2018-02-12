package com.djavid.schoolapp.view.adapter;

import android.content.Context;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.djavid.schoolapp.R;
import com.djavid.schoolapp.model.schedule.ScheduleLesson;
import com.mindorks.placeholderview.PlaceHolderView;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.NonReusable;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;


@NonReusable
@Layout(R.layout.lesson_layout)
public class LessonItem {

    @View(R.id.start_time)
    private TextView start_time;
    @View(R.id.end_time)
    private TextView end_time;
    @View(R.id.ll_lesson_info)
    private LinearLayout ll_lesson_info;
    @View(R.id.tv_title)
    private TextView tv_title;
    @View(R.id.tv_teacher)
    private TextView tv_teacher;
    @View(R.id.tv_place)
    private TextView tv_place;

    private final String TAG = this.getClass().getSimpleName();
    private Context mContext;
    private PlaceHolderView mPlaceHolderView;
    private ScheduleLesson lesson;


    public LessonItem(Context mContext, PlaceHolderView mPlaceHolderView, ScheduleLesson lesson) {
        this.mContext = mContext;
        this.mPlaceHolderView = mPlaceHolderView;
        this.lesson = lesson;
    }

    @Resolve
    private void onResolved() {

        try {
            start_time.setText(lesson.getStart_time());
            end_time.setText(lesson.getStart_time()); //TODO add end_time on server
            tv_title.setText(lesson.getSubject());
            tv_teacher.setText(lesson.getTeacher());
            tv_place.setText(lesson.getPlace());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
