package com.example.foodie.utils.services;

import com.example.foodie.data.core.model.User;
import com.example.foodie.data.home.model.response.Meal;

import java.util.List;

public interface StorageCallback {
    void onSuccess();
    void onError(String message);
    void onSuccessWithResult(List<Meal> meals);
    void onSuccessWithUserData(User user);

}
