package com.djavid.schoolapp.viewmodel.group_details;

import android.support.v7.util.ListUpdateCallback;
import android.support.v7.util.SortedList;

/**
 * Created by Andrei on 25-Feb-18.
 */

public class GroupParticipantItemList extends SortedList<GroupParticipantItem> {
    public GroupParticipantItemList(ListUpdateCallback listUpdateCallback) {
        super(GroupParticipantItem.class, new Callback<GroupParticipantItem>() {
            @Override
            public int compare(GroupParticipantItem o1, GroupParticipantItem o2) {
                if (o1 == null && o2 == null) {
                    return 0;
                }
                if (o1 == null) {
                    return 1;
                }
                if (o2 == null) {
                    return -1;
                }
                return o1.participant.getNick().compareTo(o2.participant.getNick());

            }

            @Override
            public void onChanged(int position, int count) {
                listUpdateCallback.onChanged(position, count, new Object());
            }

            @Override
            public boolean areContentsTheSame(GroupParticipantItem oldItem, GroupParticipantItem newItem) {
                return false;
            }

            @Override
            public boolean areItemsTheSame(GroupParticipantItem item1, GroupParticipantItem item2) {
                return item1 != null && item1.equals(item2);
            }

            @Override
            public void onInserted(int position, int count) {
                listUpdateCallback.onInserted(position, count);
            }

            @Override
            public void onRemoved(int position, int count) {
                listUpdateCallback.onRemoved(position, count);
            }

            @Override
            public void onMoved(int fromPosition, int toPosition) {
                listUpdateCallback.onMoved(fromPosition, toPosition);
            }
        });
    }
}
