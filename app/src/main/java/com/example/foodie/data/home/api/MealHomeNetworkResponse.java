package com.example.foodie.data.home.api;

import com.example.foodie.data.home.model.response.Meal;

import java.util.List;

public interface MealHomeNetworkResponse {
    void onSuccessOneMeal(Meal meal);

    void onSuccessMeals(List<Meal> meals);

    void onFailure(String message);

    void noInternet(String message);

}

