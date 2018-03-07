package com.djavid.schoolapp.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.djavid.schoolapp.databinding.FragmentNotificationitemBinding;
import com.djavid.schoolapp.view.fragment.notifications.NotificationListFragment;
import com.djavid.schoolapp.viewmodel.notifications.NotificationItem;

import java.util.LinkedList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MyNotificationItemRecyclerViewAdapter extends RecyclerView.Adapter<MyNotificationItemRecyclerViewAdapter.ViewHolder> {

    private final List<NotificationItem> mValues = new LinkedList<>();
    private final NotificationListFragment.NotificationsInteractionListener mListener;

    public MyNotificationItemRecyclerViewAdapter(Observable<NotificationItem> items, NotificationListFragment.NotificationsInteractionListener listener) {
        mListener = listener;

        items.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::addItem);
    }

    private void addItem(NotificationItem item) {
        mValues.add(item);
        notifyItemInserted(mValues.size() - 1);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentNotificationitemBinding.inflate(LayoutInflater.from(parent.getContext()),
                parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.binding.setNotification(mValues.get(position));
        holder.binding.setPresenter(this);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public FragmentNotificationitemBinding binding;

        public ViewHolder(FragmentNotificationitemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
