package com.example.foodie.presentation.calender.presenter;

import com.example.foodie.data.calender.model.CalendarMeal;

public interface CalenderPresenter {

    void checkLoginAndLoad();

    void loadTodayMeals();

    void loadMealsByDate(String date);

    void onMealSelected(String mealId);

    void deleteMeal(CalendarMeal meal, String date);

    void clear();
}
