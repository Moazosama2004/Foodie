package com.example.foodie.data.core;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.foodie.data.core.datasource.local.FavMealsLocalDataSource;
import com.example.foodie.data.core.datasource.remote.FavMealsRemoteDataSource;
import com.example.foodie.data.home.model.response.Meal;

import java.util.List;

public class FavMealsRepo {
    private final FavMealsLocalDataSource favMealsLocalDataSource;
    private final FavMealsRemoteDataSource favMealsRemoteDataSource;


    public FavMealsRepo(Context context) {
        this.favMealsLocalDataSource = new FavMealsLocalDataSource(context);
        this.favMealsRemoteDataSource = new FavMealsRemoteDataSource();
    }

    public void saveMealLocal(Meal meal) {
        favMealsLocalDataSource.insertMeal(meal);
    }

    public void deleteMealLocal(Meal meal) {
        favMealsLocalDataSource.deleteMeal(meal);
    }

    public void deleteMealRemote(String id) {
        favMealsRemoteDataSource.deleteMeal(id);
    }


    public LiveData<Boolean> isMealFav(String id) {
        return favMealsLocalDataSource.isMealFav(id);
    }

    public LiveData<List<Meal>> getAllFavMeals() {
        return favMealsLocalDataSource.getAllFavMeals();
    }

    public void saveMealRemote(Meal meal) {
        favMealsRemoteDataSource.saveMeal(meal);
    }
}
