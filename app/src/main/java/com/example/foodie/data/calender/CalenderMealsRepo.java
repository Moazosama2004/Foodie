package com.example.foodie.data.calender;

import android.content.Context;

import com.example.foodie.data.calender.datasource.CalenderMealsLocalDataSource;
import com.example.foodie.data.calender.model.CalendarMeal;

import java.util.List;

public class CalenderMealsRepo {
    private CalenderMealsLocalDataSource localDataSource;


    public CalenderMealsRepo(Context context) {
        this.localDataSource = new CalenderMealsLocalDataSource(context);
    };


    public void insertMeal(CalendarMeal meal) {
        localDataSource.insertMeal(meal);
    }

    public List<CalendarMeal> getMealsByDate(String date) {
        return localDataSource.getMealsByDate(date);
    }


    public void deleteMealsByDate(String date) {
        localDataSource.deleteMealsByDate(date);
    }
}
