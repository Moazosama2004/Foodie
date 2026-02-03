package com.example.foodie.presentation.auth.presenter;

public interface AuthPresenter {
    void login(String email, String password);
    void register(String email, String password);
    void firebaseWithGoogle();
}
