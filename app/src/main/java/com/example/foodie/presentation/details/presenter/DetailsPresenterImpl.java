package com.example.foodie.presentation.details.presenter;

import android.content.Context;
import android.util.Log;

import com.example.foodie.data.calender.CalenderMealsRepo;
import com.example.foodie.data.calender.model.CalendarMeal;
import com.example.foodie.data.core.FavMealsRepo;
import com.example.foodie.data.home.model.response.Meal;
import com.example.foodie.presentation.details.view.DetailsView;
import com.example.foodie.utils.services.StorageCallback;

import java.util.concurrent.Executors;

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
        Executors.newSingleThreadExecutor().execute(() ->
                favMealsRepo.saveMealLocal(meal)
        );
    }



    @Override
    public void saveMealRemote(Meal meal) {
        favMealsRepo.saveMealRemote(meal, new StorageCallback() {
            @Override
            public void onSuccess() {
                view.onSuccess();
            }

            @Override
            public void onError(String message) {
                view.showError(message);
            }
        });
    }


    @Override
    public void addToCalender(Meal meal, String date) {
        Log.d("DetailsPresenterImpl", "Adding to Calender: " + meal.getStrMeal());

        CalendarMeal calendarMeal = new CalendarMeal();
        calendarMeal.setDate(date);
        calendarMeal.setMealId(meal.getIdMeal());
        calendarMeal.setMealName(meal.getStrMeal());
        calendarMeal.setMealImage(meal.getStrMealThumb());

        Executors.newSingleThreadExecutor().execute(
                () -> {
                    Log.d("DetailsPresenterImpl", "before Adding to Calender: " + meal.getStrMeal());
                    calenderMealsRepo.insertMeal(calendarMeal);
                    Log.d("DetailsPresenterImpl", "after Adding to Calender: " + meal.getStrMeal());

                }
        );
        Log.d("DetailsPresenterImpl", " finish Adding to Calender: " + meal.getStrMeal());

    }


}
