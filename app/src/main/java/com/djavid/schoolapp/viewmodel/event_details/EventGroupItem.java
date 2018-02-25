package com.djavid.schoolapp.viewmodel.event_details;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.djavid.schoolapp.viewmodel.events.EventItem;
import com.djavid.schoolapp.viewmodel.groups.GroupItem;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * @author Andrei Kolomiets
 */
public class EventGroupItem extends BaseObservable {
    public final GroupItem group;
    public final EventItem event;
    private final boolean _isGroupBelongsToEvent;

    public EventGroupItem(GroupItem group, EventItem event, boolean isGroupBelongsToEvent) {
        this.group = group;
        this.event = event;
        _isGroupBelongsToEvent = isGroupBelongsToEvent;
    }

    public EventGroupItem withMy(boolean my){
        return new EventGroupItem(this.group, this.event, my);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventGroupItem that = (EventGroupItem) o;

        if (!group.equals(that.group)) return false;
        return event.equals(that.event);
    }

    @Bindable
    public boolean isGroupBelongsToEvent() {
        return _isGroupBelongsToEvent;
    }

    @Bindable
    public int getAddButtonVisibility() {
        return !event.isCreated
                || _isGroupBelongsToEvent
                ? GONE
                : VISIBLE;
    }

    @Bindable
    public int getRemoveButtonVisibility() {
        return event.isCreated
                && _isGroupBelongsToEvent
                ? VISIBLE
                : GONE;
    }

    @Override
    public int hashCode() {
        int result = group.hashCode();
        result = 31 * result + event.hashCode();
        return result;
    }
}
