package com.example.foodie.data.core;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.foodie.data.core.datasource.local.FavMealsLocalDataSource;
import com.example.foodie.data.core.model.FavMeal;

import java.util.List;

public class FavMealsRepo {
    private FavMealsLocalDataSource favMealsLocalDataSource;

    public FavMealsRepo(Context context) {
        this.favMealsLocalDataSource = new FavMealsLocalDataSource(context);
    }

    public void insertMeal(FavMeal meal) {
        favMealsLocalDataSource.insertMeal(meal);
    }

    public void deleteMeal(FavMeal meal) {
        favMealsLocalDataSource.deleteMeal(meal);
    }


    public LiveData<Boolean> isMealFav(String id) {
        return favMealsLocalDataSource.isMealFav(id);
    }

    public LiveData<List<FavMeal>> getAllFavMeals(){
        return favMealsLocalDataSource.getAllFavMeals();
    }
}
