package com.example.foodie.presentation.profile.view;

public interface ProfileView {
    void goToAuth();

    void showUser(String username, String email);

    void showError(String message);
}
