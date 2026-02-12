package com.example.foodie.data.search.datasource.remote;

import com.example.foodie.data.home.model.response.Meal;
import com.example.foodie.data.search.api.MealSearchApi;
import com.example.foodie.data.search.api.MealSearchService;
import com.example.foodie.data.search.model.Area;
import com.example.foodie.data.search.model.Category;
import com.example.foodie.data.search.model.FilteredMeal;
import com.example.foodie.data.search.model.Ingredient;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealsSearchRemoteDataSource {

    private final MealSearchService mealSearchService;

    public MealsSearchRemoteDataSource() {
        this.mealSearchService = MealSearchApi.getService();
    }

    public Single<List<Category>> getCategories() {
        return mealSearchService.getCategoriesList()
                .map(response -> {
                    if (response.getCategories() != null) {
                        return response.getCategories();
                    } else {
                        throw new Exception("No categories found");
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<Area>> getAreas() {
        return mealSearchService.getAreasList()
                .map(response -> {
                    if (response.getAreas() != null) {
                        return response.getAreas();
                    } else {
                        throw new Exception("No areas found");
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<Ingredient>> getIngredients() {
        return mealSearchService.getIngredientsList()
                .map(response -> {
                    if (response.getIngredients() != null) {
                        return response.getIngredients();
                    } else {
                        throw new Exception("No ingredients found");
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<FilteredMeal>> getFilteredMealsByArea(String country) {
        return mealSearchService.getFilteredMealsByArea(country)
                .map(response -> {
                    if (response.getFilteredMeals() != null) {
                        return response.getFilteredMeals();
                    } else {
                        throw new Exception("No meals found for area: " + country);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<FilteredMeal>> getFilteredMealsByCategory(String category) {
        return mealSearchService.getFilteredMealsByCategory(category)
                .map(response -> {
                    if (response.getFilteredMeals() != null) {
                        return response.getFilteredMeals();
                    } else {
                        throw new Exception("No meals found for category: " + category);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<List<FilteredMeal>> getFilteredMealsByIngredient(String ingredient) {
        return mealSearchService.getFilteredMealsByIngredient(ingredient)
                .map(response -> {
                    if (response.getFilteredMeals() != null) {
                        return response.getFilteredMeals();
                    } else {
                        throw new Exception("No meals found for ingredient: " + ingredient);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<Meal> getMealById(String id) {
        return mealSearchService.getMealById(id)
                .map(response -> {
                    if (response.getMeals() != null && !response.getMeals().isEmpty()) {
                        return response.getMeals().get(0);
                    } else {
                        throw new Exception("Meal not found with id: " + id);
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
