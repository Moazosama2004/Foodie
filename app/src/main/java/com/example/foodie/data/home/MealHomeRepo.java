package com.example.foodie.data.home;

import com.example.foodie.data.home.api.MealHomeNetworkResponse;
import com.example.foodie.data.home.datasource.remote.MealHomeRemoteDataSource;

public class MealHomeRepo {
    private MealHomeRemoteDataSource mealHomeRemoteDataSource;

    public MealHomeRepo() {
        this.mealHomeRemoteDataSource = new MealHomeRemoteDataSource();
    }

    public void getRandomeMeal(MealHomeNetworkResponse callback){
        mealHomeRemoteDataSource.getRandomMeal(callback);
    }

}
