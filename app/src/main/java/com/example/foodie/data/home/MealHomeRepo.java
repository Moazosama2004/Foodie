package com.example.foodie.data.home;

import com.example.foodie.data.home.datasource.remote.MealHomeRemoteDataSource;
import com.example.foodie.data.home.model.response.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class MealHomeRepo {
    private final MealHomeRemoteDataSource mealHomeRemoteDataSource;

    public MealHomeRepo() {
        this.mealHomeRemoteDataSource = new MealHomeRemoteDataSource();
    }

    public Single<Meal> getRandomeMeal() {
        return mealHomeRemoteDataSource.getRandomMeal();
    }

    public Single<List<Meal>> getRandomMeals(int count) {
        return mealHomeRemoteDataSource.getRandomMeals(count);
    }

}
