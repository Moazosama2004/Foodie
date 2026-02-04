package com.example.foodie.data.home.api;

import com.example.foodie.data.home.model.response.MealsBaseResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MealHomeService {
    @GET("random.php")
    Call<MealsBaseResponse> getRandomMeal();

}
