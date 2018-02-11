package com.djavid.schoolapp.view.fragment.schedule;


import android.os.Bundle;
import android.view.View;

import com.djavid.schoolapp.R;
import com.djavid.schoolapp.core.BaseFragment;
import com.djavid.schoolapp.core.Router;
import com.djavid.schoolapp.presenter.instancestate.ScheduleFragmentInstanceState;
import com.djavid.schoolapp.presenter.interfaces.ScheduleFragmentPresenter;
import com.djavid.schoolapp.view.interfaces.ScheduleFragmentView;


public class ScheduleFragment extends BaseFragment implements ScheduleFragmentView {

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


}
