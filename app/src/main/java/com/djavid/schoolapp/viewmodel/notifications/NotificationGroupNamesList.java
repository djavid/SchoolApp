package com.djavid.schoolapp.viewmodel.notifications;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.annimon.stream.Stream;
import com.djavid.schoolapp.BR;
import com.djavid.schoolapp.rest.Api;

import java.util.LinkedList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Andrei on 25-Feb-18.
 */
public class NotificationGroupNamesList extends BaseObservable {

    private List<NotificationGroupItem> groups = new LinkedList<>();

    public NotificationGroupNamesList(Observable<NotificationGroupItem> groups) {
        groups
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::addItem, Api::HandleError);
    }

    private void addItem(NotificationGroupItem s) {
        groups.add(s);
        notifyPropertyChanged(BR.string);
    }

    @Bindable
    public String getString() {
        String s = Stream.of(groups)
                .filter(g -> g.isGroupBelongsToNotification())
                .map(g -> g.group.getTitle())
                .reduce("", (a, t) -> a + ", " + t);
        if (s.length() < 1) {
            return s;
        } else {
            return s.substring(1);
        }
    }
}
