package com.djavid.schoolapp.view.fragment.groups;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annimon.stream.Stream;
import com.djavid.schoolapp.App;
import com.djavid.schoolapp.R;
import com.djavid.schoolapp.viewmodel.groups.GroupItem;

import io.reactivex.Observable;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link GroupRecyclerViewAdapter.GroupListInteractionListener}
 * interface.
 */
public class MyGroupFragment extends Fragment {

    private GroupRecyclerViewAdapter.GroupListInteractionListener mListener;

    public MyGroupFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mygroup_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;

            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new MyGroupRecyclerViewAdapter(
                    provideMyGroups()
                    , mListener));
        }
        return view;
    }

    private Observable<GroupItem> provideMyGroups() {
        return App.getAppInstance().getApi()
                .getMyGroups(App.getAppInstance().getPreferences().getToken())
                .flatMapObservable(myGroups -> Observable.fromIterable(
                        Stream.of(myGroups)
                                .map(group -> new GroupItem(group, true))
                                .toList()));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof GroupRecyclerViewAdapter.GroupListInteractionListener) {
            mListener = (GroupRecyclerViewAdapter.GroupListInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement GroupListInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
}
