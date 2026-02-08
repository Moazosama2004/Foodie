package com.example.foodie.presentation.profile.presenter;

import android.app.Activity;

import com.example.foodie.data.auth.datasource.AuthRepo;
import com.example.foodie.presentation.profile.view.ProfileView;

public class ProfilePresenterImpl implements ProfilePresenter {
    private AuthRepo authRepo;
    private ProfileView view;

    public ProfilePresenterImpl(Activity activity , ProfileView view) {
        authRepo = new AuthRepo(activity);
        this.view = view;
    }

    @Override
    public void logout() {
        authRepo.logout();

        view.goToAuth();
    }
}
