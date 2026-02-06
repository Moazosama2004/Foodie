package com.example.foodie.data.search.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FilteredResponse {
    @SerializedName("meals")
    private List<FilteredMeal> filteredMeals;

    public List<FilteredMeal> getFilteredMeals() {
        return filteredMeals;
    }

    public void setFilteredMeals(List<FilteredMeal> ingredients) {
        this.filteredMeals = filteredMeals;
    }

}
