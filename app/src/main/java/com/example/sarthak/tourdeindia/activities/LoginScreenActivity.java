package com.example.sarthak.tourdeindia.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.sarthak.tourdeindia.R;
import com.example.sarthak.tourdeindia.utils.Constants;

public class LoginScreenActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText mUsernameEt;
    private EditText mPasswordEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        mUsernameEt = (EditText) findViewById(R.id.et_username);
        mPasswordEt = (EditText) findViewById(R.id.et_password);

        Button mLoginButton = (Button) findViewById(R.id.button_login);

        // button onClick listener
        if (mLoginButton != null) {
            mLoginButton.setOnClickListener(this);
        }
    }

    @Override
    public void onBackPressed() {

        // dialog prompt to exit application
        exitApplication();
    }

    @Override
    protected void onStop() {
        super.onStop();

        // clear the text fields when user leaves the activity
        mUsernameEt.setText("");
        mPasswordEt.setText("");
    }

    //------------------------------------------------------------
    // button onClick listener
    //------------------------------------------------------------
    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.button_login :

                // log in user
                login();
        }
    }

    /**
     * Log in user with pre-defined username and password and launch HomeScreenActivity
     */
    private void login() {

        String username = mUsernameEt.getText().toString();
        String password = mPasswordEt.getText().toString();

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password)) {

            Toast.makeText(LoginScreenActivity.this, R.string.alert_login_message, Toast.LENGTH_SHORT).show();
        } else {

            if (username.equals(Constants.LOGIN_USERNAME) && password.equals(Constants.LOGIN_PASSWORD)){

                SharedPreferences loginStatus = getSharedPreferences(Constants.MY_SHARED_PREFERENCES, MODE_PRIVATE);
                SharedPreferences.Editor editor = loginStatus.edit();
                editor.putBoolean(Constants.PREF_LOGIN_STATUS, true);
                editor.apply();

                Intent intent = new Intent(LoginScreenActivity.this, HomeScreenActivity.class);
                startActivity(intent);
                finish();
            } else {

                Toast.makeText(LoginScreenActivity.this, R.string.invalid_login_toast, Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * Prompt the user to exit the application
     */
    private void exitApplication() {

        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.alert_exit))
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                }).setNegativeButton("No", null).show();
    }
}
