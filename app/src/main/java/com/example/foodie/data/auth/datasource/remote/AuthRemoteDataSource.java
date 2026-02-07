package com.example.foodie.data.auth.datasource.remote;

import android.app.Activity;

import com.example.foodie.data.core.model.User;
import com.example.foodie.utils.firebase.auth.FirebaseAuthImpl;
import com.example.foodie.utils.firebase.storage.FirestoreUserStorage;
import com.example.foodie.utils.services.AuthCallback;
import com.example.foodie.utils.services.AuthService;
import com.example.foodie.utils.services.StorageCallback;
import com.example.foodie.utils.services.UserStorage;

import java.util.ArrayList;

public class AuthRemoteDataSource {

    private final AuthService firebaseAuthService;
    private final UserStorage userStorage;

    public AuthRemoteDataSource(Activity activity) {
        this.firebaseAuthService = new FirebaseAuthImpl(activity);
        this.userStorage = new FirestoreUserStorage();
    }

    public void login(String email, String password, AuthCallback authCallback) {
        firebaseAuthService.login(email, password, new AuthCallback() {
            @Override
            public void onSuccess() {
                String userId = firebaseAuthService.getCurrentUserId();
                if (userId == null) {
                    authCallback.onError("Failed to get user ID after login.");
                    return;
                }

                User user = new User(userId, "Default Username", email, new ArrayList<>());

                userStorage.saveUser(user, new StorageCallback() {
                    @Override
                    public void onSuccess() {
                        authCallback.onSuccess();
                    }

                    @Override
                    public void onError(String message) {
                        authCallback.onError("User saved, but error: " + message);
                    }
                });
            }

            @Override
            public void onError(String errorMessage) {
                authCallback.onError(errorMessage);
            }
        });
    }

    public void register(String username,String email, String password, AuthCallback authCallback) {
        firebaseAuthService.register(email, password, new AuthCallback() {
            @Override
            public void onSuccess() {
                String userId = firebaseAuthService.getCurrentUserId();
                if (userId == null) {
                    authCallback.onError("Failed to get user ID after registration.");
                    return;
                }

                User user = new User(userId, username, email, new ArrayList<>());

                userStorage.saveUser(user, new StorageCallback() {
                    @Override
                    public void onSuccess() {
                        authCallback.onSuccess();
                    }

                    @Override
                    public void onError(String message) {
                        authCallback.onError("User saved, but error: " + message);
                    }
                });
            }

            @Override
            public void onError(String errorMessage) {
                authCallback.onError(errorMessage);
            }
        });
    }
    public void firebaseWithGoogle() {
        firebaseAuthService.signInWithGoogle();
    }

    public void logout() {
        firebaseAuthService.logout();
    }
}
