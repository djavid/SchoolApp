package com.djavid.schoolapp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.djavid.schoolapp.App;
import com.djavid.schoolapp.R;
import com.djavid.schoolapp.model.dto.users.Level;
import com.djavid.schoolapp.view.fragment.events.CreateEventFragment;
import com.djavid.schoolapp.viewmodel.events.AllEventItem;
import com.djavid.schoolapp.view.fragment.events.AllEventsFragment;
import com.djavid.schoolapp.viewmodel.events.MyEventItem;
import com.djavid.schoolapp.view.fragment.events.MyEventsFragment;

public class EventsActivity extends AppCompatActivity implements AllEventsFragment.OnListFragmentInteractionListener, MyEventsFragment.OnListFragmentInteractionListener, CreateEventFragment.OnFragmentInteractionListener {

    private TextView mTextMessage;

    BottomNavigationView mNavigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.events_navigation_all_events:
                    showFragment(new AllEventsFragment());
                    return true;
                case R.id.events_navigation_my_events:
                    showFragment(new MyEventsFragment());
                    return true;
                case R.id.events_navigation_create_event:
                    showFragment(new CreateEventFragment());
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_events);

        mNavigation = findViewById(R.id.navigation);
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mNavigation.setSelectedItemId(R.id.events_navigation_all_events);
        showFragment(new AllEventsFragment());
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuView.ItemView createEventItem = findViewById(R.id.events_navigation_create_event);
        createEventItem.setEnabled(App.getAppInstance().isTeacher());

        return true;
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.eventsFragment, fragment)
                .commit();
    }

    @Override
    public void openEventDetails(MyEventItem item) {
        if (!(App.getAppInstance().isTeacher())) {
            return;
        }

        Intent intent = new Intent(this, EventDetailsActivity.class);
        intent.putExtra(EventDetailsActivity.ARG_EVENTID, item.getIdLong());

        startActivity(intent);
    }

    @Override
    public void openEventDetails(AllEventItem item) {
        if (!(App.getAppInstance().isTeacher())) {
            return;
        }

        Intent intent = new Intent(this, EventDetailsActivity.class);
        intent.putExtra(EventDetailsActivity.ARG_EVENTID, item.getIdLong());

        startActivity(intent);
    }

    @Override
    public void onEventCreated() {
        mNavigation.setSelectedItemId(R.id.events_navigation_my_events);
        //showFragment(new MyEventsFragment());
    }
}
