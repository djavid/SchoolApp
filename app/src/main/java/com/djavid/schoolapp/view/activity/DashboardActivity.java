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

import com.djavid.schoolapp.R;
import com.djavid.schoolapp.core.Router;
import com.djavid.schoolapp.view.fragment.schedule.ScheduleFragment;

public class DashboardActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Router {

    final String TAG = getClass().getSimpleName();
    final String TAG_SCHEDULE = "TAG_SCHEDULE";
    private FragmentManager fragmentManager;
    private Fragment scheduleFragment;


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

        scheduleFragment = ScheduleFragment.newInstance();
        fragmentManager = getSupportFragmentManager();
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
        getMenuInflater().inflate(R.menu.dashboard, menu);
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
            startActivity(new Intent(this, GenerateCodeActivity.class));
        } else if (id == R.id.nav_groups) {
            startActivity(new Intent(this, GroupsActivity.class));
        } else if (id == R.id.nav_events) {
            startActivity(new Intent(this, EventsActivity.class));
        } else if (id == R.id.nav_notifications) {
            startActivity(new Intent(this, PublishNotificationActivity.class));
        } else if (id == R.id.nav_schedule) {

            if (getSupportActionBar() != null) {
                getSupportActionBar().setTitle(getString(R.string.title_nav_schedule));
            }

            changeFragment(scheduleFragment, TAG_SCHEDULE, true);

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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

}
