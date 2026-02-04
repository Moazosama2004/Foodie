package com.example.foodie.data.home.api;

import com.example.foodie.network.RetrofitClient;

public class MealHomeApi {
    private static MealHomeService mealHomeService;

    public static MealHomeService getService() {
        if (mealHomeService == null) {
            mealHomeService = RetrofitClient.getInstance().create(MealHomeService.class);
        }
        return mealHomeService;
    }
}
