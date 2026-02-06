package com.example.foodie.presentation.search.presenter;

public interface SearchPresenter {
    void getCategories();

    void getAreas();

    void getIngredients();

    void getFilteredMealsByArea(String country);

    void getFilteredMealsByCategory(String category);

    void getFilteredMealsByIngredient(String ingredient);

    void getMealById(String id);

}
