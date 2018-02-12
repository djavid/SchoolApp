package com.djavid.schoolapp.view.interfaces;

import com.djavid.schoolapp.core.View;
import com.djavid.schoolapp.model.schedule.Schedule;


public interface ScheduleFragmentView extends View {

    void addSchedule(Schedule schedule);
}
