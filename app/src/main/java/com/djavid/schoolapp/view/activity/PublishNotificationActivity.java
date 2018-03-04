package com.djavid.schoolapp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.djavid.schoolapp.App;
import com.djavid.schoolapp.R;
import com.djavid.schoolapp.model.notifications.Notification;
import com.djavid.schoolapp.view.fragment.notifications.NotificationFragmentBase;
import com.djavid.schoolapp.view.fragment.notifications.NotificationGroupItemFragment;
import com.djavid.schoolapp.view.fragment.notifications.PublishNotificationFragment;
import com.djavid.schoolapp.viewmodel.notifications.NotificationGroupItem;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

public class PublishNotificationActivity extends AppCompatActivity implements NotificationFragmentBase.NotificationFragmentContext, View.OnClickListener {
    private static final String ARG_NOTIFICATION = "notification";

    private Notification notification = new Notification();

    private BehaviorSubject<Long> groupChangedEvent = BehaviorSubject.create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            notification = (Notification) savedInstanceState.getSerializable(ARG_NOTIFICATION);
        }

        setContentView(R.layout.activity_publish_notification);

        showFragment(new PublishNotificationFragment());
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.notificationFragment, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(ARG_NOTIFICATION, notification);
    }

    @Override
    public void selectGroups(Notification notification) {
        showFragment(new NotificationGroupItemFragment());
    }

    @Override
    public void publish(Notification notification) {
        App.getAppInstance().getApi().publishNotification(
                App.getAppInstance().getPreferences().getToken(),
                notification.text,
                "0 0 * * *",
                true,
                notification.groups
        )
                .subscribeOn(Schedulers.io())
                .subscribe();
    }

    @Override
    public void onGroupChanged(long groupId) {
        groupChangedEvent.onNext(groupId);
    }

    @Override
    public Observable<Long> getGroupChangedEvent() {
        return groupChangedEvent;
    }

    @Override
    public void openGroupDetails(NotificationGroupItem group) {
        Intent intent = new Intent(this, GroupDetailsActivity.class);
        intent.putExtra(GroupDetailsActivity.ARG_GROUPID, group.group.getIdLong());

        startActivity(intent);
    }

    @Override
    public Notification getNotification() {
        return notification;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.confirm) {
            getSupportFragmentManager().popBackStackImmediate();
        }
    }
}
