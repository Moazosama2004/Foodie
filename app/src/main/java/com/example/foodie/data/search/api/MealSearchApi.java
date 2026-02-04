package com.example.foodie.data.search.api;

import com.example.foodie.network.RetrofitClient;

public class MealSearchApi {
    private static MealSearchService mealSearchService;

    public static MealSearchService getService() {
        if (mealSearchService == null) {
            mealSearchService = RetrofitClient.getInstance().create(MealSearchService.class);
        }
        return mealSearchService;
    }
}

