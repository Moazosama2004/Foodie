package com.example.foodie.data.onboarding;

import android.content.Context;

import com.example.foodie.data.onboarding.datasource.local.OnBoardingLocalDataSource;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class OnboardingRepo {

    private final OnBoardingLocalDataSource localDataSource;

    public OnboardingRepo(Context context) {
        this.localDataSource = new OnBoardingLocalDataSource(context);
    }


    public Single<Boolean> userIsLoggedIn() {
        return localDataSource.userIsLoggedIn();
    }

    public Completable setUserLoggedIn(boolean isLoggedIn) {
        return localDataSource.setUserLoggedIn(isLoggedIn);
    }


    public Single<Boolean> isOnboardingSeen() {
        return localDataSource.isOnboardingSeen();
    }

    public Completable setOnboardingSeen(boolean seen) {
        return localDataSource.setOnboardingSeen(seen);
    }
}
