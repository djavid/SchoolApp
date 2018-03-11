package com.djavid.schoolapp.view.fragment.events;

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
import com.djavid.schoolapp.databinding.FragmentAlleventsListBinding;
import com.djavid.schoolapp.model.events.Event;
import com.djavid.schoolapp.view.adapter.AllEventsRecyclerViewAdapter;
import com.djavid.schoolapp.viewmodel.events.EventItem;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class AllEventsFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public AllEventsFragment() {
    }

    public static AllEventsFragment newInstance() {
        return new AllEventsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        FragmentAlleventsListBinding binding = FragmentAlleventsListBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        RecyclerView recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));

        AllEventsRecyclerViewAdapter adapter = new AllEventsRecyclerViewAdapter(
                provideAllEvents(), mListener);
        binding.setPresenter(adapter);
        recyclerView.setAdapter(adapter);
        return view;
    }

    private Observable<EventItem> provideAllEvents() {
        return App.getAppInstance().getApi()
                .getAllEvents(
                        App.getAppInstance().getPreferences().getToken()
                ).flatMapObservable(allEvents -> {
                    Single<List<Event>> enteredEventsSingle = App.getAppInstance().getApi()
                            .getEnteredEvents(App.getAppInstance().getPreferences().getToken());
                    Single<List<Event>> createdEventsSingle = App.getAppInstance().getApi()
                            .getCreatedEvents(App.getAppInstance().getPreferences().getToken());
                    return enteredEventsSingle.flatMapObservable(enteredEvents ->
                            createdEventsSingle.flatMapObservable(createdEvents ->
                                    Observable.fromIterable(allEvents).map(event ->
                                            new EventItem(event,
                                                    Stream.of(enteredEvents).anyMatch(e -> e.id == event.id),
                                                    Stream.of(createdEvents).anyMatch(e -> e.id == event.id)))
                            ));
                });
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
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
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void openEventDetails(EventItem item);
    }
}
