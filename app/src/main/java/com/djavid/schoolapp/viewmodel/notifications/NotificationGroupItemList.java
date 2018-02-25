package com.djavid.schoolapp.viewmodel.notifications;

import android.support.v7.util.ListUpdateCallback;
import android.support.v7.util.SortedList;

/**
 * Created by Andrei on 25-Feb-18.
 */

public class NotificationGroupItemList extends SortedList<NotificationGroupItem> {
    public NotificationGroupItemList(ListUpdateCallback listUpdateCallback) {
        super(NotificationGroupItem.class, new Callback<NotificationGroupItem>() {
            @Override
            public int compare(NotificationGroupItem o1, NotificationGroupItem o2) {
                if (o1.isGroupBelongsToNotification() && !o2.isGroupBelongsToNotification()) {
                    return -1;
                }
                if (o2.isGroupBelongsToNotification() && !o1.isGroupBelongsToNotification()) {
                    return 1;
                }
                return Long.compare(o1.group.getIdLong(), o2.group.getIdLong());
            }

            @Override
            public void onChanged(int position, int count) {
                listUpdateCallback.onChanged(position, count, new Object());
            }

            @Override
            public boolean areContentsTheSame(NotificationGroupItem oldItem, NotificationGroupItem newItem) {
                return false;
            }

            @Override
            public boolean areItemsTheSame(NotificationGroupItem item1, NotificationGroupItem item2) {
                return item1.group.getIdLong() == item2.group.getIdLong();
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
