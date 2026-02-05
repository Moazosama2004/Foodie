package com.example.foodie.data.search;

import android.util.Log;

import com.example.foodie.data.search.api.MealsSearchNetworkResponse;
import com.example.foodie.data.search.datasource.remote.MealsSearchRemoteDataSource;
import com.example.foodie.data.search.model.Area;
import com.example.foodie.data.search.model.Category;
import com.example.foodie.data.search.model.Ingredient;

public class MealSearchRepo {
    private final MealsSearchRemoteDataSource mealsSearchRemoteDataSource;


    public MealSearchRepo() {
        this.mealsSearchRemoteDataSource = new MealsSearchRemoteDataSource();
    }

    public void getCategories(MealsSearchNetworkResponse<Category> callback) {
        Log.d("Categories - MealSearchRepo  ", "getCategories");
        mealsSearchRemoteDataSource.getCategories(callback);
    }

    public void getAreas(MealsSearchNetworkResponse<Area> callback) {
        mealsSearchRemoteDataSource.getAreas(callback);
    }

    public void getIngredients(MealsSearchNetworkResponse<Ingredient> callback) {
        mealsSearchRemoteDataSource.getIngredients(callback);
    }
}
