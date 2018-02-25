package com.djavid.schoolapp.view.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.djavid.schoolapp.R;
import com.djavid.schoolapp.view.fragment.group_details.AboutGroupFragment;
import com.djavid.schoolapp.view.fragment.group_details.GroupParticipantItemFragment;

public class GroupDetailsActivity extends AppCompatActivity implements GroupParticipantItemFragment.GroupParticipantListInteractionListener, AboutGroupFragment.AboutGroupFragmentInteractionListener {

    public static final String ARG_GROUPID = "groupId";
    private long mGroupId;


    BottomNavigationView mNavigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_about_group:
                    showFragment(AboutGroupFragment.newInstance(mGroupId));
                    return true;
                case R.id.navigation_group_participants:
                    showFragment(GroupParticipantItemFragment.newInstance(mGroupId));
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_details);

        mGroupId = getIntent().getLongExtra(ARG_GROUPID, 0);
        if (mGroupId == 0) {
            onBackPressed();
            return;
        }

        mNavigation = findViewById(R.id.navigation);
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        mNavigation.setSelectedItemId(R.id.navigation_about_group);
        showFragment(AboutGroupFragment.newInstance(mGroupId));
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        return true;
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.groupDetailsFragment, fragment)
                .commit();
    }
}
