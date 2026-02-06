package com.example.foodie.data.core.datasource.remote;

import com.example.foodie.data.home.model.response.Meal;
import com.example.foodie.utils.FirebaseStorageImpl;
import com.example.foodie.utils.StorageService;

public class FavMealsRemoteDataSource {
    private final StorageService storageService;

    public FavMealsRemoteDataSource() {
        this.storageService = new FirebaseStorageImpl();
    }

    public void saveMeal(Meal meal) {
        storageService.saveMeal(meal);
    }

    public void deleteMeal(String id) {
        storageService.deleteMeal(id);
    }
}
