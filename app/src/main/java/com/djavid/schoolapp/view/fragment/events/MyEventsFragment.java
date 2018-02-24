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
import com.annimon.stream.operator.ObjMerge;
import com.djavid.schoolapp.App;
import com.djavid.schoolapp.R;
import com.djavid.schoolapp.model.events.Event;
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
public class MyEventsFragment extends Fragment {
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MyEventsFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myevents_list, container, false);


        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;

            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(new MyEventsRecyclerViewAdapter(provideMyEvents(), mListener));
        }
        return view;
    }

    private Observable<EventItem> provideMyEvents() {
                    Single<List<Event>> enteredEventsSingle = App.getAppInstance().getApi()
                            .getEnteredEvents(App.getAppInstance().getPreferences().getToken());
                    Single<List<Event>> createdEventsSingle = App.getAppInstance().getApi()
                            .getCreatedEvents(App.getAppInstance().getPreferences().getToken());
                    return enteredEventsSingle.flatMapObservable(enteredEvents ->
                            createdEventsSingle.flatMapObservable(createdEvents ->
                                    Observable.fromIterable(Stream.<Event>merge(
                                            Stream.<Event>of(enteredEvents),
                                            Stream.<Event>of(createdEvents),
                                            (a,b)-> ObjMerge.MergeResult.TAKE_SECOND)
                                    .map(event -> new EventItem(
                                            event,
                                            Stream.<Event>of(enteredEvents).anyMatch(e -> e.id == event.id),
                                            Stream.<Event>of(createdEvents).anyMatch(e -> e.id == event.id)))
                                            .toList()
                            )));
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
     * fragment to Myow an interaction in this fragment to be communicated
     * to the activity and potentiMyy other fragments contained in that
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
