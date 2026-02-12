package com.example.foodie.utils.services;

import com.example.foodie.data.calender.model.CalendarMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface PlannedMealStorage {

    Completable saveMeal(CalendarMeal meal);

    Completable deleteMeal(String mealId);

    Single<List<CalendarMeal>> getAllMeals();
}
