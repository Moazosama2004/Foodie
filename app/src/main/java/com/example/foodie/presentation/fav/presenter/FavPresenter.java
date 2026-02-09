package com.example.foodie.presentation.fav.presenter;

import com.example.foodie.data.home.model.response.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface FavPresenter {

    Single<List<Meal>> getFavMeals();

    Completable deleteFromFavLocal(Meal meal);

    Completable deleteFromFavRemote(Meal meal);
}
