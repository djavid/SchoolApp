package com.djavid.schoolapp.view.adapter;

import android.support.v7.util.ListUpdateCallback;
import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.annimon.stream.Stream;
import com.djavid.schoolapp.databinding.FragmentMyeventsBinding;
import com.djavid.schoolapp.rest.Api;
import com.djavid.schoolapp.view.fragment.events.MyEventsFragment;
import com.djavid.schoolapp.viewmodel.events.EventItem;
import com.djavid.schoolapp.viewmodel.events.EventItemList;

import java.util.LinkedList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class MyEventsRecyclerViewAdapter extends RecyclerView.Adapter<MyEventsRecyclerViewAdapter.ViewHolder> {

    private EventItemList mValues = new EventItemList(new ListUpdateCallback() {
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
    private final MyEventsFragment.OnListFragmentInteractionListener mListener;

    protected List<EventItem> allValues = new LinkedList<>();

    public MyEventsRecyclerViewAdapter(Observable<EventItem> myEvents, MyEventsFragment.OnListFragmentInteractionListener listener) {
        mListener = listener;

        myEvents
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::addEvent, Api::HandleError);
    }

    private void addEvent(EventItem item) {
        allValues.add(item);
        notifyItemInserted(mValues.add(item));
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentMyeventsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.binding.setEvent(mValues.get(position));
        holder.binding.setPresenter(this);
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void onClick(EventItem event) {
        mListener.openEventDetails(event);
    }

    public void onSearchChanged(CharSequence seq, int start, int before, int count) {
        String s = seq.toString().toLowerCase();
        if (TextUtils.isEmpty(s.trim())) {
            mValues.clear();
            mValues.addAll(allValues);
        } else {
            Stream<EventItem> filtered = Stream.of(allValues)
                    .filter(q -> q.getTitle().toLowerCase().contains(s));
            List<EventItem> newList = filtered.toList();

            if (newList.size() == mValues.size() &&
                    filtered.allMatch(q -> mValues.indexOf(q) != SortedList.INVALID_POSITION)) {
                return;
            }

            mValues.clear();
            mValues.addAll(newList);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final FragmentMyeventsBinding binding;

        ViewHolder(FragmentMyeventsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
