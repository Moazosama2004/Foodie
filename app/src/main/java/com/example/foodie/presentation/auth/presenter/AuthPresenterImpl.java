package com.example.foodie.presentation.auth.presenter;

import android.app.Activity;
import android.util.Log;

import com.example.foodie.data.auth.datasource.AuthRepo;
import com.example.foodie.presentation.auth.view.AuthView;

public class AuthPresenterImpl implements AuthPresenter {

    private final AuthRepo authRepo;
    private final AuthView authView;


    public AuthPresenterImpl(AuthView authView, Activity activity) {
        this.authRepo = new AuthRepo(activity);
        this.authView = authView;
    }

    @Override
    public void login(String email, String password) {
        Log.d("TAG", "start login: " + email);

        authView.showLoading();
        authRepo.login(email, password);
        Log.d("TAG", "after login: " + email);
        authView.hideLoading();
        authView.navigateToHome();
    }

    @Override
    public void register(String email, String password) {
        authView.showLoading();
        authRepo.register(email, password);
        authView.hideLoading();
        authView.navigateToHome();
    }

    @Override
    public void firebaseWithGoogle() {
        authView.showLoading();
        authRepo.firebaseWithGoogle();
        authView.hideLoading();
        authView.navigateToHome();
    }

    @Override
    public void setUserLoggedIn() {
        authRepo.setUserLoggedIn();
        authView.navigateToHome();
    }
}
