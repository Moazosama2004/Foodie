package com.example.foodie.data.auth.datasource;

import android.app.Activity;
import android.content.Context;

import com.example.foodie.data.auth.datasource.local.AuthLocalDataSource;
import com.example.foodie.data.auth.datasource.remote.AuthRemoteDataSource;
import com.example.foodie.utils.firebase.auth.FirebaseAuthImpl;

import io.reactivex.rxjava3.core.Completable;

public class AuthRepo {

    private final AuthRemoteDataSource authRemoteDataSource;
    private final AuthLocalDataSource authLocalDataSource;

    public AuthRepo(Context context) {
        this.authRemoteDataSource = new AuthRemoteDataSource((Activity) context);
        this.authLocalDataSource = new AuthLocalDataSource(context.getApplicationContext());
    }

    public Completable login(String email, String password) {
        return authRemoteDataSource.login(email, password);
    }

    public Completable register(String username, String email, String password) {
        return authRemoteDataSource.register(username, email, password);
    }

    public Completable signInWithGoogle(String idToken) {
        return authRemoteDataSource.signInWithGoogle(idToken);
    }

    public Completable logout() {
        return authRemoteDataSource.logout();
    }

    public FirebaseAuthImpl getAuthService() {
        return (FirebaseAuthImpl) this.authRemoteDataSource.getAuthService();
    }
}
