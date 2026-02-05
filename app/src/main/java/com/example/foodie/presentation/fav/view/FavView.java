package com.example.foodie.presentation.fav.view;

import androidx.lifecycle.LifecycleOwner;

import com.example.foodie.data.core.model.FavMeal;

import java.util.List;

public interface FavView {
    void showProgress();

    void hideProgress();

    void showError(String message);

    void showFavMeals(List<FavMeal> favMeals);

    LifecycleOwner getLifecycleOwner();

}
