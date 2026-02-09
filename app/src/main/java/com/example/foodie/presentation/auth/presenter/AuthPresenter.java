package com.example.foodie.presentation.auth.presenter;

import com.example.foodie.utils.services.AuthCallback;

public interface AuthPresenter {
    void login(String email, String password);

    void register(String username, String email, String password);

    void firebaseWithGoogle(String idToken);


    void clear();

    void setUserLoggedIn();
}
