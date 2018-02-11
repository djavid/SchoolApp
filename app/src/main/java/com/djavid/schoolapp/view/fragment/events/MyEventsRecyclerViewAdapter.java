package com.djavid.schoolapp.view.fragment.events;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.djavid.schoolapp.databinding.FragmentMyeventsBinding;
import com.djavid.schoolapp.viewmodel.events.MyEventItem;

import java.util.LinkedList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * {@link RecyclerView.Adapter} that can display a {@link MyEventItem} and makes a cMy to the
 * specified {@link MyEventsFragment.OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyEventsRecyclerViewAdapter extends RecyclerView.Adapter<MyEventsRecyclerViewAdapter.ViewHolder> {

    private List<MyEventItem> mValues = new LinkedList<>();
    private final MyEventsFragment.OnListFragmentInteractionListener mListener;

    public MyEventsRecyclerViewAdapter(Single<List<MyEventItem>> items, MyEventsFragment.OnListFragmentInteractionListener listener) {
        mListener = listener;

        items
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onItemsArrived);
    }

    private void onItemsArrived(List<MyEventItem> items) {
        mValues = items;
        notifyDataSetChanged();
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

    public void onClick(MyEventItem event) {
        mListener.openEventDetails(event);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final FragmentMyeventsBinding binding;

        ViewHolder(FragmentMyeventsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
