package com.example.foodie.data.calender.datasource.local;

import android.content.Context;

import com.example.foodie.config.db.AppDatabase;
import com.example.foodie.data.calender.model.CalendarMeal;
import com.example.foodie.data.calender.model.CalendarMealsDao;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class CalenderMealsLocalDataSource {
    private final CalendarMealsDao calendarMealsDao;

    public CalenderMealsLocalDataSource(Context context) {
        this.calendarMealsDao = AppDatabase.getInstance(context).calendarMealsDao();
    }

    public Completable insertMeal(CalendarMeal meal) {
        return calendarMealsDao.insertMeal(meal);
    }

    public Single<List<CalendarMeal>> getMealsByDate(String date) {
        return calendarMealsDao.getMealsByDate(date);
    }

    public Completable deleteMealsByDate(String date) {
        return calendarMealsDao.deleteMealsByDate(date);
    }

    public Single<List<CalendarMeal>> getAllMeals() {
        return Single.fromCallable(() -> calendarMealsDao.getAllMeals());
    }

    public Completable clearAllMeals() {
        return calendarMealsDao.clearAllMeals();
    }
}

