package com.example.foodie.utils.sharedprefs;

import android.content.Context;
import android.content.SharedPreferences;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

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


    // Save user info
    public Completable saveUser(String userId, String username, String email) {
        return Completable.fromAction(() ->
                prefs.edit()
                        .putString(KEY_USER_ID, userId)
                        .putString(KEY_USERNAME, username)
                        .putString(KEY_EMAIL, email)
                        .apply()
        ).subscribeOn(Schedulers.io());
    }

    // Getters
    public Single<String> getUserId() {
        return Single.fromCallable(() ->
                prefs.getString(KEY_USER_ID, "")
        ).subscribeOn(Schedulers.io());
    }

    public Single<String> getUsername() {
        return Single.fromCallable(() ->
                prefs.getString(KEY_USERNAME, "")
        ).subscribeOn(Schedulers.io());
    }

    public Single<String> getEmail() {
        return Single.fromCallable(() ->
                prefs.getString(KEY_EMAIL, "")
        ).subscribeOn(Schedulers.io());
    }

    public Completable clearUser() {
        return Completable.fromAction(() ->
                prefs.edit()
                        .remove(KEY_USER_ID)
                        .remove(KEY_USERNAME)
                        .remove(KEY_EMAIL)
                        .apply()
        ).subscribeOn(Schedulers.io());
    }


    @Override
    public Single<Boolean> isSeen() {
        return Single.fromCallable(() ->
                prefs.getBoolean(KEY_ONBOARDING_SEEN, false)
        ).subscribeOn(Schedulers.io());
    }

    @Override
    public Completable setSeen(boolean seen) {
        return Completable.fromAction(() ->
                prefs.edit().putBoolean(KEY_ONBOARDING_SEEN, seen).apply()
        ).subscribeOn(Schedulers.io());
    }

    @Override
    public Single<Boolean> isLoggedIn() {
        return Single.fromCallable(() ->
                prefs.getBoolean(KEY_IS_LOGGED_IN, false)
        ).subscribeOn(Schedulers.io());
    }

    @Override
    public Completable setLoggedIn(boolean loggedIn) {
        return Completable.fromAction(() ->
                prefs.edit().putBoolean(KEY_IS_LOGGED_IN, loggedIn).apply()
        ).subscribeOn(Schedulers.io());
    }
}
