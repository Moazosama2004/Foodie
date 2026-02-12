package com.example.foodie.utils.services;

import com.example.foodie.data.home.model.response.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface MealStorage {
    Completable saveMeal(Meal meal);

    Completable deleteMeal(String mealId);

    Single<List<Meal>> getAllMeals();
}
