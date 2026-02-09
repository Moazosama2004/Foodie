package com.example.foodie.presentation.details.presenter;

import com.example.foodie.data.home.model.response.Meal;

import io.reactivex.rxjava3.core.Completable;

public interface DetailsPresenter {
    void saveMealLocal(Meal meal);

    Completable saveMealRemote(Meal meal);

    Completable addToCalender(Meal meal, String date);
}
