package com.djavid.schoolapp.viewmodel.groups;

import com.djavid.schoolapp.model.groups.Group;

/**
 * @author Andrei Kolomiets
 */
public class AllGroupItem {
    public final long id;
    public final String content;

    public boolean isMyGroup;

    private final Group group;

    public AllGroupItem(Group group, boolean isMyGroup) {
        this.group = group;

        this.id = group.id;
        this.content = group.title;
        this.isMyGroup = isMyGroup;
    }

    @Override
    public String toString() {
        return content;
    }
}