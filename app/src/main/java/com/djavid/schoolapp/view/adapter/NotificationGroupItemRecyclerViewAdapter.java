package com.djavid.schoolapp.view.adapter;

import android.support.v7.util.ListUpdateCallback;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.djavid.schoolapp.databinding.FragmentNotificationgroupitemBinding;
import com.djavid.schoolapp.model.notifications.Notification;
import com.djavid.schoolapp.rest.Api;
import com.djavid.schoolapp.view.fragment.notifications.NotificationFragmentBase;
import com.djavid.schoolapp.viewmodel.notifications.NotificationGroupItem;
import com.djavid.schoolapp.viewmodel.notifications.NotificationGroupItemList;
import com.djavid.schoolapp.viewmodel.notifications.NotificationItem;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class NotificationGroupItemRecyclerViewAdapter extends RecyclerView.Adapter<NotificationGroupItemRecyclerViewAdapter.ViewHolder> {

    private final NotificationGroupItemList mValues = new NotificationGroupItemList(new ListUpdateCallback() {
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
    private final NotificationFragmentBase.NotificationFragmentContext mListener;

    private final Notification notification;

    public NotificationGroupItemRecyclerViewAdapter(
            Observable<NotificationGroupItem> groups,
            NotificationItem notification,
            NotificationFragmentBase.NotificationFragmentContext listener) {
        this.notification = notification.notification;

        groups
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::addItem, Api::HandleError);
        mListener = listener;
    }

    private void addItem(NotificationGroupItem item) {
        mValues.add(item);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(
                FragmentNotificationgroupitemBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent,
                        false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.binding.setGroup(mValues.get(position));
        holder.binding.setPresenter(this);
        holder.binding.executePendingBindings();
    }

    public void onAddClick(NotificationGroupItem group) {
        this.notification.groups.add(group.group.getIdLong());
        group.setGroupBelongsToNotification(true);
        mListener.onGroupChanged(group.group.getIdLong());
    }

    public void onRemoveClick(NotificationGroupItem group) {
        this.notification.groups.remove(group.group.getIdLong());
        group.setGroupBelongsToNotification(false);
        mListener.onGroupChanged(group.group.getIdLong());
    }

    public void onGroupClick(NotificationGroupItem group) {
        mListener.openGroupDetails(group);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final FragmentNotificationgroupitemBinding binding;

        public ViewHolder(FragmentNotificationgroupitemBinding binding) {
            super(binding.getRoot());

            this.binding = binding;
        }
    }
}
