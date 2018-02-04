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
import com.djavid.schoolapp.view.fragment.groups.AllGroupFragment;
import com.djavid.schoolapp.viewmodel.groups.AllGroupItem;
import com.djavid.schoolapp.view.fragment.groups.CreateGroupFragment;
import com.djavid.schoolapp.view.fragment.groups.MyGroupFragment;
import com.djavid.schoolapp.viewmodel.groups.MyGroupItem;

public class GroupsActivity extends AppCompatActivity implements AllGroupFragment.OnListFragmentInteractionListener, CreateGroupFragment.OnFragmentInteractionListener, MyGroupFragment.OnListFragmentInteractionListener {
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            invalidateOptionsMenu();
            switch (item.getItemId()) {
                case R.id.groups_navigation_all_groups:
                    showFragment(new AllGroupFragment());
                    return true;
                case R.id.groups_navigation_my_groups:
                    showFragment(new MyGroupFragment());
                    return true;
                case R.id.groups_navigation_create_group:
                    showFragment(CreateGroupFragment.newInstance("", ""));
                    return true;
            }
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(0);
        showFragment(new AllGroupFragment());
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuView.ItemView createGroupItem = findViewById(R.id.groups_navigation_create_group);
        createGroupItem.setEnabled(App.getAppInstance().getPreferences().getLevel().ordinal() > Level.Student.ordinal());

        return true;
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.groupsFragment, fragment)
                .commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setSelectedItemId(0);
        showFragment(new AllGroupFragment());
    }

    @Override
    public void onListFragmentInteraction(AllGroupItem item) {

    }

    @Override
    public void onListFragmentInteraction(MyGroupItem item) {

    }
}
