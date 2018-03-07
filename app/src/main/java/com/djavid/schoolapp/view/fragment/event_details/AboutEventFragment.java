package com.djavid.schoolapp.view.fragment.event_details;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.djavid.schoolapp.App;
import com.djavid.schoolapp.R;
import com.djavid.schoolapp.databinding.FragmentAboutEventBinding;
import com.djavid.schoolapp.rest.Api;
import com.djavid.schoolapp.viewmodel.events.EventItem;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AboutEventFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class AboutEventFragment extends Fragment {
    private static final String ARG_EVENTID = "eventId";

    private long mEventId;

    private AboutEventFragmentInteractionListener mListener;

    public AboutEventFragment() {
        // Required empty public constructor
    }

    public static AboutEventFragment newInstance(long eventId) {
        AboutEventFragment fragment = new AboutEventFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_EVENTID, eventId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentAboutEventBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_about_event, container, false);
        if (mEventId == 0) {
            Log.e("DBG", "mEventId == 0");
        }
        App.getAppInstance().getApi().getEvent(
                App.getAppInstance().getPreferences().getToken(),
                mEventId
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(event -> binding.setEvent(
                        new EventItem(event)), Api::HandleError);

        return binding.getRoot();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mEventId = getArguments().getLong(ARG_EVENTID);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AboutEventFragmentInteractionListener) {
            mListener = (AboutEventFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement AboutEventFragmentInteractionListener");
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
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface AboutEventFragmentInteractionListener {
    }
}
