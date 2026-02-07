package com.example.foodie.presentation.fav.presenter;

import androidx.lifecycle.LiveData;

import com.example.foodie.data.home.model.response.Meal;

import java.util.List;

public interface FavPresenter {
    LiveData<List<Meal>> getFavMeals();

    void deleteFromFavLocal(Meal meal);

    void deleteFromFavRemote(String id);
}
