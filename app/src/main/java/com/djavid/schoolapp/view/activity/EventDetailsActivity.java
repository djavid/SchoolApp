package com.djavid.schoolapp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.djavid.schoolapp.App;
import com.djavid.schoolapp.R;
import com.djavid.schoolapp.model.events.Event;
import com.djavid.schoolapp.rest.Api;
import com.djavid.schoolapp.view.fragment.event_details.AboutEventFragment;
import com.djavid.schoolapp.view.fragment.event_details.EventGroupItemFragment;
import com.djavid.schoolapp.viewmodel.event_details.EventGroupItem;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public class EventDetailsActivity extends AppCompatActivity implements AboutEventFragment.AboutEventFragmentInteractionListener, EventGroupItemFragment.EventGroupInteractionListener {

    public static final String ARG_EVENTID = "eventId";
    private long mEventId;


    BottomNavigationView mNavigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_about_event:
                    showFragment(AboutEventFragment.newInstance(mEventId));
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
            return;
        }

        mNavigation = findViewById(R.id.navigation);
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mNavigation.setSelectedItemId(R.id.navigation_about_event);
        showFragment(AboutEventFragment.newInstance(mEventId));
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
    public void openGroupDetails(EventGroupItem group) {
        Intent intent = new Intent(this, GroupDetailsActivity.class);
        intent.putExtra(GroupDetailsActivity.ARG_GROUPID, group.group.getIdLong());

        startActivity(intent);
    }

    @Override
    public void addEventGroup(EventGroupItem eventGroup) {
        Single<Event> event = App.getAppInstance().getApi()
                .getEvent(App.getAppInstance().getPreferences().getToken(),
                        eventGroup.event.getIdLong());
        event.subscribeOn(Schedulers.io())
                .subscribe(e -> {
                    List<Long> groups = e.participation_groups;
                    groups.add(eventGroup.group.getIdLong());

                    App.getAppInstance().getApi()
                            .updateEvent(
                                    App.getAppInstance().getPreferences().getToken(),
                                    eventGroup.event.getIdLong(),
                                    eventGroup.event.getTitle(),
                                    eventGroup.event.getPlace(),
                                    eventGroup.event.getDescription(),
                                    groups,
                                    Api.Date(eventGroup.event.getStartDate()),
                                    Api.Date(eventGroup.event.getEndDate())
                            ).subscribeOn(Schedulers.io()).subscribe();
                });
    }

    @Override
    public void removeEventGroup(EventGroupItem eventGroup) {
        Single<Event> event = App.getAppInstance().getApi()
                .getEvent(App.getAppInstance().getPreferences().getToken(),
                        eventGroup.event.getIdLong());
        event.subscribeOn(Schedulers.io())
                .subscribe(e -> {
                    List<Long> groups = e.participation_groups;
                    groups.remove(eventGroup.group.getIdLong());

                    App.getAppInstance().getApi()
                            .updateEvent(
                                    App.getAppInstance().getPreferences().getToken(),
                                    eventGroup.event.getIdLong(),
                                    eventGroup.event.getTitle(),
                                    eventGroup.event.getPlace(),
                                    eventGroup.event.getDescription(),
                                    groups,
                                    Api.Date(eventGroup.event.getStartDate()),
                                    Api.Date(eventGroup.event.getEndDate())
                            ).subscribeOn(Schedulers.io()).subscribe();
                });
    }
}
