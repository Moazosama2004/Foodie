package com.example.foodie.utils.sharedprefs;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface OnboardingManager {
    Single<Boolean> isSeen();
    Completable setSeen(boolean seen);
}
