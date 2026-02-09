package com.example.foodie.presentation.details.presenter;

import android.content.Context;
import android.util.Log;

import com.example.foodie.data.calender.CalenderMealsRepo;
import com.example.foodie.data.calender.model.CalendarMeal;
import com.example.foodie.data.core.FavMealsRepo;
import com.example.foodie.data.core.model.User;
import com.example.foodie.data.home.model.response.Meal;
import com.example.foodie.presentation.details.view.DetailsView;

import java.util.List;
import java.util.concurrent.Executors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class DetailsPresenterImpl implements DetailsPresenter {
    private final FavMealsRepo favMealsRepo;
    private final CalenderMealsRepo calenderMealsRepo;
    private final DetailsView view;

    public DetailsPresenterImpl(Context context, DetailsView view) {
        this.view = view;
        favMealsRepo = new FavMealsRepo(context);
        calenderMealsRepo = new CalenderMealsRepo(context);
    }

    @Override
    public void saveMealLocal(Meal meal) {
        favMealsRepo.saveMealLocal(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> Log.d("DetailsPresenter", "Meal saved locally"),
                        throwable -> Log.e("DetailsPresenter", "Failed local save", throwable)
                );
    }

    @Override
    public Completable saveMealRemote(Meal meal) {
        return favMealsRepo.saveMealRemote(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(() -> {
                    Log.d("DetailsPresenter", "Meal saved remotely");
                    view.onSuccess();
                })
                .doOnError(view::showError);
    }


    @Override
    public Completable addToCalender(Meal meal, String date) {
        CalendarMeal calendarMeal = new CalendarMeal();
        calendarMeal.setDate(date);
        calendarMeal.setMealId(meal.getIdMeal());
        calendarMeal.setMealName(meal.getStrMeal());
        calendarMeal.setMealImage(meal.getStrMealThumb());

        return Completable.fromAction(() -> calenderMealsRepo.insertMeal(calendarMeal))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(() -> {
                    Log.d("DetailsPresenter", "Meal added to calendar");
                    view.onSuccess();
                })
                .doOnError(throwable -> {
                    Log.e("DetailsPresenter", "Failed to add meal to calendar", throwable);
                    view.showError(throwable);
                });
    }
}
