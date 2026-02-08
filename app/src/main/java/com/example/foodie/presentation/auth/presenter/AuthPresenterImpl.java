package com.example.foodie.presentation.auth.presenter;

import android.app.Activity;
import android.util.Log;

import com.example.foodie.data.auth.datasource.AuthRepo;
import com.example.foodie.presentation.auth.view.AuthView;
import com.example.foodie.utils.services.AuthCallback;

public class AuthPresenterImpl implements AuthPresenter {

    private final AuthRepo authRepo;
    private final AuthView authView;


    public AuthPresenterImpl(AuthView authView, Activity activity) {
        this.authRepo = new AuthRepo(activity);
        this.authView = authView;
    }

    @Override
    public void login(String email, String password) {
        authView.showLoading();
        authRepo.login(email, password, new AuthCallback() {
            @Override
            public void onSuccess() {
                Log.d("Auth" , "onSuccess:");
                authView.hideLoading();
                authView.navigateToHome();
            }

            @Override
            public void onError(String message) {
                authView.hideLoading();
                authView.showError(message);
            }
        });

    }

    @Override
    public void register(String username , String email, String password) {
        authView.showLoading();
        authRepo.register(username,email, password, new AuthCallback() {
            @Override
            public void onSuccess() {
                authView.hideLoading();
                authView.navigateToHome();
            }

            @Override
            public void onError(String message) {
                authView.hideLoading();
                authView.showError(message);
            }
        });

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
    }
}
