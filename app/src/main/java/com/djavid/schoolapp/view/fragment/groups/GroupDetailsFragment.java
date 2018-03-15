package com.djavid.schoolapp.view.fragment.groups;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.annimon.stream.Stream;
import com.djavid.schoolapp.App;
import com.djavid.schoolapp.R;
import com.djavid.schoolapp.model.users.User;
import com.djavid.schoolapp.util.RxUtils;
import com.djavid.schoolapp.view.adapter.GroupParticipantItemRecyclerViewAdapter;
import com.djavid.schoolapp.viewmodel.group_details.GroupParticipantItem;
import com.djavid.schoolapp.viewmodel.group_details.UserItem;
import com.djavid.schoolapp.viewmodel.groups.GroupItem;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;


public class GroupDetailsFragment extends Fragment {

    private static final String ARG_GROUPID = "groupId";
    private long mGroupId;
    private GroupParticipantListInteractionListener mListener;


    public GroupDetailsFragment() { }

    public static GroupDetailsFragment newInstance(long groupId) {
        GroupDetailsFragment fragment = new GroupDetailsFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_GROUPID, groupId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mGroupId = getArguments().getLong(ARG_GROUPID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_groupdetails_list, container, false);

        // Set the adapter
        Context context = view.getContext();
        RecyclerView recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        recyclerView.setAdapter(new GroupParticipantItemRecyclerViewAdapter(
                provideGroupParticipants(mGroupId), mListener));

        App.getAppInstance().getApi().getGroup(App.getAppInstance().getPreferences().getToken(), mGroupId)
                .compose(RxUtils.applySingleSchedulers())
                .subscribe(response -> {
                    ((TextView) view.findViewById(R.id.groupTitle)).setText(response.title);
                }, error -> {
                    error.printStackTrace();
                    Toast.makeText(getContext(), R.string.error_try_again, Toast.LENGTH_SHORT).show();
                });

        return view;
    }

    public Observable<GroupParticipantItem> provideGroupParticipants(long groupId) {
        return App.getAppInstance().getApi()
                .getGroup(App.getAppInstance().getPreferences().getToken(),
                        mGroupId)
                .flatMapObservable(group -> {
                    Single<List<User>> participantsSingle = App.getAppInstance().getApi().getGroupParticipants(
                            App.getAppInstance().getPreferences().getToken(),
                            group.id);
                    return participantsSingle.flatMapObservable(participants ->
                            Observable.fromIterable(
                                    Stream.of(participants)
                                            .map(participant -> new GroupParticipantItem(new GroupItem(group),
                                                    new UserItem(participant)))
                                            .toList()));
                });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof GroupParticipantListInteractionListener) {
            mListener = (GroupParticipantListInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement GroupParticipantListInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface GroupParticipantListInteractionListener { }
}
