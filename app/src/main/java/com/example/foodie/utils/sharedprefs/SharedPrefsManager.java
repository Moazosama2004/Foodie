package com.example.foodie.utils.sharedprefs;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefsManager implements UserSessionManager, OnboardingManager {

    private static final String PREF_NAME = "foodie_prefs";
    private static final String KEY_IS_LOGGED_IN = "is_logged_in";
    private static final String KEY_ONBOARDING_SEEN = "onboarding_seen";

    private static final String KEY_USER_ID = "user_id";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL = "email";

    private static volatile SharedPrefsManager INSTANCE;
    private final SharedPreferences prefs;

    private SharedPrefsManager(Context context) {
        prefs = context.getApplicationContext()
                .getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPrefsManager getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SharedPrefsManager.class) {
                if (INSTANCE == null) {
                    INSTANCE = new SharedPrefsManager(context);
                }
            }
        }
        return INSTANCE;
    }


    // User session
    @Override
    public boolean isLoggedIn() {
        return prefs.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    @Override
    public void setLoggedIn(boolean loggedIn) {
        prefs.edit().putBoolean(KEY_IS_LOGGED_IN, loggedIn).apply();

    }

    // Onboarding


    @Override
    public boolean isSeen() {
        return prefs.getBoolean(KEY_ONBOARDING_SEEN, false);

    }

    @Override
    public void setSeen(boolean seen) {
        prefs.edit().putBoolean(KEY_ONBOARDING_SEEN, seen).apply();

    }

    // Save user info
    public void saveUser(String userId, String username, String email) {
        prefs.edit()
                .putString(KEY_USER_ID, userId)
                .putString(KEY_USERNAME, username)
                .putString(KEY_EMAIL, email)
                .apply();
    }

    // Getters
    public String getUserId() {
        return prefs.getString(KEY_USER_ID, null);
    }

    public String getUsername() {
        return prefs.getString(KEY_USERNAME, null);
    }

    public String getEmail() {
        return prefs.getString(KEY_EMAIL, null);
    }

    // Clear user info
    public void clearUser() {
        prefs.edit()
                .remove(KEY_USER_ID)
                .remove(KEY_USERNAME)
                .remove(KEY_EMAIL)
                .apply();
    }


}
