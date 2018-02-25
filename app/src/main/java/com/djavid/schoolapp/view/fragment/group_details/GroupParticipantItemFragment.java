package com.djavid.schoolapp.view.fragment.group_details;

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
import com.djavid.schoolapp.model.users.User;
import com.djavid.schoolapp.viewmodel.group_details.GroupParticipantItem;
import com.djavid.schoolapp.viewmodel.group_details.UserItem;
import com.djavid.schoolapp.viewmodel.groups.GroupItem;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link GroupParticipantListInteractionListener}
 * interface.
 */
public class GroupParticipantItemFragment extends Fragment {
    private static final String ARG_GROUPID = "groupId";

    private long mGroupId;

    private GroupParticipantListInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public GroupParticipantItemFragment() {
    }

    public static GroupParticipantItemFragment newInstance(long groupId) {
        GroupParticipantItemFragment fragment = new GroupParticipantItemFragment();
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
        View view = inflater.inflate(R.layout.fragment_groupparticipantitem_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            recyclerView.setAdapter(new GroupParticipantItemRecyclerViewAdapter(
                    provideGroupParticipants(mGroupId),
                    mListener));
        }
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
                                            .map(participant -> new GroupParticipantItem(new GroupItem(group), new UserItem(participant)))
                                            .toList()
                            )
                    );
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
    public interface GroupParticipantListInteractionListener {
    }
}
