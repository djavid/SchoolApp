package com.djavid.schoolapp.view.adapter;

import android.support.v7.util.ListUpdateCallback;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.djavid.schoolapp.databinding.FragmentEventcommentBinding;
import com.djavid.schoolapp.view.fragment.event_details.EventCommentFragment.EventCommentFragmentInteractionListener;
import com.djavid.schoolapp.viewmodel.event_details.EventCommentItem;
import com.djavid.schoolapp.viewmodel.event_details.EventCommentItemList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * {@link RecyclerView.Adapter} that can display a {@link EventCommentItem} and makes a call to the
 * specified {@link EventCommentFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class EventCommentItemRecyclerViewAdapter extends RecyclerView.Adapter<EventCommentItemRecyclerViewAdapter.ViewHolder> {

    private final EventCommentItemList mValues = new EventCommentItemList(new ListUpdateCallback() {
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
    private final EventCommentFragmentInteractionListener mListener;

    public EventCommentItemRecyclerViewAdapter(Observable<EventCommentItem> items, EventCommentFragmentInteractionListener listener) {
        items
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::addItem);

        mListener = listener;
    }

    public void addItem(EventCommentItem item) {
        mValues.add(item);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(
                FragmentEventcommentBinding.inflate(LayoutInflater.from(parent.getContext()),
                        parent,
                        false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.binding.setComment(mValues.get(position));
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final FragmentEventcommentBinding binding;

        public ViewHolder(FragmentEventcommentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
