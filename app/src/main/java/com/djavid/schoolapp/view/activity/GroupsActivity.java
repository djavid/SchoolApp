package com.djavid.schoolapp.view.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.djavid.schoolapp.R;

public class GroupsActivity extends AppCompatActivity {

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            invalidateOptionsMenu();
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
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups);

        mTextMessage = findViewById(R.id.message);

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        navigation.setSelectedItemId(0);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuView.ItemView createGroupItem = findViewById(R.id.groups_navigation_create_group);
        createGroupItem.setEnabled(getPreferences(MODE_PRIVATE).getInt("level", 0) > 1);

        return true;
    }

}
