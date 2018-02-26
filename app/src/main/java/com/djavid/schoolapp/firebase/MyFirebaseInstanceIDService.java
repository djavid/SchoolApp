package com.djavid.schoolapp.firebase;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by Andrei on 27-Feb-18.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();

        Log.i("firebase", "onTokenRefresh " + FirebaseInstanceId.getInstance().getToken());
    }
}
