package com.example.foodie.utils.services;

public interface AuthCallback {
    void onSuccess();
    void onError(String message);
}
