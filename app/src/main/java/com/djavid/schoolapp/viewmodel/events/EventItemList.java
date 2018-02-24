package com.djavid.schoolapp.viewmodel.events;

import android.support.v7.util.ListUpdateCallback;
import android.support.v7.util.SortedList;

/**
 * Created by Andrei on 25-Feb-18.
 */

public class EventItemList extends SortedList<EventItem> {
    public EventItemList(ListUpdateCallback listUpdateCallback) {
        super(EventItem.class, new Callback<EventItem>() {
            @Override
            public int compare(EventItem o1, EventItem o2) {
                if (o1 == null && o2 == null){
                    return 0;
                }
                if (o1 == null){
                    return -1;
                }
                if (o2 == null){
                    return 1;
                }
                if (o2.isCreated && !o1.isCreated){
                    return 1;
                }
                if (o1.isCreated && !o2.isCreated){
                    return -1;
                }
                if (o2.isEntered && !o1.isEntered){
                    return 1;
                }
                if (o1.isEntered && !o2.isEntered){
                    return -1;
                }
                return Long.compare(o1.getIdLong(), o2.getIdLong());
            }

            @Override
            public void onChanged(int position, int count) {
                listUpdateCallback.onChanged(position, count, new Object());
            }

            @Override
            public boolean areContentsTheSame(EventItem oldItem, EventItem newItem) {
                return false;
            }

            @Override
            public boolean areItemsTheSame(EventItem item1, EventItem item2) {
                if (item1 == null && item2 == null){
                    return true;
                }
                return item1 != null && item2!=null
                        && (item1.getIdLong() == item2.getIdLong());
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
