package com.djavid.schoolapp.viewmodel.event_details;

import android.support.v7.util.ListUpdateCallback;
import android.support.v7.util.SortedList;

/**
 * Created by Andrei on 25-Feb-18.
 */

public class EventCommentItemList extends SortedList<EventCommentItem> {
    public EventCommentItemList(ListUpdateCallback listUpdateCallback) {
        super(EventCommentItem.class, new Callback<EventCommentItem>() {
            @Override
            public int compare(EventCommentItem o1, EventCommentItem o2) {
                if (o1 == null && o2 == null) {
                    return 0;
                }
                if (o1 == null) {
                    return 1;
                }
                if (o2 == null) {
                    return -1;
                }
                return o2.getTimeStampCalendar().compareTo(o1.getTimeStampCalendar());
            }

            @Override
            public void onChanged(int position, int count) {
                listUpdateCallback.onChanged(position, count, new Object());
            }

            @Override
            public boolean areContentsTheSame(EventCommentItem oldItem, EventCommentItem newItem) {
                return false;
            }

            @Override
            public boolean areItemsTheSame(EventCommentItem item1, EventCommentItem item2) {
                return item1.comment.id == item2.comment.id;
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
