package com.example.foodie.data.onboarding.datasource.local;

import android.content.Context;

import com.example.foodie.utils.SharedPrefsImpl;
import com.example.foodie.utils.SharedPrefsService;

public class OnBoardingLocalDataSource {

    private final SharedPrefsService sharedPreferences;

    public OnBoardingLocalDataSource(Context context) {
        this.sharedPreferences = SharedPrefsImpl.getInstance(context);
    }


    public boolean userIsLoggedIn() {
        return sharedPreferences.userIsLoggedIn();
    }

    public void setUserLoggedIn(boolean isLoggedIn) {
        sharedPreferences.setUserLoggedIn(isLoggedIn);
    }


    public boolean isOnboardingSeen() {
        return sharedPreferences.isOnboardingSeen();
    }

    public void setOnboardingSeen(boolean seen) {
        sharedPreferences.setOnboardingSeen(seen);
    }
}
