package com.example.foodie.data.home.model.response;


import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MealsBaseResponse {
    @SerializedName("meals")
    private List<Meal> meals;

    public MealsBaseResponse(List<Meal> meals) {
        this.meals = meals;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }

}