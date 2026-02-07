package com.example.foodie.data.core.datasource.local;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.foodie.data.core.model.FavMealsDao;
import com.example.foodie.data.home.model.response.Meal;
import com.example.foodie.db.AppDatabase;

import java.util.List;
import java.util.concurrent.Executors;

public class FavMealsLocalDataSource {
    private final FavMealsDao favMealsDao;

    public FavMealsLocalDataSource(Context context) {
        this.favMealsDao = AppDatabase.getInstance(context).favMealsDao();
    }

    public void insertMeals(List<Meal> meals) {
        Executors.newSingleThreadExecutor().execute(() -> {
            favMealsDao.insertMeals(meals);
        });
    }

    public void insertMeal(Meal meal) {
        favMealsDao.insertMeal(meal);
    }

    public void deleteMeal(Meal meal) {
        favMealsDao.deleteMeal(meal);
    }

    public LiveData<Boolean> isMealFav(String id) {
        return favMealsDao.isMealFav(id);
    }

    public LiveData<List<Meal>> getAllFavMeals() {
        return favMealsDao.getAllFavMeals();
    }

}
