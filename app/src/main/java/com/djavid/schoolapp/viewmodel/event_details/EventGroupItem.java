package com.djavid.schoolapp.viewmodel.event_details;

import com.djavid.schoolapp.model.events.Event;
import com.djavid.schoolapp.model.groups.Group;
import com.djavid.schoolapp.model.users.User;

import java.util.List;

import io.reactivex.Single;

/**
 * @author Andrei Kolomiets
 */
public class EventGroupItem {
    public final long id;
    public final String content;
    public final boolean isMy;

    public final Group group;
    public final Event event;

    public EventGroupItem(Group group, Event event, boolean isMy) {
        this.group = group;
        this.event = event;

        this.id = group.id;
        this.content = group.title;
        this.isMy = isMy;
    }

    public EventGroupItem withMy(boolean my){
        return new EventGroupItem(this.group, this.event, my);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        EventGroupItem that = (EventGroupItem) o;

        if (isMy != that.isMy) return false;
        if (!group.equals(that.group)) return false;
        return event.equals(that.event);
    }

    @Override
    public int hashCode() {
        int result = group.hashCode();
        result = 31 * result + event.hashCode();
        return result;
    }
}
