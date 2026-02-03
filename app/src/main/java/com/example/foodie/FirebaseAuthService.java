package com.example.foodie;

public interface FirebaseAuthService {
    void login(String email , String password);
    void register(String email , String password);
    void firebaseWithGoogle();
    void logout();
}
