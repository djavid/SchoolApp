package com.djavid.schoolapp.view.fragment.events;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.djavid.schoolapp.App;
import com.djavid.schoolapp.databinding.FragmentCreateEventBinding;
import com.djavid.schoolapp.rest.Api;
import com.djavid.schoolapp.viewmodel.events.EventItem;

import java.util.LinkedList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CreateEventFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public CreateEventFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentCreateEventBinding binding = FragmentCreateEventBinding.inflate(inflater, container, false);
        binding.setEvent(new EventItem());

        binding.setCreateCommand(a -> {
            EventItem event = binding.getEvent();
            App.getAppInstance().getApi()
                    .createEvent(
                            App.getAppInstance().getPreferences().getToken(),
                            event.getTitle(),
                            event.getPlace(),
                            event.getDescription(),
                            new LinkedList<>(),
                            Api.Date(event.getStartDate()),
                            Api.Date(event.getEndDate()))
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(q -> mListener.onEventCreated(), Api::HandleError);
        });

        binding.setSelectStartDateCommand(a -> {
            EventItem event = binding.getEvent();
            new DatePickerDialog(getContext(), (datePicker, year, month, day) -> {
                event.setStartDateYear(year);
                event.setStartDateMonth(month);
                event.setStartDateDay(day);
                new TimePickerDialog(getContext(), (timePicker, hour, min) -> {
                    event.setStartDateMinute(min);
                    event.setStartDateHour(hour);
                }, event.getStartDateHour(), event.getStartDateMinute(), true)
                        .show();
            },
                    event.getStartDateYear(),
                    event.getStartDateMonth(),
                    event.getStartDateDay()).show();
        });


        binding.setSelectEndDateCommand(a -> {
            EventItem event = binding.getEvent();
            new DatePickerDialog(getContext(), (datePicker, year, month, day) -> {
                event.setEndDateYear(year);
                event.setEndDateMonth(month);
                event.setEndDateDay(day);
                new TimePickerDialog(getContext(), (timePicker, hour, min) -> {
                    event.setEndDateMinute(min);
                    event.setEndDateHour(hour);
                }, event.getEndDateHour(), event.getEndDateMinute(), true)
                        .show();
            },
                    event.getEndDateYear(),
                    event.getEndDateMonth(),
                    event.getEndDateDay()).show();
        });

        return binding.getRoot();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
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
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onEventCreated();
    }
}
