package com.djavid.schoolapp.view.fragment.groups;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.djavid.schoolapp.App;
import com.djavid.schoolapp.R;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public class CreateGroupFragment extends Fragment {
    Button _createButton;
    TextView _title;

    private CreateGroupInteractionListener mListener;

    public CreateGroupFragment() { }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_group, container, false);
        _createButton = view.findViewById(R.id.createGroupButton);
        _title = view.findViewById(R.id.groupTitle);

        _createButton.setOnClickListener(a -> {
            String title = _title.getText().toString();
            if (mListener != null && !title.isEmpty())
                App.getAppInstance().getApi()
                        .createGroup(App.getAppInstance().getPreferences().getToken(), title)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(q -> mListener.onGroupCreated());
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof CreateGroupInteractionListener) {
            mListener = (CreateGroupInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement CreateGroupInteractionListener");
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
    public interface CreateGroupInteractionListener {
        // TODO: Update argument type and name
        void onGroupCreated();
    }
}
