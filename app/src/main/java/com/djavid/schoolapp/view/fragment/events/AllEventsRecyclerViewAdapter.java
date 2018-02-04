package com.djavid.schoolapp.view.fragment.events;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.annimon.stream.Stream;
import com.djavid.schoolapp.databinding.FragmentAlleventsBinding;
import com.djavid.schoolapp.viewmodel.events.AllEventItem;
import com.djavid.schoolapp.viewmodel.events.MyEventItem;

import java.util.LinkedList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * {@link RecyclerView.Adapter} that can display a {@link AllEventItem} and makes a call to the
 * specified {@link AllEventsFragment.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class AllEventsRecyclerViewAdapter extends RecyclerView.Adapter<AllEventsRecyclerViewAdapter.ViewHolder> {

    private List<AllEventItem> mValues;
    private List<MyEventItem> mMyEvents;
    private final AllEventsFragment.OnListFragmentInteractionListener mListener;

    public AllEventsRecyclerViewAdapter(Single<List<AllEventItem>> items, Single<List<MyEventItem>> myItems, AllEventsFragment.OnListFragmentInteractionListener listener) {
        mValues = new LinkedList<>();
        mListener = listener;

        items.subscribeOn(Schedulers.io())
                .zipWith(myItems.subscribeOn(Schedulers.io()), (all, my) -> {
                    mMyEvents = my;
                    return all;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onItemsArrived);
    }

    private void onItemsArrived(List<AllEventItem> items) {
        mValues = items;
        for (AllEventItem allEventItem : mValues) {
            if (Stream.of(mMyEvents).anyMatch(q -> q.getId() == allEventItem.getId())) {
                allEventItem.isMyEvent = true;
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentAlleventsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.binding.setEvent(mValues.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final FragmentAlleventsBinding binding;

        ViewHolder(FragmentAlleventsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
