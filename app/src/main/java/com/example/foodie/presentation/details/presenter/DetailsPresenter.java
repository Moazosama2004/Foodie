package com.example.foodie.presentation.details.presenter;

import com.example.foodie.data.core.model.FavMeal;
import com.example.foodie.data.home.model.response.Meal;

public interface DetailsPresenter {
    void addToFav(Meal meal);
}
