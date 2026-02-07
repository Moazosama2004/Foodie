package com.example.foodie.data.core.datasource.remote;

import com.example.foodie.data.home.model.response.Meal;
import com.example.foodie.utils.firebase.storage.FirestoreMealStorage;
import com.example.foodie.utils.services.MealStorage;
import com.example.foodie.utils.services.StorageCallback;
import com.google.android.gms.auth.api.signin.internal.Storage;

public class FavMealsRemoteDataSource {
    private final MealStorage storageService;

    public FavMealsRemoteDataSource() {
        this.storageService = new FirestoreMealStorage();
    }

    public void saveMeal(Meal meal , StorageCallback callback) {
        storageService.saveMeal(meal , callback);
    }

    public void deleteMeal(String id,StorageCallback callback) {
        storageService.deleteMeal(id , callback);
    }
}
