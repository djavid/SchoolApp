package com.djavid.schoolapp.view.fragment.groups;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.djavid.schoolapp.App;
import com.djavid.schoolapp.R;
import com.djavid.schoolapp.util.LogTags;
import com.djavid.schoolapp.view.fragment.groups.MyGroupFragment.OnListFragmentInteractionListener;
import com.djavid.schoolapp.viewmodel.groups.MyGroupItem;

import java.util.LinkedList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * {@link RecyclerView.Adapter} that can display a {@link MyGroupItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyGroupRecyclerViewAdapter extends RecyclerView.Adapter<MyGroupRecyclerViewAdapter.ViewHolder> {

    private List<MyGroupItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyGroupRecyclerViewAdapter(Single<List<MyGroupItem>> items, OnListFragmentInteractionListener listener) {
        mValues = new LinkedList<>();
        mListener = listener;

        items
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::onItemsArrived);
    }

    private void onItemsArrived(List<MyGroupItem> items) {
        mValues = items;
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_mygroup, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mContentView.setText(mValues.get(position).content);

        // TODO: change to bindings

        holder.mRemoveButton.setOnClickListener(v -> {
            switch (v.getId()) {
                case R.id.mygroup_remove: {
                    App.getAppInstance().getApi().leaveGroup(
                            App.getAppInstance().getPreferences().getToken(),
                            holder.mItem.id
                    )
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(response -> {
                                mValues.remove(position);
                                notifyItemRemoved(position);
                            }, error -> Log.w(LogTags.Exception, error));
                }
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
        public final Button mRemoveButton;
        public MyGroupItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mContentView = view.findViewById(R.id.content);
            mRemoveButton = view.findViewById(R.id.mygroup_remove);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
