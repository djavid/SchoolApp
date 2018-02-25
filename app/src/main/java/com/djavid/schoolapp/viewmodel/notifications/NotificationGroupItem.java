package com.djavid.schoolapp.viewmodel.notifications;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.djavid.schoolapp.BR;
import com.djavid.schoolapp.viewmodel.groups.GroupItem;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by Andrei on 25-Feb-18.
 */

public class NotificationGroupItem extends BaseObservable {
    public final NotificationItem notification;
    public final GroupItem group;


    public NotificationGroupItem() {
        this(new NotificationItem(), new GroupItem());
    }

    public NotificationGroupItem(NotificationItem notification, GroupItem group) {
        this(notification, group, false);
    }

    public NotificationGroupItem(NotificationItem notification, GroupItem group, boolean isGroupBelongsToNotification) {
        this.notification = notification;
        this.group = group;
    }

    @Bindable
    public boolean isGroupBelongsToNotification() {
        return notification.notification.groups.contains(group.getIdLong());
    }

    public void setGroupBelongsToNotification(boolean isGroupBelongsToNotification) {
        if (isGroupBelongsToNotification) {
            notification.notification.groups.add(group.getIdLong());
        } else {
            notification.notification.groups.remove(group.getIdLong());
        }
        notifyPropertyChanged(BR.groupBelongsToNotification);
        notifyPropertyChanged(BR.addButtonVisibility);
        notifyPropertyChanged(BR.removeButtonVisibility);
    }

    @Bindable
    public int getAddButtonVisibility() {
        return isGroupBelongsToNotification()
                ? GONE
                : VISIBLE;
    }

    @Bindable
    public int getRemoveButtonVisibility() {
        return isGroupBelongsToNotification()
                ? VISIBLE
                : GONE;
    }
}
