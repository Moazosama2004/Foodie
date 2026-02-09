package com.example.foodie.data.calender;

import android.content.Context;

import com.example.foodie.data.calender.datasource.CalenderMealsLocalDataSource;
import com.example.foodie.data.calender.model.CalendarMeal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class CalenderMealsRepo {
    private final CalenderMealsLocalDataSource localDataSource;

    public CalenderMealsRepo(Context context) {
        this.localDataSource = new CalenderMealsLocalDataSource(context);
    }

    public Completable insertMeal(CalendarMeal meal) {
        return localDataSource.insertMeal(meal);
    }

    public Single<List<CalendarMeal>> getMealsByDate(String date) {
        return localDataSource.getMealsByDate(date);
    }

    public Completable deleteMealsByDate(String date) {
        return localDataSource.deleteMealsByDate(date);
    }

    public Single<List<CalendarMeal>> getAllMeals() {
        return localDataSource.getAllMeals();
    }

}
