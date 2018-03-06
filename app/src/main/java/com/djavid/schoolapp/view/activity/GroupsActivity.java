package com.djavid.schoolapp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationItemView;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.djavid.schoolapp.App;
import com.djavid.schoolapp.R;
import com.djavid.schoolapp.view.fragment.groups.AllGroupFragment;
import com.djavid.schoolapp.view.fragment.groups.CreateGroupFragment;
import com.djavid.schoolapp.view.adapter.GroupRecyclerViewAdapter;
import com.djavid.schoolapp.view.fragment.groups.MyGroupFragment;
import com.djavid.schoolapp.viewmodel.groups.GroupItem;

import io.reactivex.schedulers.Schedulers;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class GroupsActivity extends AppCompatActivity implements
        GroupRecyclerViewAdapter.GroupListInteractionListener, CreateGroupFragment.CreateGroupInteractionListener {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = item -> {
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
        BottomNavigationItemView createGroupItem = findViewById(R.id.groups_navigation_create_group);
        createGroupItem.setVisibility(App.getAppInstance().isTeacher() ? VISIBLE : GONE);

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
