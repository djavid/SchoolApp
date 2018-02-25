package com.djavid.schoolapp.viewmodel.group_details;

import android.databinding.BaseObservable;

import com.djavid.schoolapp.viewmodel.groups.GroupItem;

/**
 * Created by Andrei on 25-Feb-18.
 */

public class GroupParticipantItem extends BaseObservable {
    public final GroupItem group;
    public final UserItem participant;

    public GroupParticipantItem() {
        this(new GroupItem(), new UserItem());
    }

    public GroupParticipantItem(GroupItem group) {
        this(group, new UserItem());
    }

    public GroupParticipantItem(GroupItem group, UserItem user) {
        this.group = group;
        this.participant = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GroupParticipantItem that = (GroupParticipantItem) o;

        if (!group.equals(that.group)) return false;
        return participant.equals(that.participant);
    }

    @Override
    public int hashCode() {
        int result = group.hashCode();
        result = 31 * result + participant.hashCode();
        return result;
    }
}
