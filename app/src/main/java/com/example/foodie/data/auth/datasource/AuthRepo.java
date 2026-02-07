package com.example.foodie.data.auth.datasource;

import android.app.Activity;

import com.example.foodie.data.auth.datasource.local.AuthLocalDataSource;
import com.example.foodie.data.auth.datasource.remote.AuthRemoteDataSource;
import com.example.foodie.utils.services.AuthCallback;

public class AuthRepo {
    private final AuthRemoteDataSource authRemoteDataSource;
    private final AuthLocalDataSource authLocalDataSource;


    // TODO : MEMORY LEAK
    public AuthRepo(Activity activity) {
        this.authRemoteDataSource = new AuthRemoteDataSource(activity);
        this.authLocalDataSource = new AuthLocalDataSource(activity.getApplicationContext());
    }

    public void login(String email, String password, AuthCallback callback) {
        authRemoteDataSource.login(email, password,callback);
    }

    public void register(String email, String password, AuthCallback callback) {
        authRemoteDataSource.register(email, password,callback);
    }

    public void logout() {
        authRemoteDataSource.logout();
    }

    public void firebaseWithGoogle() {
        authRemoteDataSource.firebaseWithGoogle();
    }

    public void setUserLoggedIn() {
        authLocalDataSource.setUserLoggedIn();
    }
}
