package com.example.foodie.presentation.calender.presenter;

import android.content.Context;

import com.example.foodie.data.calender.CalenderMealsRepo;
import com.example.foodie.data.calender.model.CalendarMeal;
import com.example.foodie.presentation.calender.view.CalenderView;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.CompletableEmitter;
import io.reactivex.rxjava3.core.CompletableOnSubscribe;
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
//        return calenderMealsRepo.insertMeal(meal)
//                .subscribeOn(Schedulers.io())
//                .doOnComplete(() -> view.showMeals(List.of(meal)))
//                .doOnError((t)->view.showError(t.getMessage()));
        return Completable.create(new CompletableOnSubscribe() {
            @Override
            public void subscribe(@NonNull CompletableEmitter emitter) throws Throwable {

            }
        });
    }

    //
    @Override
    public Single<List<CalendarMeal>> getMealsByDate(String date) {
        return calenderMealsRepo.getMealsByDate(date)
                .subscribeOn(Schedulers.io())
                .doOnError(error -> view.showError(error.getMessage()));
    }

    @Override
    public Completable deleteMealsByDate(CalendarMeal meal) {
        return calenderMealsRepo.deleteMealRemote(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(view::showEmptyDay)
                .doOnError(error -> view.showError(error.getMessage()));
    }

    @Override
    public Single<CalendarMeal> getMealsByMealId(String mealId) {

        return calenderMealsRepo.getAllMealsLocal()
                .subscribeOn(Schedulers.io())
                .map(meals -> {
                    for (CalendarMeal meal : meals) {
                        if (meal.getMealId().equals(mealId)) {
                            return meal;
                        }
                    }
                    throw new RuntimeException("Meal not found");
                })
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(error -> view.showError(error.getMessage()));
    }


    public Single<List<CalendarMeal>> syncMeals() {
        return calenderMealsRepo.syncPlannedMeals()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
