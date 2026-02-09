package com.example.foodie.presentation.calender.presenter;

import android.content.Context;

import com.example.foodie.data.calender.CalenderMealsRepo;
import com.example.foodie.data.calender.model.CalendarMeal;
import com.example.foodie.presentation.calender.view.CalenderView;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CalenderPresenterImpl implements CalenderPresenter {
    private final CalenderView view;
    private final CalenderMealsRepo calenderMealsRepo;

    public CalenderPresenterImpl(Context context, CalenderView view) {
        this.calenderMealsRepo = new CalenderMealsRepo(context);
        this.view = view;
    }

    @Override
    public Completable insertMeal(CalendarMeal meal) {
        return calenderMealsRepo.insertMeal(meal)
                .subscribeOn(Schedulers.io())
                .doOnComplete(() -> view.showMeals(List.of(meal)))
                .doOnError((t)->view.showError(t.getMessage()));
    }

    //
    @Override
    public Single<List<CalendarMeal>> getMealsByDate(String date) {
        return calenderMealsRepo.getMealsByDate(date)
                .subscribeOn(Schedulers.io())

                .doOnError(error -> view.showError(error.getMessage()));
    }

    @Override
    public Completable deleteMealsByDate(String date) {
        return calenderMealsRepo.deleteMealsByDate(date)
                .subscribeOn(Schedulers.io())
                .doOnComplete(() -> view.showEmptyDay())
                .doOnError((t)->view.showError(t.getMessage()));
    }

    @Override
    public Single<CalendarMeal> getMealsByMealId(String mealId) {
        return calenderMealsRepo.getAllMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(meals -> {
                    for (CalendarMeal meal : meals) {
                        if (meal.getMealId().equals(mealId)) return meal;
                    }
                    return null;
                });
    }


}
