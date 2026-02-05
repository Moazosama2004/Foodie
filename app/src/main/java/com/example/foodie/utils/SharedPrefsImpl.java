package com.example.foodie.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefsImpl implements SharedPrefsService {
    // TODO : SEPERATE IT

    private static final String PREF_NAME = "foodie_prefs";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_ONBOARDING_SEEN = "onboarding_seen";

    private static SharedPrefsImpl INSTANCE;
    private SharedPreferences sharedPreferences;

    private SharedPrefsImpl(Context context) {
        sharedPreferences =
                context.getApplicationContext()
                        .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPrefsService getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new SharedPrefsImpl(context);
        }
        return INSTANCE;
    }


    @Override
    public boolean userIsLoggedIn() {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    @Override
    public void setUserLoggedIn(boolean isLoggedIn) {
        sharedPreferences.edit()
                .putBoolean(KEY_IS_LOGGED_IN, isLoggedIn)
                .apply();
    }


    @Override
    public boolean isOnboardingSeen() {
        return sharedPreferences.getBoolean(KEY_ONBOARDING_SEEN, false);
    }

    @Override
    public void setOnboardingSeen(boolean seen) {
        sharedPreferences.edit()
                .putBoolean(KEY_ONBOARDING_SEEN, seen)
                .apply();
    }
}
