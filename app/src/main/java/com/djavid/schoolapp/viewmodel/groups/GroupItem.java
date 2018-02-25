package com.djavid.schoolapp.viewmodel.groups;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.djavid.schoolapp.BR;
import com.djavid.schoolapp.model.groups.Group;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by Andrei on 25-Feb-18.
 */

public class GroupItem extends BaseObservable {
    protected final Group group;
    private final boolean _isEntered;

    public GroupItem() {
        this(new Group(), false);
    }

    public GroupItem(Group group) {
        this(group, false);
    }

    public GroupItem(Group group, boolean isEntered) {
        this.group = group;
        this._isEntered = isEntered;
    }

    public long getIdLong() {
        return group.id;
    }

    @Bindable
    public String getId() {
        return Long.toString(group.id);
    }

    public void setId(String id) {
        group.id = Long.parseLong(id);
        notifyPropertyChanged(BR.id);
    }

    @Bindable
    public String getTitle() {
        return group.title;
    }

    public void setTitle(String title) {
        group.title = title;
        notifyPropertyChanged(BR.title);
        notifyPropertyChanged(BR.valid);
    }

    @Bindable
    public boolean isValid() {
        return getTitle() != null
                && !getTitle().isEmpty();
    }

    @Bindable
    public int getAddButtonVisibility() {
        return isEntered()
                ? GONE
                : VISIBLE;
    }

    @Bindable
    public int getRemoveButtonVisibility() {
        return isEntered()
                ? VISIBLE
                : GONE;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GroupItem groupItem = (GroupItem) o;

        return group.equals(groupItem.group);
    }

    @Override
    public int hashCode() {
        return group.hashCode();
    }

    public GroupItem withEntered(boolean isEntered) {
        return new GroupItem(this.group, isEntered);

    }

    @Bindable
    public boolean isEntered() {
        return _isEntered;
    }
}
