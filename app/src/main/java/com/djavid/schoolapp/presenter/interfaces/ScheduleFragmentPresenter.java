package com.djavid.schoolapp.presenter.interfaces;

import com.djavid.schoolapp.core.Presenter;
import com.djavid.schoolapp.core.Router;
import com.djavid.schoolapp.presenter.instancestate.ScheduleFragmentInstanceState;
import com.djavid.schoolapp.view.interfaces.ScheduleFragmentView;


public interface ScheduleFragmentPresenter extends Presenter<ScheduleFragmentView, Router,
        ScheduleFragmentInstanceState> {

    void getSchedule();
}
