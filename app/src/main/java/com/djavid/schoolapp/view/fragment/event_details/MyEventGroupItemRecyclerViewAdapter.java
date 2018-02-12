package com.djavid.schoolapp.view.fragment.event_details;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.djavid.schoolapp.R;
import com.djavid.schoolapp.databinding.FragmentEventgroupitemBinding;
import com.djavid.schoolapp.model.events.Event;
import com.djavid.schoolapp.view.fragment.event_details.EventGroupItemFragment.OnListFragmentInteractionListener;
import com.djavid.schoolapp.viewmodel.event_details.EventGroupItem;

import java.util.LinkedList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MyEventGroupItemRecyclerViewAdapter extends RecyclerView.Adapter<MyEventGroupItemRecyclerViewAdapter.ViewHolder> {

    private List<EventGroupItem> mValues = new LinkedList<>();
    private int mInsertIdx = 0;
    private Event mEvent = new Event();
    private final OnListFragmentInteractionListener mListener;

    public MyEventGroupItemRecyclerViewAdapter(Observable<EventGroupItem> eventGroups, Single<Event> event, OnListFragmentInteractionListener listener) {
        event.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(e -> mEvent = e);
        eventGroups.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::addGroupItem);

        mListener = listener;
    }

    private void addGroupItem(EventGroupItem item) {
        mValues.add(item);
        notifyItemInserted(mInsertIdx++);
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
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final FragmentEventgroupitemBinding binding;
        final RecyclerView recyclerView;

        public ViewHolder(FragmentEventgroupitemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            this.recyclerView = binding.getRoot().findViewById(R.id.event_group_item_participants_list);
            this.recyclerView.setAdapter(new ParticipantsRecyclerViewAdapter(
                    binding
            ));
        }
    }
}
