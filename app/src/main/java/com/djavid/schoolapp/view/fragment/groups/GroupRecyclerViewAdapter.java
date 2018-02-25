package com.djavid.schoolapp.view.fragment.groups;

import android.support.v7.util.ListUpdateCallback;
import android.support.v7.widget.RecyclerView;

import com.djavid.schoolapp.viewmodel.groups.GroupItem;
import com.djavid.schoolapp.viewmodel.groups.GroupItemList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Andrei on 25-Feb-18.
 */

public abstract class GroupRecyclerViewAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {

    protected GroupItemList mValues = new GroupItemList(new ListUpdateCallback() {
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
    private final GroupListInteractionListener mListener;

    public GroupRecyclerViewAdapter(Observable<GroupItem> groups, GroupListInteractionListener listener) {
        mListener = listener;

        groups
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::addItem);
    }

    private void addItem(GroupItem group) {
        mValues.add(group);
    }

    private void setGroupItem(GroupItem item) {
        mValues.updateItemAt(mValues.indexOf(item), item);
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void addGroup(GroupItem group) {
        setGroupItem(group.withEntered(true));
        mListener.addGroup(group);
    }

    public void removeGroup(GroupItem group) {
        setGroupItem(group.withEntered(false));
        mListener.removeGroup(group);
    }

    public void showGroup(GroupItem group) {
        mListener.showGroupDetails(group);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface GroupListInteractionListener {
        // TODO: Update argument type and name
        void addGroup(GroupItem group);

        void removeGroup(GroupItem group);

        void showGroupDetails(GroupItem group);
    }
}
