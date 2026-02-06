package com.example.foodie.data.calender.datasource;

import android.content.Context;

import com.example.foodie.data.calender.model.CalendarMeal;
import com.example.foodie.data.calender.model.CalendarMealsDao;
import com.example.foodie.db.AppDatabase;

import java.util.List;

public class CalenderMealsLocalDataSource {
    private CalendarMealsDao calendarMealsDao;

    public CalenderMealsLocalDataSource(Context context) {
        this.calendarMealsDao = AppDatabase.getInstance(context).calendarMealsDao();
    }

    public void insertMeal(CalendarMeal meal) {
        calendarMealsDao.insertMeal(meal);
    }

    public List<CalendarMeal> getMealsByDate(String date) {
        return calendarMealsDao.getMealsByDate(date);
    }

    public void deleteMealsByDate(String date) {
        calendarMealsDao.deleteMealsByDate(date);
    }
}
