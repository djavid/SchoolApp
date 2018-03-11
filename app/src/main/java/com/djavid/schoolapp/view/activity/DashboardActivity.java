package com.djavid.schoolapp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.djavid.schoolapp.App;
import com.djavid.schoolapp.R;
import com.djavid.schoolapp.core.Router;
import com.djavid.schoolapp.rest.Api;
import com.djavid.schoolapp.view.adapter.GroupRecyclerViewAdapter;
import com.djavid.schoolapp.view.fragment.events.AllEventsFragment;
import com.djavid.schoolapp.view.fragment.events.CreateEventFragment;
import com.djavid.schoolapp.view.fragment.events.MyEventsFragment;
import com.djavid.schoolapp.view.fragment.groups.AllGroupFragment;
import com.djavid.schoolapp.view.fragment.groups.MyGroupFragment;
import com.djavid.schoolapp.view.fragment.notifications.NotificationListFragment;
import com.djavid.schoolapp.view.fragment.schedule.GenerateCodeFragment;
import com.djavid.schoolapp.view.fragment.schedule.ScheduleFragment;
import com.djavid.schoolapp.viewmodel.events.EventItem;
import com.djavid.schoolapp.viewmodel.groups.GroupItem;
import com.google.firebase.iid.FirebaseInstanceId;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Router, GroupRecyclerViewAdapter.GroupListInteractionListener, NotificationListFragment.NotificationsInteractionListener,
        MyEventsFragment.OnListFragmentInteractionListener, CreateEventFragment.OnFragmentInteractionListener, AllEventsFragment.OnListFragmentInteractionListener {

    final String TAG = getClass().getSimpleName();
    final String TAG_SCHEDULE = "TAG_SCHEDULE";
    final String TAG_GENERATE_CODE = "TAG_GENERATE_CODE";
    final String TAG_ALL_GROUPS = "TAG_ALL_GROUPS";
    final String TAG_MY_GROUPS = "TAG_MY_GROUPS";
    final String TAG_ALL_EVENTS = "TAG_ALL_EVENTS";
    final String TAG_MY_EVENTS = "TAG_MY_EVENTS";
    final String TAG_CREATE_EVENT = "TAG_CREATE_EVENT";
    final String TAG_NOTIFICATION_LIST = "TAG_NOTIFICATION_LIST";

    private FragmentManager fragmentManager;
    private Fragment scheduleFragment, generateCodeFragment, myGroupFragment,
            notificationListFragment, myEventsFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Menu menu = navigationView.getMenu();
        MenuItem item = menu.findItem(R.id.nav_generate_code);
        item.setVisible(App.getAppInstance().isTeacher());

        scheduleFragment = ScheduleFragment.newInstance();
        generateCodeFragment = GenerateCodeFragment.newInstance();
        myGroupFragment = MyGroupFragment.newInstance();
        myEventsFragment = MyEventsFragment.newInstance();
        notificationListFragment = NotificationListFragment.newInstance();

        fragmentManager = getSupportFragmentManager();

        fragmentManager.addOnBackStackChangedListener(() -> {
            int count = fragmentManager.getBackStackEntryCount();
            if (count == 0) {
                setToolbarTitle(R.string.app_name);
            } else {
                setToolbarTitle(fragmentManager.getBackStackEntryAt(count - 1).getName());
            }
        });
    }

    private void setToolbarTitle(String tag) {
        switch (tag) {
            case TAG_SCHEDULE:
                setToolbarTitle(R.string.title_nav_schedule);
                break;
            case TAG_GENERATE_CODE:
                setToolbarTitle(R.string.title_nav_create_code);
                break;
            case TAG_ALL_GROUPS:
            case TAG_MY_GROUPS:
                setToolbarTitle(R.string.title_nav_groups);
                break;
            case TAG_ALL_EVENTS:
            case TAG_MY_EVENTS:
            case TAG_CREATE_EVENT:
                setToolbarTitle(R.string.title_nav_events);
                break;
            case TAG_NOTIFICATION_LIST:
                setToolbarTitle(R.string.title_nav_notifications);
                break;
            default:
                setToolbarTitle(R.string.app_name);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (App.getAppInstance().getPreferences().getIdentity() == null) {
            startActivity(new Intent(this, LandingActivity.class));
            finishAffinity();
        } else if (App.getAppInstance().getPreferences().getToken() == null) {
            startActivity(new Intent(this, EnterCodeActivity.class));
            finishAffinity();
        } else {
            String fcm = FirebaseInstanceId.getInstance().getToken();
            Log.i("firebase", fcm == null ? "null" : fcm);
            if (fcm != null) {
                App.getAppInstance().getApi().login(
                        App.getAppInstance().getPreferences().getDisplayName(),
                        App.getAppInstance().getPreferences().getIdentity(),
                        FirebaseInstanceId.getInstance().getToken()
                )
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(m ->
                                        App.getAppInstance().getPreferences().setToken(m.token)
                                , Api::HandleError);
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.dashboard, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_generate_code) {
            changeFragment(generateCodeFragment, TAG_GENERATE_CODE, true);
        } else if (id == R.id.nav_groups) {
            changeFragment(myGroupFragment, TAG_MY_GROUPS, true);
        } else if (id == R.id.nav_events) {
            changeFragment(myEventsFragment, TAG_MY_EVENTS, true);
        } else if (id == R.id.nav_notifications) {
            changeFragment(notificationListFragment, TAG_NOTIFICATION_LIST, true);
        } else if (id == R.id.nav_schedule) {
            changeFragment(scheduleFragment, TAG_SCHEDULE, true);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    private void setToolbarTitle(int title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(getString(title));
        }
    }

    @Override
    public void goBack() {

    }

    public void changeFragment(Fragment fragment, String tag, boolean addBackStack) {
        Log.i(TAG, "changeFragment");

        try {

            Fragment existFragment = fragmentManager.findFragmentByTag(tag);
            FragmentTransaction ft = fragmentManager.beginTransaction();
            //ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN); GOOGLE BUG

            if (existFragment == null) {

                // fragment not in back stack, create it.
                ft.replace(R.id.container, fragment, tag);

                if (addBackStack)
                    ft.addToBackStack(tag);
                ft.commit();

                Log.w(TAG, tag + " added to the backstack");

            } else {

                // fragment in back stack, call it back.
                ft.replace(R.id.container, existFragment, tag);
                if (addBackStack) {
                    fragmentManager.popBackStack(tag, 0);
                }
                ft.commit();

                Log.w(TAG, tag + " fragment returned back from backstack");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addGroup(GroupItem group) {
        App.getAppInstance().getApi().joinGroup(
                App.getAppInstance().getPreferences().getToken(),
                group.getIdLong()
        )
                .subscribeOn(Schedulers.io())
                .subscribe(q -> {
                }, Api::HandleError);
    }

    @Override
    public void removeGroup(GroupItem group) {
        App.getAppInstance().getApi().leaveGroup(
                App.getAppInstance().getPreferences().getToken(),
                group.getIdLong()
        )
                .subscribeOn(Schedulers.io())
                .subscribe(q -> {
                }, Api::HandleError);
    }


    @Override
    public void showGroupDetails(GroupItem group) {
        if (!(App.getAppInstance().isTeacher())) {
            return;
        }

        Intent intent = new Intent(this, GroupDetailsActivity.class);
        intent.putExtra(GroupDetailsActivity.ARG_GROUPID, group.getIdLong());

        startActivity(intent);
    }

    @Override
    public void showAllEvents() {
        changeFragment(AllEventsFragment.newInstance(), TAG_ALL_EVENTS, true);
    }

    @Override
    public void openCreateEvent() {
        changeFragment(CreateEventFragment.newInstance(), TAG_CREATE_EVENT, true);
    }

    @Override
    public void showAllGroups() {
        changeFragment(AllGroupFragment.newInstance(), TAG_ALL_GROUPS, true);
    }

    @Override
    public void createNotification() {
        startActivity(new Intent(this, PublishNotificationActivity.class));
    }

    @Override
    public void openEventDetails(EventItem item) {
        Intent intent = new Intent(this, EventDetailsActivity.class);
        intent.putExtra(EventDetailsActivity.ARG_EVENTID, item.getIdLong());

        startActivity(intent);
    }

    @Override
    public void onEventCreated() {
        changeFragment(new MyEventsFragment(), TAG_MY_EVENTS, true);
    }
}
