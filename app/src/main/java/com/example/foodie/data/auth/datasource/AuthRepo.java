package com.example.foodie.data.auth.datasource;

import android.app.Activity;

import com.example.foodie.data.auth.datasource.local.AuthLocalDataSource;
import com.example.foodie.data.auth.datasource.remote.AuthRemoteDataSource;

public class AuthRepo {
    private final AuthRemoteDataSource authRemoteDataSource;
    private final AuthLocalDataSource authLocalDataSource;


    // TODO : MEMORY LEAK
    public AuthRepo(Activity activity) {
        this.authRemoteDataSource = new AuthRemoteDataSource(activity);
        this.authLocalDataSource = new AuthLocalDataSource(activity.getApplicationContext());
    }

    public void login(String email, String password) {
        authRemoteDataSource.login(email, password);
    }

    public void register(String email, String password) {
        authRemoteDataSource.register(email, password);
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
