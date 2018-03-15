package com.djavid.schoolapp.firebase;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;

import com.djavid.schoolapp.App;
import com.djavid.schoolapp.R;
import com.djavid.schoolapp.view.activity.DashboardActivity;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by Andrei on 27-Feb-18.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Log.i("firebase", "onMessageReceived");

        Intent intent = new Intent(this, DashboardActivity.class);
        intent.putExtra(DashboardActivity.NAV_PARAM, DashboardActivity.NAV_NOTIFICATIONS);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setContentTitle(App.getContext().getString(R.string.app_name))
                .setSmallIcon(R.drawable.ic_menu_camera)
                .setContentText(remoteMessage.getNotification().getBody())
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(PendingIntent.getActivity(this, 0, intent,
                        PendingIntent.FLAG_UPDATE_CURRENT));

        NotificationManagerCompat.from(this).notify(1, mBuilder.build());

    }
}
