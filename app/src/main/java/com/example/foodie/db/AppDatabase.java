package com.example.foodie.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.foodie.data.core.model.FavMeal;
import com.example.foodie.data.core.model.FavMealsDao;

@Database(entities = {FavMeal.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    "meals_db"
            ).build();
        }
        return instance;
    }

    public abstract FavMealsDao favMealsDao();
}