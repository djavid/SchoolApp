package com.djavid.schoolapp.view.fragment.notifications;

import android.content.Context;
import android.databinding.DataBindingUtil;
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
import com.djavid.schoolapp.databinding.FragmentNotificationitemListBinding;
import com.djavid.schoolapp.view.adapter.MyNotificationItemRecyclerViewAdapter;
import com.djavid.schoolapp.viewmodel.notifications.NotificationItem;

import io.reactivex.Observable;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class NotificationListFragment extends Fragment {

    private NotificationsInteractionListener mListener;


    public NotificationListFragment() { }

    public static NotificationListFragment newInstance() {
        return new NotificationListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentNotificationitemListBinding binding = DataBindingUtil.inflate(inflater,
                R.layout.fragment_notificationitem_list, container, false);
        binding.setPresenter(this);

        Context context = binding.getRoot().getContext();
        RecyclerView recyclerView = binding.getRoot().findViewById(R.id.list);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new MyNotificationItemRecyclerViewAdapter(provideNotifications(), mListener));

        return binding.getRoot();
    }

    private Observable<NotificationItem> provideNotifications() {
        return App.getAppInstance().getApi()
                .getNotifications(App.getAppInstance().getPreferences().getToken())
                .flatMapObservable(notifications -> Observable.fromIterable(
                        Stream.of(notifications)
                                .map(n -> new NotificationItem(n))
                                .toList()
                ));
    }

    public int getPublishNotificationButtonVisibility() {
        return App.getAppInstance().isTeacher() ? VISIBLE : GONE;
    }

    public void createNotification() {
        mListener.createNotification();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NotificationsInteractionListener) {
            mListener = (NotificationsInteractionListener) context;
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

    public interface NotificationsInteractionListener {
        void createNotification();
    }
}
