package com.example.foodie.data.home.datasource.remote;

import com.example.foodie.data.home.api.MealHomeApi;
import com.example.foodie.data.home.api.MealHomeNetworkResponse;
import com.example.foodie.data.home.api.MealHomeService;
import com.example.foodie.data.home.model.response.Meal;
import com.example.foodie.data.home.model.response.MealsBaseResponse;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealHomeRemoteDataSource {
    private MealHomeService mealHomeService;

    public MealHomeRemoteDataSource() {
        this.mealHomeService = MealHomeApi.getService();
    }

    public void getRandomMeal(MealHomeNetworkResponse callback) {
        mealHomeService.getRandomMeal().enqueue(new Callback<MealsBaseResponse>() {
            @Override
            public void onResponse(Call<MealsBaseResponse> call, Response<MealsBaseResponse> response) {

                if (response.code() == 200) {
                    MealsBaseResponse mealBaseResponse = response.body();
                    List<Meal>  meals = mealBaseResponse.getMeals();
                    callback.onSuccessOneMeal(meals.get(0));
                } else {
                    callback.onFailure("Error server error");
                }
            }

            @Override
            public void onFailure(Call<MealsBaseResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    callback.noInternet("Network Connection");
                } else {
                    callback.onFailure("Conversion Error! Please try again.");
                }
            }
        });
    }
}
