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

import com.djavid.schoolapp.App;
import com.djavid.schoolapp.R;
import com.djavid.schoolapp.view.fragment.groups.AllGroupFragment;
import com.djavid.schoolapp.view.fragment.groups.CreateGroupFragment;
import com.djavid.schoolapp.view.fragment.groups.GroupRecyclerViewAdapter;
import com.djavid.schoolapp.view.fragment.groups.MyGroupFragment;
import com.djavid.schoolapp.viewmodel.groups.GroupItem;

public class GroupsActivity extends AppCompatActivity implements GroupRecyclerViewAdapter.GroupListInteractionListener, CreateGroupFragment.CreateGroupInteractionListener {
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
                    showFragment(new CreateGroupFragment());
                    return true;
            }
            return true;
        }
    };

    BottomNavigationView mNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        mNavigation = findViewById(R.id.navigation);
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mNavigation.setSelectedItemId(R.id.groups_navigation_all_groups);
        showFragment(new AllGroupFragment());
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuView.ItemView createGroupItem = findViewById(R.id.groups_navigation_create_group);
        createGroupItem.setEnabled(App.getAppInstance().isTeacher());

        return true;
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.groupsFragment, fragment)
                .commit();
    }

    @Override
    public void onGroupCreated() {
        mNavigation.setSelectedItemId(R.id.groups_navigation_my_groups);
    }

    @Override
    public void addGroup(GroupItem group) {
        App.getAppInstance().getApi().joinGroup(
                App.getAppInstance().getPreferences().getToken(),
                group.getIdLong()
        );
    }

    @Override
    public void removeGroup(GroupItem group) {
        App.getAppInstance().getApi().leaveGroup(
                App.getAppInstance().getPreferences().getToken(),
                group.getIdLong()
        );
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
