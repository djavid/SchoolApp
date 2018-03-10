package com.djavid.schoolapp.view.adapter;

import android.support.v7.util.SortedList;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.annimon.stream.Stream;
import com.djavid.schoolapp.databinding.FragmentMygroupBinding;
import com.djavid.schoolapp.viewmodel.groups.GroupItem;

import java.util.LinkedList;
import java.util.List;

import io.reactivex.Observable;

/**
 * {@link RecyclerView.Adapter} that can display a {@link GroupItem} and makes a call to the
 * specified {@link GroupListInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyGroupRecyclerViewAdapter extends GroupRecyclerViewAdapter<MyGroupRecyclerViewAdapter.ViewHolder> {

    public MyGroupRecyclerViewAdapter(Observable<GroupItem> groups, GroupListInteractionListener listener) {
        super(groups, listener);
    }

    protected List<GroupItem> allValues = new LinkedList<>();

    @Override
    protected void addItem(GroupItem item) {
        allValues.add(item);
        super.addItem(item);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(FragmentMygroupBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false));
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.binding.setGroup(mValues.get(position));
        holder.binding.setPresenter(this);
        holder.binding.executePendingBindings();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        final FragmentMygroupBinding binding;

        public ViewHolder(FragmentMygroupBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void onSearchChanged(CharSequence seq, int start, int before, int count) {
        String s = seq.toString().toLowerCase();
        if (TextUtils.isEmpty(s.trim())) {
            mValues.clear();
            mValues.addAll(allValues);
        } else {
            Stream<GroupItem> filtered = Stream.of(allValues)
                    .filter(q -> q.getTitle().toLowerCase().contains(s));
            List<GroupItem> newList = filtered.toList();

            if (newList.size() == mValues.size() &&
                    filtered.allMatch(q -> mValues.indexOf(q) != SortedList.INVALID_POSITION)) {
                return;
            }

            mValues.clear();
            mValues.addAll(newList);
        }
    }

    @Override
    public void removeGroup(GroupItem group) {
        mValues.remove(group);
        mListener.removeGroup(group);
    }
}
