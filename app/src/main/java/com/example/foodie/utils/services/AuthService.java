package com.example.foodie.utils.services;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface AuthService {

    Completable login(String email, String password);

    Completable register(String email, String password);

    Completable signInWithGoogle(String idToken);

    Completable logout();

    Single<String> getCurrentUserId();
}
