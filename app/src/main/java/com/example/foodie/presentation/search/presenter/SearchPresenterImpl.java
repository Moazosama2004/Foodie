package com.example.foodie.presentation.search.presenter;

import android.util.Log;

import com.example.foodie.data.home.model.response.Meal;
import com.example.foodie.data.search.MealSearchRepo;
import com.example.foodie.data.search.api.MealsSearchNetworkResponse;
import com.example.foodie.data.search.model.Area;
import com.example.foodie.data.search.model.Category;
import com.example.foodie.data.search.model.FilteredMeal;
import com.example.foodie.data.search.model.Ingredient;
import com.example.foodie.presentation.search.view.SearchView;

import java.util.List;

public class SearchPresenterImpl implements SearchPresenter {

    private final MealSearchRepo searchRepo;
    private final SearchView searchView;

    public SearchPresenterImpl(SearchView searchView) {
        this.searchRepo = new MealSearchRepo();
        this.searchView = searchView;
    }

    @Override
    public void getCategories() {

        searchView.showLoading();
        searchRepo.getCategories(new MealsSearchNetworkResponse<Category>() {
            @Override
            public void onSuccess(List<Category> data) {
                Log.d("Categories", data.toString());
                searchView.hideLoading();
                searchView.showData(data);
            }

            @Override
            public void onFailure(String message) {
                Log.d("Categories", message);
                searchView.hideLoading();
            }

            @Override
            public void noInternet(String message) {
                Log.d("Categories", message);
                searchView.hideLoading();
            }
        });
    }

    @Override
    public void getAreas() {
        searchView.showLoading();
        searchRepo.getAreas(new MealsSearchNetworkResponse<Area>() {
            @Override
            public void onSuccess(List<Area> data) {
                searchView.hideLoading();
                searchView.showData(data);
            }

            @Override
            public void onFailure(String message) {

                searchView.hideLoading();
            }

            @Override
            public void noInternet(String message) {

                searchView.hideLoading();
            }
        });
    }

    @Override
    public void getIngredients() {
        searchView.showLoading();
        searchRepo.getIngredients(new MealsSearchNetworkResponse<Ingredient>() {
            @Override
            public void onSuccess(List<Ingredient> data) {
                Log.d("Ingredients", data.toString());
                searchView.hideLoading();
                searchView.showData(data);
            }

            @Override
            public void onFailure(String message) {

                searchView.hideLoading();
            }

            @Override
            public void noInternet(String message) {

                searchView.hideLoading();
            }
        });
    }

    @Override
    public void getFilteredMealsByArea(String country) {
        searchView.showLoading();
        searchRepo.getFilteredMealsByArea(country, new MealsSearchNetworkResponse<FilteredMeal>() {
            @Override
            public void onSuccess(List<FilteredMeal> data) {
                searchView.hideLoading();
                searchView.showData(data);
            }

            @Override
            public void onFailure(String message) {
                searchView.hideLoading();
                searchView.showError(message);
            }

            @Override
            public void noInternet(String message) {
                searchView.hideLoading();
                searchView.showError(message);
            }
        });
    }

    @Override
    public void getFilteredMealsByCategory(String category) {
        searchRepo.getFilteredMealsByCategory(category, new MealsSearchNetworkResponse<FilteredMeal>() {
            @Override
            public void onSuccess(List<FilteredMeal> data) {
                searchView.hideLoading();
                searchView.showData(data);
            }

            @Override
            public void onFailure(String message) {
                searchView.hideLoading();
                searchView.showError(message);
            }

            @Override
            public void noInternet(String message) {
                searchView.hideLoading();
                searchView.showError(message);
            }
        });
    }

    @Override
    public void getFilteredMealsByIngredient(String ingredient) {
        searchView.showLoading();
        searchRepo.getFilteredMealsByCategory(ingredient, new MealsSearchNetworkResponse<FilteredMeal>() {
            @Override
            public void onSuccess(List<FilteredMeal> data) {
                searchView.hideLoading();
                searchView.showData(data);
            }

            @Override
            public void onFailure(String message) {
                searchView.hideLoading();
                searchView.showError(message);
            }

            @Override
            public void noInternet(String message) {
                searchView.hideLoading();
                searchView.showError(message);
            }
        });
    }

    @Override
    public void getMealById(String id) {
        searchView.showLoading();
        searchRepo.getMealById(id, new MealsSearchNetworkResponse<Meal>() {
            @Override
            public void onSuccess(List<Meal> data) {
                searchView.hideLoading();
                searchView.goToMealDetails(data.get(0));
            }

            @Override
            public void onFailure(String message) {
                searchView.hideLoading();
                searchView.showError(message);
            }

            @Override
            public void noInternet(String message) {
                searchView.hideLoading();
                searchView.showError(message);
            }
        });
    }
}
