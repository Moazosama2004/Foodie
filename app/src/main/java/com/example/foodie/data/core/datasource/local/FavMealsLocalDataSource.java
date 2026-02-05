package com.example.foodie.data.core.datasource.local;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.foodie.data.core.model.FavMeal;
import com.example.foodie.data.core.model.FavMealsDao;
import com.example.foodie.db.AppDatabase;

import java.util.List;

public class FavMealsLocalDataSource {
    private FavMealsDao favMealsDao;

    public FavMealsLocalDataSource(Context context) {
        this.favMealsDao = AppDatabase.getInstance(context).favMealsDao();
    }

    public void insertMeal(FavMeal meal){
        favMealsDao.insertMeal(meal);
    }

    public void deleteMeal(FavMeal meal){
        favMealsDao.deleteMeal(meal);
    }

    public LiveData<Boolean> isMealFav(String id){
        return favMealsDao.isMealFav(id);
    }

    public LiveData<List<FavMeal>> getAllFavMeals(){
        return favMealsDao.getAllFavMeals();
    }

}
