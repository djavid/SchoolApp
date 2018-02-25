package com.djavid.schoolapp.viewmodel.groups;

import android.support.v7.util.ListUpdateCallback;
import android.support.v7.util.SortedList;

/**
 * Created by Andrei on 25-Feb-18.
 */

public class GroupItemList extends SortedList<GroupItem> {
    public GroupItemList(ListUpdateCallback listUpdateCallback) {
        super(GroupItem.class, new Callback<GroupItem>() {
            @Override
            public int compare(GroupItem o1, GroupItem o2) {
                if (o1 == null && o2 == null) {
                    return 0;
                }
                if (o1 == null) {
                    return 1;
                }
                if (o2 == null) {
                    return -1;
                }
                return Long.compare(o1.getIdLong(), o2.getIdLong());
            }

            @Override
            public void onChanged(int position, int count) {
                listUpdateCallback.onChanged(position, count, new Object());
            }

            @Override
            public boolean areContentsTheSame(GroupItem oldItem, GroupItem newItem) {
                return false;
            }

            @Override
            public boolean areItemsTheSame(GroupItem item1, GroupItem item2) {
                if (item1 == null && item2 == null) {
                    return true;
                }
                return item1 != null && item2 != null && item1.getId().equals(item2.getId());
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
