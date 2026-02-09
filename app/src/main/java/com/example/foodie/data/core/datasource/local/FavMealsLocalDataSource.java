package com.example.foodie.data.core.datasource.local;

import android.content.Context;

import com.example.foodie.data.core.model.FavMealsDao;
import com.example.foodie.data.home.model.response.Meal;
import com.example.foodie.db.AppDatabase;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class FavMealsLocalDataSource {
    private final FavMealsDao favMealsDao;

    public FavMealsLocalDataSource(Context context) {
        this.favMealsDao = AppDatabase.getInstance(context).favMealsDao();
    }

    public Completable insertMeal(Meal meal) {
        return favMealsDao.insertMeal(meal);
    }

    public Completable insertMeals(List<Meal> meals) {
        return favMealsDao.insertMeals(meals);
    }

    public Completable deleteMeal(Meal meal) {
        return favMealsDao.deleteMeal(meal);
    }

    public Single<Boolean> isMealFav(String id) {
        return favMealsDao.isMealFav(id);
    }

    public Single<List<Meal>> getAllFavMeals() {
        return favMealsDao.getAllFavMeals();
    }
}
