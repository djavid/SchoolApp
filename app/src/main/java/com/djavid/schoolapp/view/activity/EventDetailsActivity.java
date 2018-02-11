package com.djavid.schoolapp.view.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuView;
import android.view.Menu;
import android.view.MenuItem;

import com.djavid.schoolapp.App;
import com.djavid.schoolapp.R;
import com.djavid.schoolapp.model.dto.users.Level;
import com.djavid.schoolapp.view.fragment.event_details.AboutEventFragment;
import com.djavid.schoolapp.view.fragment.event_details.EventGroupItemFragment;
import com.djavid.schoolapp.view.fragment.events.AllEventsFragment;

public class EventDetailsActivity extends AppCompatActivity implements AboutEventFragment.OnFragmentInteractionListener, EventGroupItemFragment.OnListFragmentInteractionListener {

    public static final String ARG_EVENTID = "eventId";
    private long mEventId;


    BottomNavigationView mNavigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_about_event:
                    showFragment(new AboutEventFragment());
                    return true;
                case R.id.navigation_event_groups:
                    showFragment(EventGroupItemFragment.newInstance(mEventId));
                    return true;
                case R.id.navigation_event_comments:
                    return false;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        mEventId = getIntent().getLongExtra(ARG_EVENTID, 0);
        if (mEventId == 0) {
            onBackPressed();
        }

        mNavigation = findViewById(R.id.navigation);
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mNavigation.setSelectedItemId(R.id.navigation_about_event);
        showFragment(new AboutEventFragment());
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        return true;
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.eventDetailsFragment, fragment)
                .commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
