package com.example.foodie.presentation.fav.presenter;

import android.content.Context;

import androidx.lifecycle.LiveData;

import com.example.foodie.data.core.FavMealsRepo;
import com.example.foodie.data.core.model.FavMeal;
import com.example.foodie.data.home.model.response.Meal;
import com.example.foodie.presentation.fav.view.FavView;

import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.Executors;

public class FavPresenterImpl implements FavPresenter{
    private FavView view;
    private FavMealsRepo favMealsRepo;
    public FavPresenterImpl(Context context , FavView view) {
        this.view = view;
        this.favMealsRepo = new FavMealsRepo(context);
    }
    @Override
    public LiveData<List<FavMeal>> getFavMeals() {
        return favMealsRepo.getAllFavMeals();
    }


    @Override
    public void deleteFromFav(Meal meal) {
        FavMeal favMeal = new FavMeal(meal.getIdMeal(), meal.getStrMeal(), meal.getStrMealThumb());
        Executors.newSingleThreadExecutor().execute(() ->
                favMealsRepo.deleteMeal(favMeal)
        );
    }

}
