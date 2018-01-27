package com.djavid.schoolapp.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.djavid.schoolapp.R;
import com.djavid.schoolapp.util.LogTags;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    // TODO: Where shall I put them?
    public static int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // google sign in
        _googleSignInClient = GoogleSignIn.getClient(this, GoogleSignInOptions.DEFAULT_SIGN_IN);
        findViewById(R.id.sign_in_button).setOnClickListener(this);

        if (getPreferences(MODE_PRIVATE).getString("identity", null) != null) {
            showDashboard();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sign_in_button:
                requestGoogleAccount();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void requestGoogleAccount() {
        Intent signInIntent = _googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void handleSignInResult(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            onGoogleSignInSuccess(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(LogTags.SignIn, "signInResult:failed code=" + e.getStatusCode());
            onGoogleSignInError();
        }
    }

    private void onGoogleSignInSuccess(GoogleSignInAccount account) {
        SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
        editor.putString("identity", account.getId());
        for (int i = 0; i < 5; i++) {
            if (editor.commit()) {
                showDashboard();
                return;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException ignored) {
            }
        }
        onGoogleSignInError();
    }

    private void onGoogleSignInError() {
        findViewById(R.id.note_google_sign_in_error).setVisibility(View.VISIBLE);
    }

    private void showDashboard() {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }

    private GoogleSignInClient _googleSignInClient;
}
