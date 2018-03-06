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
import com.djavid.schoolapp.view.adapter.GroupRecyclerViewAdapter;
import com.djavid.schoolapp.view.fragment.groups.MyGroupFragment;
import com.djavid.schoolapp.view.fragment.schedule.GenerateCodeFragment;
import com.djavid.schoolapp.view.fragment.schedule.ScheduleFragment;
import com.djavid.schoolapp.viewmodel.groups.GroupItem;
import com.google.firebase.iid.FirebaseInstanceId;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Router, GroupRecyclerViewAdapter.GroupListInteractionListener {

    final String TAG = getClass().getSimpleName();
    final String TAG_SCHEDULE = "TAG_SCHEDULE";
    final String TAG_GENERATE_CODE = "TAG_GENERATE_CODE";
    final String TAG_GROUPS = "TAG_GROUPS";

    private FragmentManager fragmentManager;
    private Fragment scheduleFragment, generateCodeFragment, myGroupFragment;


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
        item = menu.findItem(R.id.nav_publish_notification);
        item.setVisible(App.getAppInstance().isTeacher());

        scheduleFragment = ScheduleFragment.newInstance();
        generateCodeFragment = GenerateCodeFragment.newInstance();
        myGroupFragment = MyGroupFragment.newInstance();
        fragmentManager = getSupportFragmentManager();
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
                                App.getAppInstance().getPreferences().setToken(m.get("token")));
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

            //startActivity(new Intent(this, GenerateCodeActivity.class));
            setToolbarTitle(R.string.title_nav_create_code);
            changeFragment(generateCodeFragment, TAG_GENERATE_CODE, true);

        } else if (id == R.id.nav_groups) {

            //startActivity(new Intent(this, GroupsActivity.class));
            setToolbarTitle(R.string.title_nav_groups);
            changeFragment(myGroupFragment, TAG_GROUPS, true);

        } else if (id == R.id.nav_events) {

            startActivity(new Intent(this, EventsActivity.class));

        } else if (id == R.id.nav_publish_notification) {

            startActivity(new Intent(this, PublishNotificationActivity.class));

        } else if (id == R.id.nav_schedule) {

            setToolbarTitle(R.string.title_nav_schedule);
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
                .subscribe();
    }

    @Override
    public void removeGroup(GroupItem group) {
        App.getAppInstance().getApi().leaveGroup(
                App.getAppInstance().getPreferences().getToken(),
                group.getIdLong()
        )
                .subscribeOn(Schedulers.io())
                .subscribe();
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

}
