package com.djavid.schoolapp.view.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.djavid.schoolapp.App;
import com.djavid.schoolapp.R;
import com.djavid.schoolapp.model.users.TokenResponse;
import com.djavid.schoolapp.util.LogTags;
import com.google.firebase.iid.FirebaseInstanceId;

import retrofit2.HttpException;

/**
 * A login screen that offers login via email/password.
 */
public class EnterCodeActivity extends AppCompatActivity {

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private EditText mPasswordView;
    private EditText mNicknameView;
    private View mProgressView;

    private String _userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_code);

        mPasswordView = findViewById(R.id.password);
        mNicknameView = findViewById(R.id.nickname);

        Button mEnterCodeButton = findViewById(R.id.enter_code_button);
        mEnterCodeButton.setOnClickListener(view -> attemptLogin());

        mProgressView = findViewById(R.id.login_progress);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (App.getAppInstance().getPreferences().getToken() != null) {
            showDashboard();
        }

        _userID = App.getAppInstance().getPreferences().getIdentity();
        if (_userID == null) {
            showLanding();
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String password = mPasswordView.getText().toString();
        String nickname = mNicknameView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // validate password and nickname
        String error;

        if (!TextUtils.isEmpty(password) && (error = validatePassword(password)) != null) {
            mPasswordView.setError(error);
            focusView = mPasswordView;
            cancel = true;
        }

        if (!TextUtils.isEmpty(nickname) && (error = validateNickname(nickname)) != null) {
            mNicknameView.setError(getString(R.string.error_invalid_nickname));
            focusView = mNicknameView;
            cancel = true;
        }

        if (TextUtils.isEmpty(password) || TextUtils.isEmpty(nickname)) {
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            if (focusView != null) {
                focusView.requestFocus();
            }
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(password, nickname, _userID);
            mAuthTask.execute((Void) null);
        }
    }

    private String validatePassword(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4
                ? null
                : getString(R.string.error_invalid_password);
    }

    private String validateNickname(String nickname) {
        // letters, numbers, spaces, dots and commas
        return nickname.matches("[a-zA-Zа-яА-ЯёЁ0-9 \\.\\,]+")
                ? null
                : getString(R.string.error_invalid_nickname);
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String _nickname;
        private final String _userID;
        private final String _code;

        UserLoginTask(String code, String nickname, String userID) {
            _code = code;
            _nickname = nickname;
            _userID = userID;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                TokenResponse response = App.getAppInstance().getApi()
                        .login(_nickname, _userID, FirebaseInstanceId.getInstance().getToken()).blockingGet();
                Log.i(LogTags.SignIn, "login returned:" + response);
                App.getAppInstance().getPreferences().setToken(response.token);
                App.getAppInstance().getPreferences().setDisplayName(_nickname);
                App.getAppInstance().getPreferences().setLevel(response.getLevel());

                return response.token != null && !response.token.trim().isEmpty();
            } catch (HttpException e) {
                Log.w(LogTags.Exception, e);
                try {
                    TokenResponse response = App.getAppInstance().getApi()
                            .register(_nickname, _userID, _code).blockingGet();
                    Log.i(LogTags.SignIn, "register returned:" + response);
                    App.getAppInstance().getPreferences().setToken(response.token);
                    App.getAppInstance().getPreferences().setDisplayName(_nickname);
                    App.getAppInstance().getPreferences().setLevel(response.getLevel());

                    return response.token != null && !response.token.trim().isEmpty();
                } catch (HttpException q) {
                    Log.w(LogTags.Exception, q);
                    return false;
                }
            }
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                finish();
                showDashboard();
            } else {
                mPasswordView.setError(getString(R.string.error_invalid_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }

    private void showDashboard() {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }

    private void showLanding() {
        Intent intent = new Intent(this, LandingActivity.class);
        startActivity(intent);
    }
}

