package com.example.foodie.data.fav;

import android.content.Context;

import com.example.foodie.data.fav.datasource.local.FavMealsLocalDataSource;
import com.example.foodie.data.fav.datasource.remote.FavMealsRemoteDataSource;
import com.example.foodie.data.home.model.response.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class FavMealsRepo {
    private final FavMealsLocalDataSource localDataSource;
    private final FavMealsRemoteDataSource remoteDataSource;

    public FavMealsRepo(Context context) {
        this.localDataSource = new FavMealsLocalDataSource(context);
        this.remoteDataSource = new FavMealsRemoteDataSource();
    }

    public Completable saveMealLocal(Meal meal) {
        return localDataSource.insertMeal(meal);
    }

    public Completable deleteMealLocal(Meal meal) {
        return localDataSource.deleteMeal(meal);
    }

    public Single<Boolean> isMealFav(String id) {
        return localDataSource.isMealFav(id);
    }

    public Single<List<Meal>> getAllFavMealsLocal() {
        return localDataSource.getAllFavMeals();
    }

    public Completable saveMealRemote(Meal meal) {
        return remoteDataSource.saveMeal(meal)
                .andThen(saveMealLocal(meal));
    }

    public Completable deleteMealRemote(Meal meal) {
        return remoteDataSource.deleteMeal(meal.getIdMeal())
                .andThen(deleteMealLocal(meal));
    }

    public Single<List<Meal>> fetchAllFavMealsRemote() {
        return remoteDataSource.getAllFavMeals()
                .doOnSuccess(localDataSource::insertMeals);
    }
}
