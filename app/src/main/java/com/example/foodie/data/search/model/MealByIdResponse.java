package com.example.foodie.data.search.model;

import com.example.foodie.data.home.model.response.Meal;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MealByIdResponse {
    @SerializedName("meals")
    private List<Meal> meals;

    public MealByIdResponse(List<Meal> meals) {
        this.meals = meals;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }


}
