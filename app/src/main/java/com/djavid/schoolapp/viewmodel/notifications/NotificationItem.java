package com.djavid.schoolapp.viewmodel.notifications;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.support.v7.widget.AppCompatSpinner;
import android.view.View;
import android.widget.AdapterView;

import com.djavid.schoolapp.BR;
import com.djavid.schoolapp.model.notifications.Notification;
import com.djavid.schoolapp.model.notifications.RepeatInfo;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by Andrei on 25-Feb-18.
 */

public class NotificationItem extends BaseObservable implements AppCompatSpinner.OnItemSelectedListener {
    public Notification notification;

    private RepeatInfo repeatInfo;

    public NotificationItem() {
        this(new Notification());
    }

    public NotificationItem(Notification notification) {
        this.notification = notification;
        this.repeatInfo = notification.getRepeatInfo();
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

    @Bindable
    public boolean isNever() {
        return repeatInfo.mode == RepeatInfo.Mode.Never;
    }

    @Bindable
    public void setNever(boolean isNever) {
        if (isNever) {
            repeatInfo.mode = RepeatInfo.Mode.Never;
            notification.setRepeatInfo(repeatInfo);
            notifyPropertyChanged(BR.never);
            notifyPropertyChanged(BR.weekly);
            notifyPropertyChanged(BR.monthly);
        }
    }

    @Bindable
    public boolean isWeekly() {
        return repeatInfo.mode == RepeatInfo.Mode.DailyWeek;
    }

    @Bindable
    public void setWeekly(boolean isWeekly) {
        if (isWeekly) {
            repeatInfo.mode = RepeatInfo.Mode.DailyWeek;
            notification.setRepeatInfo(repeatInfo);
            notifyPropertyChanged(BR.never);
            notifyPropertyChanged(BR.weekly);
            notifyPropertyChanged(BR.monthly);
        }
    }

    @Bindable
    public boolean isMonthly() {
        return repeatInfo.mode == RepeatInfo.Mode.DailyMonth;
    }

    @Bindable
    public void setMonthly(boolean isMonthly) {
        if (isMonthly) {
            repeatInfo.mode = RepeatInfo.Mode.DailyMonth;
            notification.setRepeatInfo(repeatInfo);
            notifyPropertyChanged(BR.never);
            notifyPropertyChanged(BR.weekly);
            notifyPropertyChanged(BR.monthly);
        }
    }

    @Bindable
    public void setMode(RepeatInfo.Mode mode) {
        repeatInfo.mode = mode;
        notification.setRepeatInfo(repeatInfo);
        notifyPropertyChanged(BR.mode);
    }

    @Bindable
    public boolean isMonday() {
        return repeatInfo.daysOfWeek.contains(1);
    }

    @Bindable
    public void setMonday(boolean isMonday) {
        if (isMonday) {
            repeatInfo.daysOfWeek.add(1);
        } else {
            repeatInfo.daysOfWeek.remove(1);
        }
        notification.setRepeatInfo(repeatInfo);
        notifyPropertyChanged(BR.monday);
    }

    @Bindable
    public boolean isTuesday() {
        return repeatInfo.daysOfWeek.contains(2);
    }

    @Bindable
    public void setTuesday(boolean isMonday) {
        if (isMonday) {
            repeatInfo.daysOfWeek.add(2);
        } else {
            repeatInfo.daysOfWeek.remove(2);
        }
        notification.setRepeatInfo(repeatInfo);
        notifyPropertyChanged(BR.tuesday);
    }

    @Bindable
    public boolean isWednesday() {
        return repeatInfo.daysOfWeek.contains(3);
    }

    @Bindable
    public void setWednesday(boolean isMonday) {
        if (isMonday) {
            repeatInfo.daysOfWeek.add(3);
        } else {
            repeatInfo.daysOfWeek.remove(3);
        }
        notification.setRepeatInfo(repeatInfo);
        notifyPropertyChanged(BR.wednesday);
    }

    @Bindable
    public boolean isThursday() {
        return repeatInfo.daysOfWeek.contains(4);
    }

    @Bindable
    public void setThursday(boolean isMonday) {
        if (isMonday) {
            repeatInfo.daysOfWeek.add(4);
        } else {
            repeatInfo.daysOfWeek.remove(4);
        }
        notification.setRepeatInfo(repeatInfo);
        notifyPropertyChanged(BR.thursday);
    }

    @Bindable
    public boolean isFriday() {
        return repeatInfo.daysOfWeek.contains(5);
    }

    @Bindable
    public void setFriday(boolean isMonday) {
        if (isMonday) {
            repeatInfo.daysOfWeek.add(5);
        } else {
            repeatInfo.daysOfWeek.remove(5);
        }
        notification.setRepeatInfo(repeatInfo);
        notifyPropertyChanged(BR.friday);
    }

    @Bindable
    public boolean isSaturday() {
        return repeatInfo.daysOfWeek.contains(6);
    }

    @Bindable
    public void setSaturday(boolean isMonday) {
        if (isMonday) {
            repeatInfo.daysOfWeek.add(6);
        } else {
            repeatInfo.daysOfWeek.remove(6);
        }
        notification.setRepeatInfo(repeatInfo);
        notifyPropertyChanged(BR.saturday);
    }

    @Bindable
    public boolean isSunday() {
        return repeatInfo.daysOfWeek.contains(0) ||
                repeatInfo.daysOfWeek.contains(7);
    }

    @Bindable
    public void setSunday(boolean isMonday) {
        if (isMonday) {
            repeatInfo.daysOfWeek.add(0);
        } else {
            repeatInfo.daysOfWeek.remove(0);
            repeatInfo.daysOfWeek.remove(7);
        }
        notification.setRepeatInfo(repeatInfo);
        notifyPropertyChanged(BR.sunday);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        repeatInfo.daysOfMonth.clear();
        repeatInfo.daysOfMonth.add(i + 1);
        notification.setRepeatInfo(repeatInfo);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
