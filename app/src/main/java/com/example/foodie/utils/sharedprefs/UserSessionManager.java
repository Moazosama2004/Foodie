package com.example.foodie.utils.sharedprefs;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface UserSessionManager {
    Single<Boolean> isLoggedIn();

    Completable setLoggedIn(boolean loggedIn);
}
