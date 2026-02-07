package com.example.foodie.data.auth.datasource.remote;

import android.app.Activity;

import com.example.foodie.utils.firebase.auth.FirebaseAuthImpl;
import com.example.foodie.utils.services.AuthService;

public class AuthRemoteDataSource {
    private final AuthService firebaseAuthService;

    public AuthRemoteDataSource(Activity activity) {
        this.firebaseAuthService = new FirebaseAuthImpl(activity);
    }

    public void login(String email, String password) {
        firebaseAuthService.login(email, password);
    }

    public void register(String email, String password) {
        firebaseAuthService.register(email, password);
    }

    public void firebaseWithGoogle() {
        firebaseAuthService.signInWithGoogle();
    }

    public void logout() {
        firebaseAuthService.logout();
    }

}
