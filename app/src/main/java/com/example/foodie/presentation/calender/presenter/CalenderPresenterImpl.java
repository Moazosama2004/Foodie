package com.example.foodie.presentation.calender.presenter;

import android.content.Context;
import android.util.Log;

import androidx.fragment.app.Fragment;

import com.example.foodie.data.calender.CalenderMealsRepo;
import com.example.foodie.data.calender.model.CalendarMeal;
import com.example.foodie.data.home.model.response.Meal;
import com.example.foodie.data.search.MealSearchRepo;
import com.example.foodie.data.search.api.MealsSearchNetworkResponse;
import com.example.foodie.presentation.calender.view.CalenderView;

import java.util.List;

public class CalenderPresenterImpl implements CalenderPresenter {
    private final CalenderView view;
    private final CalenderMealsRepo calenderMealsRepo;
    private final MealSearchRepo mealSearchRepo;

    public CalenderPresenterImpl(Context context, CalenderView view) {
        this.calenderMealsRepo = new CalenderMealsRepo(context);
        this.view = view;
        this.mealSearchRepo = new MealSearchRepo();
    }

    @Override
    public void insertMeal(CalendarMeal meal) {
    }

    @Override
    public void getMealsByDate(String date) {
        new Thread(() -> {
            List<CalendarMeal> meals = calenderMealsRepo.getMealsByDate(date);

            // Switch to main thread to update UI
            if (view instanceof Fragment) {
                ((Fragment) view).requireActivity().runOnUiThread(() -> {
                    if (meals == null || meals.isEmpty()) {
                        view.showEmptyDay();
                        Log.d("CalenderPresenterImpl", "showEmptyDay");
                    } else {
                        Log.d("CalenderPresenterImpl", "showMeals");
                        view.showMeals(meals);
                    }
                });
            } else {
                // fallback if view is not a fragment
                android.os.Handler mainHandler = new android.os.Handler(android.os.Looper.getMainLooper());
                mainHandler.post(() -> {
                    if (meals == null || meals.isEmpty()) {
                        view.showEmptyDay();
                    } else {
                        view.showMeals(meals);
                    }
                });
            }
        }).start();
    }


    @Override
    public void deleteMealsByDate(String date) {

    }

    @Override
    public void getMealsByMealId(String mealId) {
        view.showProgress();
        mealSearchRepo.getMealById(mealId, new MealsSearchNetworkResponse<Meal>() {
            @Override
            public void onSuccess(List<Meal> data) {
                view.showProgress();
                view.goToMealDetails(data.get(0));
            }

            @Override
            public void onFailure(String message) {
                view.hideProgress();
                view.showError(message);
            }

            @Override
            public void noInternet(String message) {
                view.hideProgress();
                view.showError(message);
            }
        });
    }
}
