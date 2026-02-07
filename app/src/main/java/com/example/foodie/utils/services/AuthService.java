package com.example.foodie.utils.services;

public interface AuthService {
    void login(String email, String password , AuthCallback callback);

    void register(String email, String password , AuthCallback callback);

    void signInWithGoogle();

    void logout();

    String getCurrentUserId();
}
