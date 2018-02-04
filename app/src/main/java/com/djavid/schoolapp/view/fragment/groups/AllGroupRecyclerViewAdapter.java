package com.djavid.schoolapp.view.fragment.groups;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.annimon.stream.Stream;
import com.djavid.schoolapp.App;
import com.djavid.schoolapp.R;
import com.djavid.schoolapp.util.LogTags;
import com.djavid.schoolapp.view.fragment.groups.AllGroupFragment.OnListFragmentInteractionListener;
import com.djavid.schoolapp.viewmodel.groups.AllGroupItem;
import com.djavid.schoolapp.viewmodel.groups.MyGroupItem;

import java.util.LinkedList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * {@link RecyclerView.Adapter} that can display a {@link AllGroupItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class AllGroupRecyclerViewAdapter extends RecyclerView.Adapter<AllGroupRecyclerViewAdapter.ViewHolder> {

    private List<AllGroupItem> mValues;
    private List<MyGroupItem> mMyGroups;
    private final OnListFragmentInteractionListener mListener;

    public AllGroupRecyclerViewAdapter(Single<List<AllGroupItem>> items, Single<List<MyGroupItem>> myItems, OnListFragmentInteractionListener listener) {
        mValues = new LinkedList<>();
        mListener = listener;

        items
                .subscribeOn(Schedulers.io())
                .zipWith(myItems.subscribeOn(Schedulers.io()), (all, my) -> {
                    mMyGroups = my;
                    return all;
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onItemsArrived);
    }

    private void onItemsArrived(List<AllGroupItem> items) {
        mValues = items;
        for (AllGroupItem allGroupItem : mValues) {
            if (Stream.of(mMyGroups).anyMatch(q -> q.id == allGroupItem.id)) {
                allGroupItem.isMyGroup = true;
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_allgroup, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mContentView.setText(mValues.get(position).content);

        holder.mAddButton.setVisibility(holder.mItem.isMyGroup
                ? View.GONE
                : View.VISIBLE);

        holder.mRemoveButton.setVisibility(holder.mItem.isMyGroup
                ? View.VISIBLE
                : View.GONE);

        holder.mAddButton.setOnClickListener(v -> {
            switch (v.getId()) {
                case R.id.allgroup_add:
                    App.getAppInstance().getApi().joinGroup(
                            App.getAppInstance().getPreferences().getToken(),
                            holder.mItem.id
                    )
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(response -> {
                                holder.mAddButton.setVisibility(View.GONE);
                                holder.mRemoveButton.setVisibility(View.VISIBLE);
                                holder.mItem.isMyGroup = true;
                            }, error -> Log.w(LogTags.Exception, error));
                    return;
            }
        });

        holder.mRemoveButton.setOnClickListener(v -> {
            switch (v.getId()) {
                case R.id.allgroup_remove:
                    App.getAppInstance().getApi().leaveGroup(
                            App.getAppInstance().getPreferences().getToken(),
                            holder.mItem.id
                    )
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(response -> {
                                holder.mAddButton.setVisibility(View.VISIBLE);
                                holder.mRemoveButton.setVisibility(View.GONE);
                                holder.mItem.isMyGroup = false;
                            }, error -> Log.w(LogTags.Exception, error));

                    return;
            }
        });

        holder.mView.setOnClickListener(v -> {
            if (null != mListener) {
                // Notify the active callbacks interface (the activity, if the
                // fragment is attached to one) that an item has been selected.
                mListener.onListFragmentInteraction(holder.mItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mContentView;
        public final Button mAddButton;
        public final Button mRemoveButton;
        public AllGroupItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = view.findViewById(R.id.content);
            mAddButton = view.findViewById(R.id.allgroup_add);
            mRemoveButton = view.findViewById(R.id.allgroup_remove);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
