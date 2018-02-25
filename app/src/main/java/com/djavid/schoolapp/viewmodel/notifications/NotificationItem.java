package com.djavid.schoolapp.viewmodel.notifications;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.djavid.schoolapp.BR;
import com.djavid.schoolapp.model.notifications.Notification;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by Andrei on 25-Feb-18.
 */

public class NotificationItem extends BaseObservable {
    public Notification notification;

    public NotificationItem() {
        this(new Notification());
    }

    public NotificationItem(Notification notification) {
        this.notification = notification;
    }

    @Bindable
    public String getText() {
        return notification.text;
    }

    public void setText(String text) {
        notification.text = text;
        notifyPropertyChanged(BR.text);
    }

    private int publishVisibility = VISIBLE;

    public void setPublished() {
        publishVisibility = GONE;
        notifyPropertyChanged(BR.publishVisibility);
        notifyPropertyChanged(BR.successVisibility);
    }

    @Bindable
    public int getPublishVisibility() {
        return publishVisibility;
    }

    @Bindable
    public int getSuccessVisibility() {
        return publishVisibility == VISIBLE
                ? GONE
                : VISIBLE;
    }

}
