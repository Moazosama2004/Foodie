package com.example.foodie.data.onboarding;

import android.content.Context;

import com.example.foodie.data.onboarding.datasource.local.OnBoardingLocalDataSource;

public class OnboardingRepo {

    private final OnBoardingLocalDataSource localDataSource;

    public OnboardingRepo(Context context) {
        this.localDataSource = new OnBoardingLocalDataSource(context);
    }


    public boolean userIsLoggedIn() {
        return localDataSource.userIsLoggedIn();
    }

    public void setUserLoggedIn(boolean isLoggedIn) {
        localDataSource.setUserLoggedIn(isLoggedIn);
    }


    public boolean isOnboardingSeen() {
        return localDataSource.isOnboardingSeen();
    }

    public void setOnboardingSeen(boolean seen) {
        localDataSource.setOnboardingSeen(seen);
    }
}
