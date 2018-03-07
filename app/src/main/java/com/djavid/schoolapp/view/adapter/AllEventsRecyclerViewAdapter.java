package com.djavid.schoolapp.view.adapter;

import android.support.v7.util.ListUpdateCallback;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.djavid.schoolapp.databinding.FragmentAlleventsBinding;
import com.djavid.schoolapp.rest.Api;
import com.djavid.schoolapp.view.fragment.events.AllEventsFragment;
import com.djavid.schoolapp.viewmodel.events.EventItem;
import com.djavid.schoolapp.viewmodel.events.EventItemList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * {@link RecyclerView.Adapter} that can display a {@link EventItem} and makes a call to the
 * specified {@link AllEventsFragment.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class AllEventsRecyclerViewAdapter extends RecyclerView.Adapter<AllEventsRecyclerViewAdapter.ViewHolder> {

    private final EventItemList mValues = new EventItemList(new ListUpdateCallback() {
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

    private final AllEventsFragment.OnListFragmentInteractionListener mListener;

    public AllEventsRecyclerViewAdapter(Observable<EventItem> items, AllEventsFragment.OnListFragmentInteractionListener listener) {
        mListener = listener;

        items.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::addItem, Api::HandleError);
    }

    private void addItem(EventItem item) {
        mValues.add(item);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentAlleventsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        final FragmentAlleventsBinding binding;

        ViewHolder(FragmentAlleventsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
