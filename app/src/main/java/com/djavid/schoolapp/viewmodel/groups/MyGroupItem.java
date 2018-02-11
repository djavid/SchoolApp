package com.djavid.schoolapp.viewmodel.groups;

import com.djavid.schoolapp.model.groups.Group;

/**
 * @author Andrei Kolomiets
 */
public class MyGroupItem {
    public final long id;
    public final String content;

    private final Group group;

    public MyGroupItem(Group group) {
        this.group = group;

        this.id = group.id;
        this.content = group.title;
    }

    @Override
    public String toString() {
        return content;
    }
}
