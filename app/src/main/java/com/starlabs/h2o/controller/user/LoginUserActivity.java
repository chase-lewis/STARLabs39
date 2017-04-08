package com.starlabs.h2o.controller.user;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.starlabs.h2o.R;
import com.starlabs.h2o.controller.HomeActivity;
import com.starlabs.h2o.dao.ContentProvider;
import com.starlabs.h2o.dao.ContentProviderFactory;
import com.starlabs.h2o.model.user.User;

import java.util.function.Consumer;

/**
 * The login screen for username/password authentication
 *
 * @author tejun, chase
 */
public class LoginUserActivity extends AppCompatActivity {

    private static final int MAX_USER_LENGTH = 20;
    private static final int MIN_USER_LENGTH = 3;
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    @Nullable
    private UserLoginTask mAuthTask = null;
    // UI references
    private AutoCompleteTextView mUsernameView;
    private EditText mPasswordView;
    private ProgressBar mProgressView;
    private TextView mForgotPasswordView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the layout
        setContentView(R.layout.activity_login);

        // Set up the username form
        mUsernameView = (AutoCompleteTextView) findViewById(R.id.login_username);
        mUsernameView.setText("");

        // Set up the password form
        mPasswordView = (EditText) findViewById(R.id.login_password);
        mPasswordView.setText("");
        mPasswordView.setOnEditorActionListener((textView, id, keyEvent) -> (id == R.id.login)
                || (id == EditorInfo.IME_NULL));

        // Set up the forgot password text
        mForgotPasswordView = (TextView) findViewById(R.id.login_forgot_password);
        mForgotPasswordView.setOnTouchListener((v, event) -> openForgotPassword());

        // Set up the sign in button
        Button mSignInButton = (Button) findViewById(R.id.login_sign_in);
        mSignInButton.setOnClickListener(view -> attemptLogin());

        // Set up the cancel button
        Button cancelSignInButton = (Button) findViewById(R.id.login_cancel);
        cancelSignInButton.setOnClickListener(view -> finish());

        // Set up the progress bar that shows logging in
        // Note: This is hidden on startup
        mProgressView = (ProgressBar) findViewById(R.id.login_progress);
    }

    /**
     * Transitions user to the forgot password activity.
     *
     * @return always true (always consumed event)
     */
    private boolean openForgotPassword() {
        Intent intent = new Intent(LoginUserActivity.this, PasswordRecoveryActivity.class);
        startActivity(intent);
        return true;
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid username, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors
        mUsernameView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        Editable usName = mUsernameView.getText();
        final String username = usName.toString();
        Editable passName = mPasswordView.getText();
        final String password = passName.toString();

        boolean cancel = false;
        View focusView = null;

        // Check for valid fields
        if (TextUtils.isEmpty(username)) {
            // Check if the username is empty
            mUsernameView.setError("A username is required");
            focusView = mUsernameView;
            cancel = true;
        } else if ((username.length() > MAX_USER_LENGTH)
                || (username.length() < MIN_USER_LENGTH)) {
            // Check if the username is not valid
            mUsernameView.setError("The username must be 3-20 characters");
            focusView = mUsernameView;
            cancel = true;
        } else if (TextUtils.isEmpty(password)) {
            // Check if the password is not empty
            mPasswordView.setError("A password is required");
            focusView = mPasswordView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner and wait for 2 seconds for the user to be logged in.
            // Happens in the background
            showProgress(true);
            mAuthTask = new UserLoginTask();
            mAuthTask.execute((Void) null);

            // Check if the user exists in the content provider
            ContentProvider contentProvider = ContentProviderFactory.getDefaultContentProvider();
            Consumer<User> onUserFound = user -> {
                if (user.isCorrectPassword(password)) {
                    // Password matches!
                    // Call the async success method
                    mAuthTask.setUser(user);
                    mAuthTask.onPostExecute(true);
                } else {
                    mAuthTask.onPostExecute(false);
                }
            };
            contentProvider.getSingleUser(onUserFound, username);
        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        mProgressView.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
        mProgressView.setIndeterminate(show);
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    private class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final int WAIT = 3000;
        private User mUser;

        UserLoginTask() {
            // Do nothing
        }

        /**
         * Sets the user
         *
         * @param user the user
         */
        void setUser(User user) {
            this.mUser = user;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // Sleep for 2 seconds while attempting to login with the content provider
            try {
                Thread.sleep(WAIT);
            } catch (InterruptedException e) {
                return false;
            }

            // Login failed
            return false;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                // Save the user for the session
                ContentProvider contentProvider =
                        ContentProviderFactory.getDefaultContentProvider();
                contentProvider.setLoggedInUser(mUser);

                // Transition to the Map fragment in the Home Activity
                Intent profileIntent = new Intent(LoginUserActivity.this, HomeActivity.class);
                startActivity(profileIntent);
                finish();
            } else {
                // Login failed due to invalid username or a network connection
                mPasswordView.setError("The username and password combination was incorrect!");
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

