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
import com.djavid.schoolapp.databinding.FragmentAllgroupListBinding;
import com.djavid.schoolapp.model.groups.Group;
import com.djavid.schoolapp.view.adapter.AllGroupRecyclerViewAdapter;
import com.djavid.schoolapp.view.adapter.GroupRecyclerViewAdapter;
import com.djavid.schoolapp.viewmodel.groups.GroupItem;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;


public class AllGroupFragment extends Fragment {

    private GroupRecyclerViewAdapter.GroupListInteractionListener mListener;

    public AllGroupFragment() { }

    public static AllGroupFragment newInstance() {
        return new AllGroupFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentAllgroupListBinding binding = FragmentAllgroupListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        RecyclerView recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        AllGroupRecyclerViewAdapter adapter = new AllGroupRecyclerViewAdapter(
                provideAllGroups(), mListener);
        binding.setPresenter(adapter);
        recyclerView.setAdapter(adapter);
        return view;
    }

    private Observable<GroupItem> provideAllGroups() {
        return App.getAppInstance().getApi()
                .getAllGroups(
                        App.getAppInstance().getPreferences().getToken()
                ).flatMapObservable(allGroups -> {
                    Single<List<Group>> enteredGroupsSingle = App.getAppInstance().getApi()
                            .getMyGroups(App.getAppInstance().getPreferences().getToken());
                    return enteredGroupsSingle.flatMapObservable(enteredGroups ->
                            Observable.fromIterable(allGroups).map(group ->
                                    new GroupItem(group,
                                            Stream.of(enteredGroups).anyMatch(g -> g.id == group.id)))
                                    .sorted((a, b) -> Boolean.compare(a.isEntered(), b.isEntered()))
                    );
                });
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
