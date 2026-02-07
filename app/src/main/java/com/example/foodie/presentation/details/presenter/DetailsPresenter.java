package com.example.foodie.presentation.details.presenter;

import com.example.foodie.data.home.model.response.Meal;

public interface DetailsPresenter {
    void saveMealLocal(Meal meal);

    void saveMealRemote(Meal meal);

    void addToCalender(Meal meal, String date);
}
