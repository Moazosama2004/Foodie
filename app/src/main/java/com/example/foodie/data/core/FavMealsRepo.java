package com.example.foodie.data.core;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.foodie.data.core.datasource.local.FavMealsLocalDataSource;
import com.example.foodie.data.core.datasource.remote.FavMealsRemoteDataSource;
import com.example.foodie.data.core.model.User;
import com.example.foodie.data.home.model.response.Meal;
import com.example.foodie.utils.services.StorageCallback;

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




    public LiveData<Boolean> isMealFav(String id) {
        return favMealsLocalDataSource.isMealFav(id);
    }

    public LiveData<List<Meal>> getAllFavMeals() {
        LiveData<List<Meal>> localMeals =
                favMealsLocalDataSource.getAllFavMeals();

        fetchFavMealsFromRemote();

        return localMeals;
    }

    private void fetchFavMealsFromRemote() {
      favMealsRemoteDataSource.getAllFavMeals(new StorageCallback() {
          @Override
          public void onSuccess() {

          }

          @Override
          public void onError(String message) {

          }

          @Override
          public void onSuccessWithResult(List<Meal> meals) {
              favMealsLocalDataSource.insertMeals(meals);
          }

          @Override
          public void onSuccessWithUserData(User user) {

          }
      });
    }

    public void saveMealRemote(Meal meal , StorageCallback callback) {
        favMealsRemoteDataSource.saveMeal(meal , callback);
    }

    public void deleteMealRemote(String id , StorageCallback callback) {
        favMealsRemoteDataSource.deleteMeal(id , callback);
    }
}
