package com.example.foodie.data.calender.datasource.remote;

import com.example.foodie.data.calender.model.CalendarMeal;
import com.example.foodie.utils.firebase.storage.FirestorePlannedMealStorage;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class CalenderMealsRemoteDataSource {
    private final FirestorePlannedMealStorage plannedMealStorage;

    public CalenderMealsRemoteDataSource() {
        this.plannedMealStorage = new FirestorePlannedMealStorage();
    }

    public Completable saveMeal(CalendarMeal meal) {
        return plannedMealStorage.saveMeal(meal);
    }

    public Completable deleteMeal(String mealId) {
        return plannedMealStorage.deleteMeal(mealId);
    }

    public Single<List<CalendarMeal>> getAllMeals() {
        return plannedMealStorage.getAllMeals();
    }
}
