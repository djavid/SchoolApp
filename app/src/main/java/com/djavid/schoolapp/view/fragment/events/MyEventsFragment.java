package com.djavid.schoolapp.view.fragment.events;

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
import com.annimon.stream.operator.ObjMerge;
import com.djavid.schoolapp.App;
import com.djavid.schoolapp.R;
import com.djavid.schoolapp.databinding.FragmentMyeventsListBinding;
import com.djavid.schoolapp.model.events.Event;
import com.djavid.schoolapp.view.adapter.MyEventsRecyclerViewAdapter;
import com.djavid.schoolapp.viewmodel.events.EventItem;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Single;


public class MyEventsFragment extends Fragment {
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MyEventsFragment() {
    }

    public static MyEventsFragment newInstance() {
        return new MyEventsFragment();
    }

    FragmentMyeventsListBinding binding;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentMyeventsListBinding.inflate(
                inflater, container, false);
        View view = binding.getRoot();

        setHasOptionsMenu(true);

        Context context = view.getContext();
        recyclerView = view.findViewById(R.id.list);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        FloatingActionButton fab = view.findViewById(R.id.fab);
        if (App.getAppInstance().isTeacher()) {
            fab.setVisibility(View.VISIBLE);
            fab.setOnClickListener(v -> mListener.openCreateEvent());
        }

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        updateRecycler();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.myevent_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_allevents:
                mListener.showAllEvents();

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private Observable<EventItem> provideMyEvents() {
                    Single<List<Event>> enteredEventsSingle = App.getAppInstance().getApi()
                            .getEnteredEvents(App.getAppInstance().getPreferences().getToken());
                    Single<List<Event>> createdEventsSingle = App.getAppInstance().getApi()
                            .getCreatedEvents(App.getAppInstance().getPreferences().getToken());
                    return enteredEventsSingle.flatMapObservable(enteredEvents ->
                            createdEventsSingle.flatMapObservable(createdEvents ->
                                    Observable.fromIterable(Stream.merge(
                                            Stream.of(enteredEvents),
                                            Stream.of(createdEvents),
                                            (a,b)-> ObjMerge.MergeResult.TAKE_SECOND)
                                    .map(event -> new EventItem(
                                            event,
                                            Stream.of(enteredEvents).anyMatch(e -> e.id == event.id),
                                            Stream.of(createdEvents).anyMatch(e -> e.id == event.id)))
                                            .toList()
                            )));
    }

    public void updateRecycler() {
        if (recyclerView != null) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            MyEventsRecyclerViewAdapter adapter = new MyEventsRecyclerViewAdapter(provideMyEvents(), mListener);
            binding.setPresenter(adapter);
            recyclerView.setAdapter(adapter);
        }
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

        void showAllEvents();

        void openCreateEvent();
    }
}
