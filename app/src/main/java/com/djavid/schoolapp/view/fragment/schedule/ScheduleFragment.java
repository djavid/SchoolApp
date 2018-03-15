package com.djavid.schoolapp.view.fragment.schedule;


import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.djavid.schoolapp.R;
import com.djavid.schoolapp.core.BaseFragment;
import com.djavid.schoolapp.core.Router;
import com.djavid.schoolapp.model.schedule.Schedule;
import com.djavid.schoolapp.model.schedule.ScheduleLesson;
import com.djavid.schoolapp.presenter.instancestate.ScheduleFragmentInstanceState;
import com.djavid.schoolapp.presenter.interfaces.ScheduleFragmentPresenter;
import com.djavid.schoolapp.view.adapter.ScheduleDayItem;
import com.djavid.schoolapp.view.interfaces.ScheduleFragmentView;
import com.mindorks.placeholderview.PlaceHolderView;

import butterknife.BindView;


public class ScheduleFragment extends BaseFragment implements ScheduleFragmentView {

    @BindView(R.id.schedule_day_container)
    PlaceHolderView rv_day_list;

    ScheduleFragmentPresenter presenter;


    public ScheduleFragment() { }

    public static ScheduleFragment newInstance() {
        return new ScheduleFragment();
    }

    @Override
    public View setupView(View view) {


        return view;
    }

    @Override
    public void loadData() {
        presenter.getSchedule();
    }

    @Override
    public void onStart() {
        presenter = getPresenter(ScheduleFragmentPresenter.class);
        presenter.setView(this);
        presenter.setRouter((Router) getActivity());
        presenter.onStart();

        super.onStart();
    }

    @Override
    public void onStop() {
        presenter.saveInstanceState(new ScheduleFragmentInstanceState());
        presenter.setView(null);
        presenter.onStop();

        super.onStop();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_schedule;
    }

    @Override
    public String getPresenterId() {
        return "schedule_fragment";
    }

    @Override
    public void addSchedule(Schedule schedule) {
        resetFeed();
        System.out.println("addShedule");

        rv_day_list.addView(new ScheduleDayItem(getContext(), rv_day_list, schedule.monday, 1));
        rv_day_list.addView(new ScheduleDayItem(getContext(), rv_day_list, schedule.tuesday, 2));
        rv_day_list.addView(new ScheduleDayItem(getContext(), rv_day_list, schedule.wednesday, 3));
        rv_day_list.addView(new ScheduleDayItem(getContext(), rv_day_list, schedule.thursday, 4));
        rv_day_list.addView(new ScheduleDayItem(getContext(), rv_day_list, schedule.friday, 5));
        rv_day_list.addView(new ScheduleDayItem(getContext(), rv_day_list, schedule.saturday, 6));
        rv_day_list.addView(new ScheduleDayItem(getContext(), rv_day_list, schedule.sunday, 7));
    }

    @Override
    public void resetFeed() {
        rv_day_list.removeAllViews();
    }
}
