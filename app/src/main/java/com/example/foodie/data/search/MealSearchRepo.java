package com.example.foodie.data.search;

import com.example.foodie.data.home.model.response.Meal;
import com.example.foodie.data.search.datasource.remote.MealsSearchRemoteDataSource;
import com.example.foodie.data.search.model.Area;
import com.example.foodie.data.search.model.Category;
import com.example.foodie.data.search.model.FilteredMeal;
import com.example.foodie.data.search.model.Ingredient;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class MealSearchRepo {

    private final MealsSearchRemoteDataSource remoteDataSource;

    public MealSearchRepo() {
        this.remoteDataSource = new MealsSearchRemoteDataSource();
    }

    public Single<List<Category>> getCategories() {
        return remoteDataSource.getCategories();
    }

    public Single<List<Area>> getAreas() {
        return remoteDataSource.getAreas();
    }

    public Single<List<Ingredient>> getIngredients() {
        return remoteDataSource.getIngredients();
    }

    public Single<List<FilteredMeal>> getFilteredMealsByArea(String country) {
        return remoteDataSource.getFilteredMealsByArea(country);
    }

    public Single<List<FilteredMeal>> getFilteredMealsByCategory(String category) {
        return remoteDataSource.getFilteredMealsByCategory(category);
    }

    public Single<List<FilteredMeal>> getFilteredMealsByIngredient(String ingredient) {
        return remoteDataSource.getFilteredMealsByIngredient(ingredient);
    }

    public Single<Meal> getMealById(String id) {
        return remoteDataSource.getMealById(id);
    }
}
