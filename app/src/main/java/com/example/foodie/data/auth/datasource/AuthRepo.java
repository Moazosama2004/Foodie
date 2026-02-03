package com.example.foodie.data.auth.datasource;

import android.app.Activity;

import com.example.foodie.data.auth.datasource.remote.AuthRemoteDataSource;

public class AuthRepo {
    private final AuthRemoteDataSource authRemoteDataSource;

    public AuthRepo(Activity activity) {
        this.authRemoteDataSource = new AuthRemoteDataSource(activity);
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
}
