package com.example.foodie.network;

import com.example.foodie.data.home.api.MealHomeService;

import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String baseUrl = "https://www.themealdb.com/api/json/v1/1/";
    private static Retrofit retrofit;
    private MealHomeService mealHomeService;

    private RetrofitClient() {
    }

    public static Retrofit getInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build();

        }
        return retrofit;
    }

}
