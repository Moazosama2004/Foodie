package com.example.foodie.data.onboarding.datasource.local;

import android.content.Context;

import com.example.foodie.utils.sharedprefs.SharedPrefsManager;

public class OnBoardingLocalDataSource {

    private final SharedPrefsManager sharedPreferences;

    public OnBoardingLocalDataSource(Context context) {
        this.sharedPreferences = SharedPrefsManager.getInstance(context);
    }


    public boolean userIsLoggedIn() {
        return sharedPreferences.isLoggedIn();
    }

    public void setUserLoggedIn(boolean isLoggedIn) {
        sharedPreferences.setLoggedIn(isLoggedIn);
    }


    public boolean isOnboardingSeen() {
        return sharedPreferences.isSeen();
    }

    public void setOnboardingSeen(boolean seen) {
        sharedPreferences.setSeen(seen);
    }
}
