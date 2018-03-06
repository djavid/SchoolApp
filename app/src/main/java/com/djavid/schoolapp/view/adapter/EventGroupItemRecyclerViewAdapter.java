package com.djavid.schoolapp.view.adapter;

import android.support.v7.util.ListUpdateCallback;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.djavid.schoolapp.databinding.FragmentEventgroupitemBinding;
import com.djavid.schoolapp.view.fragment.event_details.EventGroupItemFragment;
import com.djavid.schoolapp.viewmodel.event_details.EventGroupItem;
import com.djavid.schoolapp.viewmodel.event_details.EventGroupItemList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class EventGroupItemRecyclerViewAdapter extends RecyclerView.Adapter<EventGroupItemRecyclerViewAdapter.ViewHolder> {

    private SortedList<EventGroupItem> mValues = new EventGroupItemList(new ListUpdateCallback() {
        @Override
        public void onInserted(int position, int count) {
            notifyItemRangeInserted(position, count);
        }

        @Override
        public void onRemoved(int position, int count) {
            notifyItemRangeRemoved(position, count);
        }

        @Override
        public void onMoved(int fromPosition, int toPosition) {
            notifyItemMoved(fromPosition, toPosition);
        }

        @Override
        public void onChanged(int position, int count, Object payload) {
            notifyItemRangeChanged(position, count);
        }
    });

    private final EventGroupItemFragment.EventGroupInteractionListener mListener;

    public EventGroupItemRecyclerViewAdapter(Observable<EventGroupItem> groups, EventGroupItemFragment.EventGroupInteractionListener listener) {
        groups.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::addMyGroupItem);

        mListener = listener;
    }

    private void addMyGroupItem(EventGroupItem item) {
        mValues.add(item);
    }

    private void setGroupItem(EventGroupItem item) {
        mValues.updateItemAt(mValues.indexOf(item), item);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(
                FragmentEventgroupitemBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent,
                        false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.binding.setGroup(mValues.get(position));
        holder.binding.setPresenter(this);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void onAddClick(EventGroupItem group){
        setGroupItem(group.withMy(true));
        this.mListener.addEventGroup(group);
    }

    public void onRemoveClick(EventGroupItem group){
        setGroupItem(group.withMy(false));
        this.mListener.removeEventGroup(group);
    }

    public void onGroupClick(EventGroupItem group){
        this.mListener.openGroupDetails(group);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final FragmentEventgroupitemBinding binding;

        public ViewHolder(FragmentEventgroupitemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
