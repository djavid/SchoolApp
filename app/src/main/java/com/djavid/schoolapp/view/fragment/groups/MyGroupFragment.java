package com.djavid.schoolapp.view.fragment.groups;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.annimon.stream.Stream;
import com.djavid.schoolapp.App;
import com.djavid.schoolapp.R;
import com.djavid.schoolapp.databinding.FragmentMygroupListBinding;
import com.djavid.schoolapp.view.adapter.GroupRecyclerViewAdapter;
import com.djavid.schoolapp.view.adapter.MyGroupRecyclerViewAdapter;
import com.djavid.schoolapp.view.dialog.CreateGroupDialog;
import com.djavid.schoolapp.viewmodel.groups.GroupItem;

import io.reactivex.Observable;


public class MyGroupFragment extends Fragment {

    final String TAG_CREATE_GROUP_DIALOG = "TAG_CREATE_GROUP_DIALOG";

    RecyclerView recyclerView;
    MyGroupRecyclerViewAdapter adapter;
    FragmentMygroupListBinding binding;

    private GroupRecyclerViewAdapter.GroupListInteractionListener mListener;

    public MyGroupFragment() { }

    public static MyGroupFragment newInstance() {
        return new MyGroupFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMygroupListBinding.inflate(
                inflater, container, false);
        View view = binding.getRoot();

        setHasOptionsMenu(true);

        Context context = view.getContext();

        recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        adapter = new MyGroupRecyclerViewAdapter(provideMyGroups(), mListener);
        binding.setPresenter(adapter);
        recyclerView.setAdapter(adapter);

        FloatingActionButton fab = view.findViewById(R.id.fab);
        if (App.getAppInstance().isTeacher()) {
            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(v -> {
                try {
                    CreateGroupDialog dialog = new CreateGroupDialog();
                    dialog.setDismissListener(this::updateRecycler);
                    dialog.show(getFragmentManager(), TAG_CREATE_GROUP_DIALOG);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        updateRecycler(null);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.mygroup_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_allgroups:
                mListener.showAllGroups();
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

    public void updateRecycler(GroupItem groupItem) {

        if (groupItem != null) {
            if (recyclerView != null) {

                adapter.addGroup(groupItem);
                adapter.notifyItemInserted(adapter.getItemCount() - 1);
                recyclerView.scrollToPosition(adapter.getItemCount() - 1);

            }
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
