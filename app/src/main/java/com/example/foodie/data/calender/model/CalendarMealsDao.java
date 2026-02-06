package com.example.foodie.data.calender.model;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CalendarMealsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMeal(CalendarMeal meal);

    @Query("SELECT * FROM meal_calendar WHERE date = :date")
    List<CalendarMeal> getMealsByDate(String date);

    @Query("DELETE FROM meal_calendar WHERE date = :date")
    void deleteMealsByDate(String date);
}