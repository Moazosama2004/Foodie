package com.example.foodie.presentation.calender.presenter;

import com.example.foodie.data.calender.model.CalendarMeal;

import java.util.List;

public interface CalenderPresenter {
    void insertMeal(CalendarMeal meal);

    void getMealsByDate(String date);


    void deleteMealsByDate(String date);
    void getMealsByMealId(String mealId);
}
