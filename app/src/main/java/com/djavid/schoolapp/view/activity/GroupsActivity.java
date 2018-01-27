package com.djavid.schoolapp.view.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.djavid.schoolapp.R;

public class GroupsActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.groups_navigation_all_groups:
                    mTextMessage.setText(R.string.all_groups);
                    return true;
                case R.id.groups_navigation_my_groups:
                    mTextMessage.setText(R.string.my_groups);
                    return true;
                case R.id.groups_navigation_create_group:
                    mTextMessage.setText(R.string.create_group);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        mTextMessage = (TextView) findViewById(R.id.message);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        MenuItem createGroupItem = navigation.findViewById(R.id.groups_navigation_create_group);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        createGroupItem.setVisible(getPreferences(MODE_PRIVATE).getInt("level", 0) > 1);
    }

}
