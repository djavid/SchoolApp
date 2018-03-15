package com.djavid.schoolapp.view.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.djavid.schoolapp.R;
import com.djavid.schoolapp.view.fragment.groups.GroupDetailsFragment;

public class GroupDetailsActivity extends AppCompatActivity implements
        GroupDetailsFragment.GroupParticipantListInteractionListener {

    public static final String ARG_GROUP_ID = "groupId";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_details);

        long mGroupId = getIntent().getLongExtra(ARG_GROUP_ID, 0);
        if (mGroupId == 0) {
            onBackPressed();
            return;
        }

        showFragment(GroupDetailsFragment.newInstance(mGroupId));
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        return true;
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.groupDetailsFragment, fragment)
                .commit();
    }
}
