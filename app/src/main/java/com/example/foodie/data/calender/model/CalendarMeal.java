package com.example.foodie.data.calender.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "meal_calendar")
public class CalendarMeal {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String date;   // yyyy-MM-dd

    private String mealId;
    private String mealName;
    private String mealImage;

    public CalendarMeal() {
    }

    public CalendarMeal(@NonNull String date, String mealId, String mealName, String mealImage) {
        this.date = date;
        this.mealId = mealId;
        this.mealName = mealName;
        this.mealImage = mealImage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getDate() {
        return date;
    }

    public void setDate(@NonNull String date) {
        this.date = date;
    }

    public String getMealId() {
        return mealId;
    }

    public void setMealId(String mealId) {
        this.mealId = mealId;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public String getMealImage() {
        return mealImage;
    }

    public void setMealImage(String mealImage) {
        this.mealImage = mealImage;
    }
}
