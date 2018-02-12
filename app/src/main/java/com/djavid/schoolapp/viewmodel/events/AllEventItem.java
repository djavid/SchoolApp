package com.djavid.schoolapp.viewmodel.events;

import com.djavid.schoolapp.model.events.Event;

/**
 * @author Andrei Kolomiets
 */
public class AllEventItem extends EventItem {
    public boolean isMyEvent;

    public AllEventItem(Event event, boolean isMyEvent) {
        super(event);
        this.isMyEvent = isMyEvent;
    }
}
