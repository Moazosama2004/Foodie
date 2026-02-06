package com.example.foodie.presentation.calender.view;

import com.example.foodie.data.calender.model.CalendarMeal;
import com.example.foodie.data.home.model.response.Meal;

import java.util.List;

public interface CalenderView {
    void showProgress();

    void hideProgress();

    void showError(String message);

    void showMeals(List<CalendarMeal> meals);
    void showEmptyDay();
    void goToMealDetails(Meal meal);
}
