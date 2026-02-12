package com.example.foodie.utils.services;

import com.example.foodie.core.model.User;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface UserStorage {

    Completable saveUser(User user);

    Completable deleteUser(String userId);

    Single<User> getUserById(String userId);
}
