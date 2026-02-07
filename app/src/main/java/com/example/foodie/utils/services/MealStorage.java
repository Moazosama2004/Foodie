package com.example.foodie.utils.services;

import com.example.foodie.data.home.model.response.Meal;

public interface MealStorage {
    void saveMeal(Meal meal, StorageCallback callback);
    void deleteMeal(String mealId, StorageCallback callback);
    void getAllMealsById(StorageCallback callback);
}
