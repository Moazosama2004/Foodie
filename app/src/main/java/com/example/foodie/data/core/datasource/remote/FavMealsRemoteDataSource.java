package com.example.foodie.data.core.datasource.remote;

import com.example.foodie.data.home.model.response.Meal;
import com.example.foodie.utils.firebase.storage.FirestoreMealStorage;
import com.example.foodie.utils.services.MealStorage;
import com.google.android.gms.auth.api.signin.internal.Storage;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class FavMealsRemoteDataSource {
    private final MealStorage storageService;

    public FavMealsRemoteDataSource() {
        this.storageService = new FirestoreMealStorage();
    }



    public Completable saveMeal(Meal meal) {
        return storageService.saveMeal(meal);
    }

    public Completable deleteMeal(String id) {
        return  storageService.deleteMeal(id );
    }

    public Single<List<Meal>> getAllFavMeals( ) {
        return storageService.getAllMeals();
    }
}
