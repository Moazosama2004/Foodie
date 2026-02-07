package com.example.foodie.data.auth.datasource.remote;

import android.app.Activity;

import com.example.foodie.utils.firebase.auth.FirebaseAuthImpl;
import com.example.foodie.utils.services.AuthCallback;
import com.example.foodie.utils.services.AuthService;

public class AuthRemoteDataSource {
    private final AuthService firebaseAuthService;

    public AuthRemoteDataSource(Activity activity) {
        this.firebaseAuthService = new FirebaseAuthImpl(activity);
    }

    public void login(String email, String password, AuthCallback callback) {
        firebaseAuthService.login(email, password,callback);
    }

    public void register(String email, String password, AuthCallback callback) {
        firebaseAuthService.register(email, password,callback);
    }

    public void firebaseWithGoogle() {
        firebaseAuthService.signInWithGoogle();
    }

    public void logout() {
        firebaseAuthService.logout();
    }

}
