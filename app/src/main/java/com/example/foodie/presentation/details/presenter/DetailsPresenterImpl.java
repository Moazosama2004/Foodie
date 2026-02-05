package com.example.foodie.presentation.details.presenter;

import android.content.Context;

import com.example.foodie.data.core.FavMealsRepo;
import com.example.foodie.data.core.model.FavMeal;
import com.example.foodie.data.home.model.response.Meal;
import com.example.foodie.presentation.details.view.DetailsView;

import java.util.concurrent.Executors;

public class DetailsPresenterImpl implements DetailsPresenter {
    private final FavMealsRepo favMealsRepo;
    private final DetailsView view;

    public DetailsPresenterImpl(Context context, DetailsView view) {
        this.view = view;
        favMealsRepo = new FavMealsRepo(context);
    }

    @Override
    public void addToFav(Meal meal) {
        Executors.newSingleThreadExecutor().execute(() ->
                favMealsRepo.insertMeal(new FavMeal(meal.getIdMeal(), meal.getStrMeal(), meal.getStrMealThumb()))
        );
    }
}
