package com.djavid.schoolapp.view.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.djavid.schoolapp.R;
import com.djavid.schoolapp.core.Router;
import com.djavid.schoolapp.view.fragment.schedule.ScheduleFragment;

public class ScheduleActivity extends AppCompatActivity implements Router {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new ScheduleFragment())
                    .commit();
        }
    }

    @Override
    public void goBack() {

    }
}
