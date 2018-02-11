package com.djavid.schoolapp.viewmodel.event_details;

import com.djavid.schoolapp.model.dto.groups.Group;
import com.djavid.schoolapp.model.dto.users.User;

import java.util.List;

import io.reactivex.Single;

/**
 * @author Andrei Kolomiets
 */
public class EventGroupItem {
    public final long id;
    public final String content;
    public final Single<List<User>> participants;

    private final Group group;

    public EventGroupItem(Group group, Single<List<User>> participants) {
        this.group = group;
        this.participants = participants;

        this.id = group.id;
        this.content = group.title;
    }
}
