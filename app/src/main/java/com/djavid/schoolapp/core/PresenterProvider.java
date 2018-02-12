package com.djavid.schoolapp.core;

import com.djavid.schoolapp.presenter.implementations.ScheduleFragmentPresenterImpl;

import java.util.HashMap;
import java.util.Map;


public class PresenterProvider {

    private Map<String, Presenter> presenterMap;

    public PresenterProvider() {
        presenterMap = new HashMap<>();
    }

    public <T extends Presenter> T getPresenter(String presenterId, Class<T> c) {
        createPresenter(presenterId);
        return c.cast(presenterMap.get(presenterId));
    }

    private void createPresenter(String presenterId) {
        if (presenterMap.containsKey(presenterId)) return;

        switch (presenterId){
            case "schedule_fragment":
                presenterMap.put(presenterId, new ScheduleFragmentPresenterImpl());
                break;
        }
    }

    public void removePresenter(String presenterId) {
        if (presenterMap.containsKey(presenterId))
            presenterMap.remove(presenterId);
    }

}
