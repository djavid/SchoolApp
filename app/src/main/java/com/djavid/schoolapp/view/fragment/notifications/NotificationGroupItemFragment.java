package com.djavid.schoolapp.view.fragment.notifications;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.annimon.stream.Stream;
import com.djavid.schoolapp.App;
import com.djavid.schoolapp.R;
import com.djavid.schoolapp.viewmodel.groups.GroupItem;
import com.djavid.schoolapp.viewmodel.notifications.NotificationGroupItem;

import io.reactivex.Observable;

public class NotificationGroupItemFragment extends NotificationFragmentBase {
    public NotificationGroupItemFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_groupparticipantitem_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            recyclerView.setAdapter(new NotificationGroupItemRecyclerViewAdapter(
                    provideGroupParticipants(),
                    notification,
                    mContext));
        }
        return view;
    }

    private Observable<NotificationGroupItem> provideGroupParticipants() {
        return App.getAppInstance().getApi().getAllGroups(
                App.getAppInstance().getPreferences().getToken()
        ).flatMapObservable(allGroups -> Observable.fromIterable(
                Stream.of(allGroups)
                        .map(g -> new NotificationGroupItem(
                                notification,
                                new GroupItem(g),
                                notification.notification.groups.contains(g.id)))
                        .toList()
        ));
    }
}
