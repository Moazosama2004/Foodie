package com.example.foodie.utils.services;

import com.example.foodie.data.core.model.User;
import com.example.foodie.data.home.model.response.Meal;

public interface UserStorage {
    void saveUser(User user, StorageCallback callback);
    void deleteUser(String userId, StorageCallback callback);
}
