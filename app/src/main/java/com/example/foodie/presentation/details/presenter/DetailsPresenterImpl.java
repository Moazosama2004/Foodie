package com.example.foodie.presentation.details.presenter;

import android.content.Context;
import android.util.Log;

import com.example.foodie.data.calender.CalenderMealsRepo;
import com.example.foodie.data.calender.model.CalendarMeal;
import com.example.foodie.data.core.FavMealsRepo;
import com.example.foodie.data.core.model.FavMeal;
import com.example.foodie.data.home.model.response.Meal;
import com.example.foodie.presentation.details.view.DetailsView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
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
    public void addToFav(Meal meal) {
        Executors.newSingleThreadExecutor().execute(() ->
                favMealsRepo.insertMeal(new FavMeal(meal.getIdMeal(), meal.getStrMeal(), meal.getStrMealThumb()))
        );
    }


    @Override
    public void addToCalender(Meal meal) {
        Log.d("DetailsPresenterImpl", "Adding to Calender: " + meal.getStrMeal());
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .format(new Date());

        CalendarMeal calendarMeal = new CalendarMeal();
        calendarMeal.setDate(today);
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
