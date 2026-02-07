package com.example.foodie.presentation.fav.view;

import androidx.lifecycle.LifecycleOwner;

import com.example.foodie.data.home.model.response.Meal;

import java.util.List;

public interface FavView {
    void showProgress();

    void hideProgress();

    void showError(String message);

    void showFavMeals(List<Meal> favMeals);

    LifecycleOwner getLifecycleOwner();

    void goToDetails(Meal meal);
    void onSuccess();

}
