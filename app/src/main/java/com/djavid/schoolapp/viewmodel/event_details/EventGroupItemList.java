package com.djavid.schoolapp.viewmodel.event_details;

import android.support.v7.util.ListUpdateCallback;
import android.support.v7.util.SortedList;

/**
 * Created by Andrei on 24-Feb-18.
 */
public class EventGroupItemList extends SortedList<EventGroupItem> {
    public EventGroupItemList(ListUpdateCallback listUpdateCallback) {
        super(EventGroupItem.class, new Callback<EventGroupItem>() {
            @Override
            public int compare(EventGroupItem o1, EventGroupItem o2) {
                if (o2.isMy && !o1.isMy){
                    return 1;
                }
                if (!o2.isMy && o1.isMy){
                    return -1;
                }

                return Long.compare(o1.id, o2.id);
            }

            @Override
            public void onChanged(int position, int count) {
                listUpdateCallback.onChanged(position, count, new Object());
            }

            @Override
            public boolean areContentsTheSame(EventGroupItem oldItem, EventGroupItem newItem) {
                return false;
            }

            @Override
            public boolean areItemsTheSame(EventGroupItem item1, EventGroupItem item2) {
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
        }, 0);
    }
}
