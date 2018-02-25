package com.djavid.schoolapp.view.fragment.event_details;

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
import com.djavid.schoolapp.model.events.Event;
import com.djavid.schoolapp.model.groups.Group;
import com.djavid.schoolapp.viewmodel.event_details.EventGroupItem;
import com.djavid.schoolapp.viewmodel.events.EventItem;
import com.djavid.schoolapp.viewmodel.groups.GroupItem;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link EventGroupInteractionListener}
 * interface.
 */
public class EventGroupItemFragment extends Fragment {
    private static final String ARG_EVENTID = "eventId";

    private long mEventId;

    private EventGroupInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EventGroupItemFragment() {
    }

    public static EventGroupItemFragment newInstance(long eventId) {
        EventGroupItemFragment fragment = new EventGroupItemFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_EVENTID, eventId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mEventId = getArguments().getLong(ARG_EVENTID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_eventgroupitem_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new EventGroupItemRecyclerViewAdapter(
                    provideAllGroups(),
                    mListener));
        }
        return view;
    }

    private Observable<EventGroupItem> provideAllGroups() {
        Single<Event> eventAsync = App.getAppInstance().getApi()
                .getEvent(App.getAppInstance().getPreferences().getToken(),
                        mEventId);

        return eventAsync
                .flatMapObservable(event -> {
                    Single<List<Group>> allGroupsSingle = App.getAppInstance().getApi()
                            .getAllGroups(App.getAppInstance().getPreferences().getToken());
                    Single<List<Event>> createdEventsSingle = App.getAppInstance().getApi()
                            .getCreatedEvents(App.getAppInstance().getPreferences().getToken());
                    return allGroupsSingle.flatMapObservable(
                            allGroups -> createdEventsSingle.flatMapObservable(
                                    createdEvents -> {
                                        EventItem eventItem = new EventItem(event, false,
                                                Stream.of(createdEvents).anyMatch(
                                                        e -> e.id == event.id
                                                ));
                                        return Observable.fromIterable(
                                                Stream.of(allGroups)
                                                        .map(group -> new EventGroupItem(
                                                                new GroupItem(group, false),
                                                                eventItem,
                                                                event.participation_groups.contains(group.id)))
                                                        .toList());
                                    }));
                });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof EventGroupInteractionListener) {
            mListener = (EventGroupInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement EventGroupInteractionListener");
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
    public interface EventGroupInteractionListener {
        void openGroupDetails(EventGroupItem group);
        void addEventGroup(EventGroupItem eventGroup);
        void removeEventGroup(EventGroupItem eventGroup);
    }
}
