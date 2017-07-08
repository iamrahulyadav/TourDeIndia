package com.example.sarthak.tourdeindia.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.sarthak.tourdeindia.utils.Constants;

public class SplashScreenActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        launchActivity();
    }

    /**
     * Check shared preferences for value of 'loginStatus'.
     * Launch LoginScreenActivity if 'loginStatus' returns false. Else launch HomeScreenActivity.
     */
    private void launchActivity() {

        // access shared preferences for login status
        SharedPreferences loginStatusPrefs = getSharedPreferences(Constants.MY_SHARED_PREFERENCES, MODE_PRIVATE);
        boolean loginStatus = loginStatusPrefs.getBoolean(Constants.PREF_LOGIN_STATUS, false);

        if (loginStatus) {
            // start HomeScreenActivity
            startActivity(new Intent(SplashScreenActivity.this, HomeScreenActivity.class));
        } else {
            // start LoginScreenActivity
            startActivity(new Intent(SplashScreenActivity.this, LoginScreenActivity.class));
        }

        // remove SplashActivity from stack
        finish();
    }

}
