package com.djavid.schoolapp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.djavid.schoolapp.App;
import com.djavid.schoolapp.R;
import com.djavid.schoolapp.model.notifications.Notification;
import com.djavid.schoolapp.rest.Api;
import com.djavid.schoolapp.view.fragment.notifications.NotificationFragmentBase;
import com.djavid.schoolapp.view.fragment.notifications.NotificationGroupItemFragment;
import com.djavid.schoolapp.view.fragment.notifications.PublishNotificationFragment;
import com.djavid.schoolapp.viewmodel.notifications.NotificationGroupItem;

import java.util.Calendar;

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

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.notificationFragment, new PublishNotificationFragment())
                .commit();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_right);
    }

    private void showFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.notificationFragment, fragment)
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
        Calendar until = Calendar.getInstance();
        until.add(Calendar.YEAR, 1);
        App.getAppInstance().getApi().publishNotification(
                App.getAppInstance().getPreferences().getToken(),
                Notification.addAuthor(notification.text,
                        App.getAppInstance().getPreferences().getDisplayName()),
                notification.frequency,
                notification.noRepeat,
                notification.groups,
                Api.Date(until)
        )
                .subscribeOn(Schedulers.io())
                .subscribe(q -> {
                }, Api::HandleError);
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
        intent.putExtra(GroupDetailsActivity.ARG_GROUP_ID, group.group.getIdLong());

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
