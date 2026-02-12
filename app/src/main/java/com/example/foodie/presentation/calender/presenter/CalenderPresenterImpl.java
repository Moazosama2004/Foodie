package com.example.foodie.presentation.calender.presenter;

import android.content.Context;

import com.example.foodie.data.calender.CalenderMealsRepo;
import com.example.foodie.data.calender.model.CalendarMeal;
import com.example.foodie.data.home.model.response.Meal;
import com.example.foodie.presentation.calender.view.CalenderView;
import com.example.foodie.utils.sharedprefs.SharedPrefsManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class CalenderPresenterImpl implements CalenderPresenter {

    private final CalenderView view;
    private final CalenderMealsRepo repo;
    private final SharedPrefsManager prefsManager;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public CalenderPresenterImpl(Context context, CalenderView view) {
        this.view = view;
        this.repo = new CalenderMealsRepo(context);
        this.prefsManager = SharedPrefsManager.getInstance(context);
    }

    @Override
    public void checkLoginAndLoad() {
        disposables.add(
                prefsManager.isLoggedIn()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(isLoggedIn -> {
                            if (isLoggedIn) {
                                loadTodayMeals();
                            } else {
                                view.showEmptyDay();
                                view.showGuestAlert();
                            }
                        }, throwable -> view.showError(throwable.getMessage()))
        );
    }

    @Override
    public void loadTodayMeals() {
        String today = getTodayDate();
        view.showDate(today);
        loadMealsByDate(today);
    }

    @Override
    public void loadMealsByDate(String date) {

        view.showProgress();

        disposables.add(
                repo.syncPlannedMeals()
                        .flatMap(meals -> repo.getMealsByDate(date))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(meals -> {
                            view.hideProgress();

                            if (meals == null || meals.isEmpty()) {
                                view.showEmptyDay();
                            } else {
                                view.showMeals(meals);
                            }
                        }, throwable -> {
                            view.hideProgress();
                            view.showError(throwable.getMessage());
                        })
        );
    }

    @Override
    public void onMealSelected(String mealId) {
        disposables.add(
                repo.getAllMealsLocal()
                        .subscribeOn(Schedulers.io())
                        .map(meals -> {
                            for (CalendarMeal meal : meals) {
                                if (meal.getMealId().equals(mealId)) {
                                    return mapToMeal(meal);
                                }
                            }
                            throw new RuntimeException("Meal not found");
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(view::goToMealDetails,
                                throwable -> view.showError(throwable.getMessage()))
        );
    }

    @Override
    public void deleteMeal(CalendarMeal meal, String date) {

        disposables.add(
                repo.deleteMealRemote(meal)
                        .andThen(repo.getMealsByDate(date))
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(meals -> {
                            if (meals == null || meals.isEmpty()) {
                                view.showEmptyDay();
                            } else {
                                view.showMeals(meals);
                            }
                        }, throwable -> view.showError(throwable.getMessage()))
        );
    }

    private String getTodayDate() {
        return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .format(new Date());
    }

    private Meal mapToMeal(CalendarMeal meal) {
        Meal m = new Meal();
        m.setIdMeal(meal.getMealId());
        m.setStrMeal(meal.getMealName());
        m.setStrMealThumb(meal.getMealImage());
        return m;
    }

    @Override
    public void clear() {
        disposables.clear();
    }
}
