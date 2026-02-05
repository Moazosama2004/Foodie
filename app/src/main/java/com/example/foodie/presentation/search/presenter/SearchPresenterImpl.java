package com.example.foodie.presentation.search.presenter;

import android.util.Log;

import com.example.foodie.data.search.MealSearchRepo;
import com.example.foodie.data.search.api.MealsSearchNetworkResponse;
import com.example.foodie.data.search.model.Area;
import com.example.foodie.data.search.model.Category;
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
}
