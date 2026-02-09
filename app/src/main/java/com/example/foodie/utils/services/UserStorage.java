package com.example.foodie.utils.services;

import com.example.foodie.data.core.model.User;
import com.example.foodie.data.home.model.response.Meal;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface UserStorage {

    Completable saveUser(User user);
    Completable deleteUser(String userId);
    Single<User> getUserById(String userId);
}
