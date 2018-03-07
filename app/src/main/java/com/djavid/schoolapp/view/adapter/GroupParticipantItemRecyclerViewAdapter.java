package com.djavid.schoolapp.view.adapter;

import android.support.v7.util.ListUpdateCallback;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.djavid.schoolapp.databinding.FragmentGroupparticipantitemBinding;
import com.djavid.schoolapp.rest.Api;
import com.djavid.schoolapp.view.fragment.group_details.GroupParticipantItemFragment;
import com.djavid.schoolapp.viewmodel.group_details.GroupParticipantItem;
import com.djavid.schoolapp.viewmodel.group_details.GroupParticipantItemList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class GroupParticipantItemRecyclerViewAdapter extends RecyclerView.Adapter<GroupParticipantItemRecyclerViewAdapter.ViewHolder> {

    private SortedList<GroupParticipantItem> mValues = new GroupParticipantItemList(new ListUpdateCallback() {
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

    private final GroupParticipantItemFragment.GroupParticipantListInteractionListener mListener;

    public GroupParticipantItemRecyclerViewAdapter(Observable<GroupParticipantItem> groupParticipants, GroupParticipantItemFragment.GroupParticipantListInteractionListener listener) {
        groupParticipants
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::addItem, Api::HandleError);

        mListener = listener;
    }

    private void addItem(GroupParticipantItem item) {
        mValues.add(item);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(
                FragmentGroupparticipantitemBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent,
                        false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.binding.setParticipant(mValues.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final FragmentGroupparticipantitemBinding binding;

        public ViewHolder(FragmentGroupparticipantitemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
