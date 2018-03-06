package com.djavid.schoolapp.view.fragment.groups;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.annimon.stream.Stream;
import com.djavid.schoolapp.App;
import com.djavid.schoolapp.R;
import com.djavid.schoolapp.view.activity.AllGroupsActivity;
import com.djavid.schoolapp.view.activity.GroupsActivity;
import com.djavid.schoolapp.view.adapter.GroupRecyclerViewAdapter;
import com.djavid.schoolapp.view.adapter.MyGroupRecyclerViewAdapter;
import com.djavid.schoolapp.viewmodel.groups.GroupItem;

import io.reactivex.Observable;


public class MyGroupFragment extends Fragment {

    RecyclerView recyclerView;

    private GroupRecyclerViewAdapter.GroupListInteractionListener mListener;

    public MyGroupFragment() { }

    public static MyGroupFragment newInstance() {
        return new MyGroupFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mygroup_list, container, false);
        setHasOptionsMenu(true);

        Context context = view.getContext();
        recyclerView = view.findViewById(R.id.list);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new MyGroupRecyclerViewAdapter(provideMyGroups(), mListener));

        FloatingActionButton fab = view.findViewById(R.id.fab);
        if (App.getAppInstance().isTeacher()) {
            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(v -> {

                //

            });
        }

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.mygroup_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_allgroups :
                startActivity(new Intent(getContext(), AllGroupsActivity.class));

            default:
                return super.onOptionsItemSelected(item);
        }
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
    public void onResume() {
        super.onResume();

        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            recyclerView.setAdapter(new MyGroupRecyclerViewAdapter(provideMyGroups(), mListener));
        }

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
