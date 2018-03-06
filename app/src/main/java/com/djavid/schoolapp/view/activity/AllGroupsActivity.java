package com.djavid.schoolapp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.djavid.schoolapp.App;
import com.djavid.schoolapp.R;
import com.djavid.schoolapp.view.adapter.GroupRecyclerViewAdapter;
import com.djavid.schoolapp.view.fragment.groups.AllGroupFragment;
import com.djavid.schoolapp.view.fragment.groups.CreateGroupFragment;
import com.djavid.schoolapp.viewmodel.groups.GroupItem;

import io.reactivex.schedulers.Schedulers;

public class AllGroupsActivity extends AppCompatActivity implements GroupRecyclerViewAdapter.GroupListInteractionListener,
        CreateGroupFragment.CreateGroupInteractionListener {

    final String TAG = getClass().getSimpleName();
    private FragmentManager fragmentManager;
    private Fragment allGroupFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_groups);

        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Все группы");

        allGroupFragment = AllGroupFragment.newInstance(1);
        fragmentManager = getSupportFragmentManager();

        showFragment(allGroupFragment);
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void addGroup(GroupItem group) {
        App.getAppInstance()
                .getApi()
                .joinGroup(App.getAppInstance().getPreferences().getToken(), group.getIdLong())
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    @Override
    public void removeGroup(GroupItem group) {
        App.getAppInstance()
                .getApi()
                .leaveGroup(App.getAppInstance().getPreferences().getToken(), group.getIdLong())
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    @Override
    public void showGroupDetails(GroupItem group) {
        if (!(App.getAppInstance().isTeacher())) return;

        Intent intent = new Intent(this, GroupDetailsActivity.class);
        intent.putExtra(GroupDetailsActivity.ARG_GROUPID, group.getIdLong());

        startActivity(intent);
    }

    @Override
    public void onGroupCreated() {
        //mNavigation.setSelectedItemId(R.id.groups_navigation_my_groups);
    }
}
