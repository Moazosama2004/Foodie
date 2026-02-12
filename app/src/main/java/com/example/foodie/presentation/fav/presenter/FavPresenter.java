package com.example.foodie.presentation.fav.presenter;

import com.example.foodie.data.home.model.response.Meal;

public interface FavPresenter {

    void checkLoginAndLoad();

    void loadFavMeals();

    void deleteMeal(Meal meal);

    void clear();
}
