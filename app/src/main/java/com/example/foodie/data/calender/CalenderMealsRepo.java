package com.example.foodie.data.calender;

import android.content.Context;

import com.example.foodie.data.calender.datasource.local.CalenderMealsLocalDataSource;
import com.example.foodie.data.calender.datasource.remote.CalenderMealsRemoteDataSource;
import com.example.foodie.data.calender.model.CalendarMeal;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class CalenderMealsRepo {

    private final CalenderMealsLocalDataSource localDataSource;
    private final CalenderMealsRemoteDataSource remoteDataSource;

    public CalenderMealsRepo(Context context) {
        this.localDataSource = new CalenderMealsLocalDataSource(context);
        this.remoteDataSource = new CalenderMealsRemoteDataSource();
    }


    public Completable insertMealLocal(CalendarMeal meal) {
        return localDataSource.insertMeal(meal);
    }

    public Completable deleteMealLocal(String date) {
        return localDataSource.deleteMealsByDate(date);
    }

    public Single<List<CalendarMeal>> getMealsByDate(String date) {
        return localDataSource.getMealsByDate(date);
    }

    public Single<List<CalendarMeal>> getAllMealsLocal() {
        return localDataSource.getAllMeals();
    }

    // Remote First

    public Completable saveMealRemote(CalendarMeal meal) {
        return remoteDataSource.saveMeal(meal)
                .andThen(insertMealLocal(meal));
    }

    public Completable deleteMealRemote(CalendarMeal meal) {
        String docId = meal.getMealId() + "_" + meal.getDate();

        return remoteDataSource.deleteMeal(docId)
                .andThen(deleteMealLocal(meal.getDate()));
    }

    public Single<List<CalendarMeal>> fetchAllMealsRemote() {
        return remoteDataSource.getAllMeals()
                .doOnSuccess(meals -> {
                    for (CalendarMeal meal : meals) {
                        localDataSource.insertMeal(meal).subscribe();
                    }
                });
    }

    public Single<List<CalendarMeal>> syncPlannedMeals() {

        return remoteDataSource.getAllMeals()
                .flatMap(remoteMeals -> {

                    List<Completable> insertOperations = new ArrayList<>();

                    for (CalendarMeal meal : remoteMeals) {
                        insertOperations.add(localDataSource.insertMeal(meal));
                    }

                    return localDataSource.clearAllMeals()
                            .andThen(Completable.merge(insertOperations))
                            .andThen(localDataSource.getAllMeals());
                });
    }

}
