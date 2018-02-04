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

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(0);
        showFragment(new AllEventsFragment());
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuView.ItemView createEventItem = findViewById(R.id.events_navigation_create_event);
        createEventItem.setEnabled(App.getAppInstance().getPreferences().getLevel().ordinal() > Level.Student.ordinal());

        return true;
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.eventsFragment, fragment)
                .commit();
    }

    @Override
    public void onListFragmentInteraction(MyEventItem item) {

    }

    @Override
    public void onListFragmentInteraction(AllEventItem item) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
