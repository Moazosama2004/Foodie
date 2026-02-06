package com.example.foodie.presentation.details.presenter;

import com.example.foodie.data.home.model.response.Meal;

public interface DetailsPresenter {
    void addToFav(Meal meal);
    void addToCalender(Meal meal);
}
