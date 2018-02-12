package com.djavid.schoolapp.presenter.implementations;

import android.util.Log;

import com.djavid.schoolapp.R;
import com.djavid.schoolapp.core.BasePresenter;
import com.djavid.schoolapp.core.Router;
import com.djavid.schoolapp.presenter.instancestate.ScheduleFragmentInstanceState;
import com.djavid.schoolapp.presenter.interfaces.ScheduleFragmentPresenter;
import com.djavid.schoolapp.rest.RestDataRepository;
import com.djavid.schoolapp.view.interfaces.ScheduleFragmentView;

import io.reactivex.disposables.Disposable;
import io.reactivex.disposables.Disposables;


public class ScheduleFragmentPresenterImpl extends BasePresenter<ScheduleFragmentView, Router,
        ScheduleFragmentInstanceState> implements ScheduleFragmentPresenter {

    private final String TAG = this.getClass().getSimpleName();
    private Disposable disposable = Disposables.empty();
    private RestDataRepository dataRepository;


    public ScheduleFragmentPresenterImpl() {
        dataRepository = new RestDataRepository();
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onStop() {
        disposable.dispose();
    }

    @Override
    public String getId() {
        return "schedule_fragment";
    }


    @Override
    public void saveInstanceState(ScheduleFragmentInstanceState instanceState) {
        //setInstanceState(instanceState);
    }

    @Override
    public void getSchedule() {
        Log.i(TAG, "getSchedule()");

        disposable = dataRepository.getAllLessons()
                .subscribe(response -> {
                    if (response == null) return;
                    if (getView() == null) return;

                    getView().addSchedule(response);

                }, error -> {
                    if (getView() == null) return;
                    getView().showError(R.string.load_error);
                });
    }
}
