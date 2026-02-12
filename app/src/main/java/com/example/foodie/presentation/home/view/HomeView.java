package com.example.foodie.presentation.home.view;

import com.example.foodie.data.home.model.response.Meal;

import java.util.List;

public interface HomeView {
    void showProgress();

    void hideProgress();

    void showError(String message);

    void showPopularMeals(List<Meal> meals);

    void showRandomMeal(Meal meal);

    void showNetworkError(String Message);

    void showUserData(String username);
}
