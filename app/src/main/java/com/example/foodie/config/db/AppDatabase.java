package com.example.foodie.config.db;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.foodie.data.calender.model.CalendarMeal;
import com.example.foodie.data.calender.model.CalendarMealsDao;
import com.example.foodie.data.core.model.FavMealsDao;
import com.example.foodie.data.home.model.response.Meal;

@Database(
        entities = {
                Meal.class,
                CalendarMeal.class
        },
        version = 3
)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                            context.getApplicationContext(),
                            AppDatabase.class,
                            "meals_db"
                    )
                    .fallbackToDestructiveMigration(true)
                    .build();
        }
        return instance;
    }

    public abstract FavMealsDao favMealsDao();

    public abstract CalendarMealsDao calendarMealsDao();
}