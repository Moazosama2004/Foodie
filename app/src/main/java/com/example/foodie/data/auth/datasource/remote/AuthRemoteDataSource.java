package com.example.foodie.data.auth.datasource.remote;

import android.app.Activity;
import android.util.Log;

import com.example.foodie.core.model.User;
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

    // Login
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

    // Register

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

    // Google Sign-in
    public Completable signInWithGoogle(String idToken) {
        return authService.signInWithGoogle(idToken)
                .andThen(authService.getCurrentUser())
                .flatMapCompletable(firebaseUser -> {
                    if (firebaseUser == null) {
                        return Completable.error(
                                new IllegalStateException("FirebaseUser is null after Google login")
                        );
                    }

                    String userId = firebaseUser.getUid();
                    String name = firebaseUser.getDisplayName() != null ? firebaseUser.getDisplayName() : "Google User";
                    String email = firebaseUser.getEmail() != null ? firebaseUser.getEmail() : "";

                    return userStorage.getUserById(userId)
                            .flatMapCompletable(user -> {
                                return sharedPrefs.saveUser(
                                                user.getUserId(),
                                                user.getUsername(),
                                                user.getEmail()
                                        )
                                        .andThen(sharedPrefs.setLoggedIn(true));
                            })
                            .onErrorResumeNext(throwable -> {
                                User newUser = new User(
                                        userId,
                                        name,
                                        email,
                                        new ArrayList<>()
                                );

                                return userStorage.saveUser(newUser)
                                        .andThen(sharedPrefs.saveUser(
                                                newUser.getUserId(),
                                                newUser.getUsername(),
                                                newUser.getEmail()
                                        ))
                                        .andThen(sharedPrefs.setLoggedIn(true));
                            });
                });
    }


    // Logout

    public Completable logout() {
        return authService.logout()
                .andThen(sharedPrefs.clearUser())
                .andThen(sharedPrefs.setLoggedIn(false));
    }

    public FirebaseAuthImpl getAuthService() {
        return (FirebaseAuthImpl) this.authService;
    }
}
