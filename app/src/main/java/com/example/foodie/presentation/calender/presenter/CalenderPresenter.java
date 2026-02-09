package com.example.foodie.presentation.calender.presenter;

import com.example.foodie.data.calender.model.CalendarMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface CalenderPresenter {

    Completable insertMeal(CalendarMeal meal);

    Single<List<CalendarMeal>> getMealsByDate(String date);

    Completable deleteMealsByDate(String date);

    Single<CalendarMeal> getMealsByMealId(String mealId);

}
