package com.example.foodie.data.auth.datasource.remote;

import android.app.Activity;
import android.util.Log;

import com.example.foodie.data.core.model.User;
import com.example.foodie.utils.firebase.auth.FirebaseAuthImpl;
import com.example.foodie.utils.firebase.storage.FirestoreUserStorage;
import com.example.foodie.utils.services.AuthService;
import com.example.foodie.utils.services.UserStorage;
import com.example.foodie.utils.sharedprefs.SharedPrefsManager;

import java.util.ArrayList;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class AuthRemoteDataSource {

    private final AuthService authService;
    private final UserStorage userStorage;
    private final SharedPrefsManager sharedPrefs;

    public AuthRemoteDataSource(Activity activity) {
        this.authService = new FirebaseAuthImpl(activity);
        this.userStorage = new FirestoreUserStorage();
        this.sharedPrefs = SharedPrefsManager.getInstance(activity);
    }

    // ================= LOGIN =================

    public Completable login(String email, String password) {
        return authService.login(email, password)
                .andThen(authService.getCurrentUserId())
                .flatMap(userId -> {
                    if (userId == null) {
                        return Single.error(
                                new IllegalStateException("User ID is null after login")
                        );
                    }
                    return userStorage.getUserById(userId);
                })
                .flatMapCompletable(user -> {
                    Log.d("AuthRemoteDataSource",
                            "User loaded: " + user.getUsername());

                    return sharedPrefs.saveUser(
                                    user.getUserId(),
                                    user.getUsername(),
                                    user.getEmail()
                            )
                            .andThen(sharedPrefs.setLoggedIn(true));
                });
    }

    // ================= REGISTER =================

    public Completable register(String username, String email, String password) {
        return authService.register(email, password)
                .andThen(authService.getCurrentUserId())
                .flatMapCompletable(userId -> {
                    if (userId == null) {
                        return Completable.error(
                                new IllegalStateException("User ID is null after register")
                        );
                    }

                    User user = new User(
                            userId,
                            username,
                            email,
                            new ArrayList<>()
                    );

                    return userStorage.saveUser(user)
                            .andThen(sharedPrefs.saveUser(
                                    userId,
                                    username,
                                    email
                            ))
                            .andThen(sharedPrefs.setLoggedIn(true));
                });
    }

    // ================= GOOGLE SIGN-IN =================

    public Completable signInWithGoogle(String idToken) {
        return authService.signInWithGoogle(idToken)
                .andThen(authService.getCurrentUserId())
                .flatMap(userId -> {
                    if (userId == null) {
                        return Single.error(
                                new IllegalStateException("User ID is null after Google login")
                        );
                    }
                    return userStorage.getUserById(userId);
                })
                .flatMapCompletable(user ->
                        sharedPrefs.saveUser(
                                user.getUserId(),
                                user.getUsername(),
                                user.getEmail()
                        ).andThen(sharedPrefs.setLoggedIn(true))
                );
    }

    // ================= LOGOUT =================

    public Completable logout() {
        return authService.logout()
                .andThen(sharedPrefs.clearUser())
                .andThen(sharedPrefs.setLoggedIn(false));
    }

    public FirebaseAuthImpl getAuthService() {
        return (FirebaseAuthImpl) this.authService;
    }
}
