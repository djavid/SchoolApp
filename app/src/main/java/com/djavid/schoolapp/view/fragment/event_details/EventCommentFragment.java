package com.djavid.schoolapp.view.fragment.event_details;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.annimon.stream.Stream;
import com.djavid.schoolapp.App;
import com.djavid.schoolapp.R;
import com.djavid.schoolapp.rest.Api;
import com.djavid.schoolapp.view.adapter.EventCommentItemRecyclerViewAdapter;
import com.djavid.schoolapp.viewmodel.event_details.EventCommentItem;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link EventCommentFragmentInteractionListener}
 * interface.
 */
public class EventCommentFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_EVENTID = "eventId";
    // TODO: Customize parameters
    private long mEventId;

    private EventCommentFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EventCommentFragment() {
    }

    // TODO: Customize parameter initialization
    @SuppressWarnings("unused")
    public static EventCommentFragment newInstance(long eventId) {
        EventCommentFragment fragment = new EventCommentFragment();
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
        View view = inflater.inflate(R.layout.fragment_eventcomment_list, container, false);

        // Set the adapter
        View recyclerV = view.findViewById(R.id.list);
        if (recyclerV instanceof RecyclerView) {
            EventCommentItemRecyclerViewAdapter adapter = new EventCommentItemRecyclerViewAdapter(
                    provideEventComments(mEventId),
                    mListener);
            Context context = recyclerV.getContext();
            RecyclerView recyclerView = (RecyclerView) recyclerV;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));
            recyclerView.setAdapter(adapter);
            Button createButton = view.findViewById(R.id.newCommentButton);
            EditText commentText = view.findViewById(R.id.newCommentText);
            createButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String text = commentText.getText().toString();
                    if (text.isEmpty()) {
                        return;
                    }
                    mListener.createComment(mEventId, text)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    item -> {
                                        if (adapter != null) {
                                            adapter.addItem(item);
                                            recyclerView.getLayoutManager().scrollToPosition(0);
                                            commentText.setText("");
                                        }
                                    }, Api::HandleError);
                }
            });
        }

        return view;
    }

    private Observable<EventCommentItem> provideEventComments(long eventId) {
        return App.getAppInstance().getApi()
                .getComments(App.getAppInstance().getPreferences().getToken(),
                        eventId)
                .flatMapObservable(comments -> Observable.fromIterable(
                        Stream.of(comments)
                                .map(comment -> new EventCommentItem(
                                        comment,
                                        comment.author.equals(
                                                App.getAppInstance().getPreferences().getDisplayName())
                                )).toList()
                ));
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof EventCommentFragmentInteractionListener) {
            mListener = (EventCommentFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement EventCommentFragmentInteractionListener");
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
    public interface EventCommentFragmentInteractionListener {
        Single<EventCommentItem> createComment(long eventId, String text);
    }
}
