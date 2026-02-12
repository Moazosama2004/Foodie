package com.example.foodie.data.onboarding.datasource.local;

import android.content.Context;

import com.example.foodie.utils.sharedprefs.SharedPrefsManager;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class OnBoardingLocalDataSource {

    private final SharedPrefsManager sharedPreferences;

    public OnBoardingLocalDataSource(Context context) {
        this.sharedPreferences = SharedPrefsManager.getInstance(context);
    }


    public Single<Boolean> userIsLoggedIn() {
        return sharedPreferences.isLoggedIn();
    }

//    public Completable setUserLoggedIn(boolean isLoggedIn) {
//        return sharedPreferences.setLoggedIn(isLoggedIn);
//    }


    public Single<Boolean> isOnboardingSeen() {
        return sharedPreferences.isSeen();
    }

    public Completable setOnboardingSeen(boolean seen) {
        return sharedPreferences.setSeen(seen);
    }
}
