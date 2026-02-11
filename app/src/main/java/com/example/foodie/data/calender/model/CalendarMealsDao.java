package com.example.foodie.data.calender.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface CalendarMealsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertMeal(CalendarMeal meal);

    @Query("SELECT * FROM meal_calendar WHERE date = :date")
    Single<List<CalendarMeal>> getMealsByDate(String date);

    @Query("DELETE FROM meal_calendar WHERE date = :date")
    Completable deleteMealsByDate(String date);

    @Query("SELECT * FROM meal_calendar")
    List<CalendarMeal> getAllMeals();

    @Query("DELETE FROM meal_calendar")
    Completable clearAllMeals();

}
