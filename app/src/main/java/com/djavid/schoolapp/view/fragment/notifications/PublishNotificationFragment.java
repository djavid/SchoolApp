package com.djavid.schoolapp.view.fragment.notifications;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annimon.stream.Stream;
import com.djavid.schoolapp.App;
import com.djavid.schoolapp.BR;
import com.djavid.schoolapp.R;
import com.djavid.schoolapp.databinding.FragmentPublishNotificationBinding;
import com.djavid.schoolapp.viewmodel.groups.GroupItem;
import com.djavid.schoolapp.viewmodel.notifications.NotificationGroupItem;
import com.djavid.schoolapp.viewmodel.notifications.NotificationGroupNamesList;
import com.djavid.schoolapp.viewmodel.notifications.NotificationItem;

import io.reactivex.Observable;

public class PublishNotificationFragment extends NotificationFragmentBase {
    public PublishNotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentPublishNotificationBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_publish_notification, container, false);
        if (notification == null) {
            Log.e("DBG", "notification == null");
        }

        binding.setNotification(notification);
        binding.setPresenter(this);
        binding.setGroups(new NotificationGroupNamesList(
                App.getAppInstance().getApi().getAllGroups(
                        App.getAppInstance().getPreferences().getToken()
                ).flatMapObservable(allGroups -> Observable.fromIterable(Stream.of(allGroups).toList())
                        .map(group -> new NotificationGroupItem(
                                notification,
                                new GroupItem(group),
                                notification.notification.groups.contains(group.id))))));
        groupChangedEvent.subscribe(q -> {
            binding.getGroups().notifyPropertyChanged(BR.string);
            binding.executePendingBindings();
        });
        binding.executePendingBindings();

        return binding.getRoot();
    }

    public void selectGroups(NotificationItem item) {
        mContext.selectGroups(item.notification);
    }

    public void publish(NotificationItem item) {
        mContext.publish(item.notification);
        item.setPublished();
    }
}
