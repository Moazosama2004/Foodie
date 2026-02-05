package com.example.foodie.presentation.onboarding.presenter;

import android.content.Context;
import android.util.Log;

import com.example.foodie.data.onboarding.OnboardingRepo;
import com.example.foodie.presentation.onboarding.view.OnboardingView;

public class OnboardingPresenterImpl implements OnboardingPresenter {
    private final OnboardingRepo repo;
    private final OnboardingView view;

    public OnboardingPresenterImpl(Context context, OnboardingView view) {
        this.repo = new OnboardingRepo(context);
        this.view = view;
    }

    @Override
    public void decideStartDestination() {
        if (!repo.isOnboardingSeen()) {
            view.showOnboarding();
            Log.d("TAG", "decideStartDestination: onboarding");
            repo.setOnboardingSeen(true);
            return;
        }

        if (!repo.userIsLoggedIn()) {
            Log.d("TAG", "decideStartDestination: login");
            view.showLogin();
            return;
        }
        Log.d("TAG", "decideStartDestination: home");

        view.showHome();
    }
}
