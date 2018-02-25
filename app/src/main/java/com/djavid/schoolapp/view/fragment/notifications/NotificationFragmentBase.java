package com.djavid.schoolapp.view.fragment.notifications;

import android.content.Context;
import android.support.v4.app.Fragment;

import com.djavid.schoolapp.model.notifications.Notification;
import com.djavid.schoolapp.viewmodel.notifications.NotificationGroupItem;
import com.djavid.schoolapp.viewmodel.notifications.NotificationItem;

import io.reactivex.Observable;

/**
 * Created by Andrei on 25-Feb-18.
 */

public class NotificationFragmentBase extends Fragment {
    protected NotificationFragmentContext mContext;

    protected NotificationItem notification;
    protected Observable<Long> groupChangedEvent;
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        if (getArguments() != null) {
//            notification = new NotificationItem((Notification)getArguments().getSerializable(ARG_NOTIFICATION));
//        }
//    }
//
//    // TODO: Customize parameter initialization
//    @SuppressWarnings("unused")
//    public static NotificationGroupItemFragment newNotificationGroupItemFragment(Notification notification) {
//        NotificationGroupItemFragment fragment = new NotificationGroupItemFragment();
//        Bundle args = new Bundle();
//        args.putSerializable(ARG_NOTIFICATION, notification);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    // TODO: Customize parameter initialization
//    @SuppressWarnings("unused")
//    public static PublishNotificationFragment newPublishNotificationFragment(Notification notification) {
//        PublishNotificationFragment fragment = new PublishNotificationFragment();
//        Bundle args = new Bundle();
//        args.putSerializable(ARG_NOTIFICATION, notification);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//
//        outState.putSerializable(ARG_NOTIFICATION, notification.notification);
//    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NotificationFragmentContext) {
            mContext = (NotificationFragmentContext) context;
            notification = new NotificationItem(mContext.getNotification());
            groupChangedEvent = mContext.getGroupChangedEvent();
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement NotificationGroupItemListInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mContext = null;
        notification = null;
    }

    public interface NotificationFragmentContext {
        void selectGroups(Notification notification);

        void publish(Notification notification);

        void openGroupDetails(NotificationGroupItem group);

        void onGroupChanged(long groupId);

        Observable<Long> getGroupChangedEvent();

        Notification getNotification();
    }
}
