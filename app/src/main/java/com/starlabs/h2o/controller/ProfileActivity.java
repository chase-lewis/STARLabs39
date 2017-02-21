package com.starlabs.h2o.controller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.starlabs.h2o.R;
import com.starlabs.h2o.model.User;

public class ProfileActivity extends AppCompatActivity {


    private EditText nameField;
    private EditText emailField;
    private EditText addressField;


    private User _user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        nameField = (EditText) findViewById(R.id.user_profile_name_field);
        emailField = (EditText) findViewById(R.id.user_profile_email);
        addressField = (EditText) findViewById(R.id.user_profile_address);

        //Not Sure if we need to check if there is a parcelable object, since the user should be created by now
//        if (getIntent().hasExtra(RegisterActivity.REG_INTENT)) {
        _user = (User) getIntent().getParcelableExtra(RegisterActivity.REG_INTENT);
//        } else {
//
//        }

        nameField.setText(_user.getName());
        emailField.setText(_user.getEmail());
        addressField.setText(_user.getAddress());

        Button profileDoneButton = (Button) findViewById(R.id.profile_done_button);
        profileDoneButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onProfileDonePressed(view);
            }
        });

        Button profileCancelButton = (Button) findViewById(R.id.profile_cancel_button);
        profileCancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onProfileCancelPressed(view);
            }
        });
    }

    protected void onProfileDonePressed(View view) {
        Log.d("Edit", "Add User Profile");

        _user.setName(nameField.getText().toString());
        _user.setEmail(emailField.getText().toString());
        _user.setAddress(addressField.getText().toString());

        //Chase do thing here
    }

    protected void onProfileCancelPressed(View view) {
        Log.d("Edit", "Cancel User Profile");
        finish();
    }

}

