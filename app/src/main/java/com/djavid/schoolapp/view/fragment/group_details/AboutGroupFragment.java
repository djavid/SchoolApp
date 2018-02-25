package com.djavid.schoolapp.view.fragment.group_details;

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
import com.djavid.schoolapp.databinding.FragmentAboutGroupBinding;
import com.djavid.schoolapp.viewmodel.groups.GroupItem;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AboutGroupFragmentInteractionListener} interface
 * to handle interaction groups.
 */
public class AboutGroupFragment extends Fragment {

    public static final String ARG_GROUPID = "groupId";
    private long mGroupId;

    private AboutGroupFragmentInteractionListener mListener;

    public AboutGroupFragment() {
        // Required empty public constructor
    }

    public static AboutGroupFragment newInstance(long eventId) {
        AboutGroupFragment fragment = new AboutGroupFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_GROUPID, eventId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        FragmentAboutGroupBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_about_group, container, false);
        if (mGroupId == 0) {
            Log.e("DBG", "mGroupId == 0");
        }
        App.getAppInstance().getApi().getGroup(
                App.getAppInstance().getPreferences().getToken(),
                mGroupId
        )
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(group -> binding.setGroup(
                        new GroupItem(group)));

        return binding.getRoot();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mGroupId = getArguments().getLong(ARG_GROUPID);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof AboutGroupFragmentInteractionListener) {
            mListener = (AboutGroupFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement AboutGroupFragmentInteractionListener");
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
    public interface AboutGroupFragmentInteractionListener {
    }
}
