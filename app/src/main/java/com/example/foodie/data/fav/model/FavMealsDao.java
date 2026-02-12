package com.example.foodie.data.fav.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.foodie.data.home.model.response.Meal;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

@Dao
public interface FavMealsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertMeal(Meal meal);

    @Delete
    Completable deleteMeal(Meal meal);

    @Query("SELECT EXISTS(SELECT 1 FROM fav_meals WHERE idMeal = :id)")
    Single<Boolean> isMealFav(String id);

    @Query("SELECT * FROM fav_meals")
    Single<List<Meal>> getAllFavMeals();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertMeals(List<Meal> meals);
}