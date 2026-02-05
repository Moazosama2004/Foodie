package com.example.foodie.data.core.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavMealsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMeal(FavMeal meal);

    @Delete
    void deleteMeal(FavMeal meal);

    @Query("SELECT EXISTS(SELECT 1 FROM fav_meals WHERE idMeal = :id)")
    LiveData<Boolean> isMealFav(String id);

    @Query("SELECT * FROM fav_meals")
    LiveData<List<FavMeal>> getAllFavMeals();
}