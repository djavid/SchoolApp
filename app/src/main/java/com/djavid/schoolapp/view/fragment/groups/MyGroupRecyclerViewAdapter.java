package com.djavid.schoolapp.view.fragment.groups;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.djavid.schoolapp.databinding.FragmentMygroupBinding;
import com.djavid.schoolapp.viewmodel.groups.GroupItem;

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

    @Override
    public void removeGroup(GroupItem group) {
        mValues.remove(group);
        mListener.removeGroup(group);
    }
}
