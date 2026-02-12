package com.example.foodie.data.home.datasource.remote;

import com.example.foodie.data.home.api.MealHomeApi;
import com.example.foodie.data.home.api.MealHomeService;
import com.example.foodie.data.home.model.response.Meal;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class MealHomeRemoteDataSource {
    private final MealHomeService mealHomeService;

    public MealHomeRemoteDataSource() {
        this.mealHomeService = MealHomeApi.getService();
    }

    public Single<Meal> getRandomMeal() {
        return mealHomeService.getRandomMeal()
                .map(response -> response.getMeals().get(0));
    }


    public Single<List<Meal>> getRandomMeals(int count) {

        List<Single<Meal>> requests = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            requests.add(
                    mealHomeService.getRandomMeal()
                            .map(response -> response.getMeals().get(0))
            );
        }

        return Single.zip(
                requests,
                results -> {
                    List<Meal> meals = new ArrayList<>();
                    for (Object result : results) {
                        meals.add((Meal) result);
                    }
                    return meals;
                }
        );
    }

}
