package com.example.foodie.data.search.api;

import com.example.foodie.data.home.model.response.MealsBaseResponse;
import com.example.foodie.data.search.model.AreaResponse;
import com.example.foodie.data.search.model.CategoryResponse;
import com.example.foodie.data.search.model.IngredientsResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MealSearchService {
    @GET("categories.php")
    Call<CategoryResponse> getCategoriesList();

    @GET("list.php?a=list")
    Call<AreaResponse> getAreasList();

    @GET("list.php?i=list")
    Call<IngredientsResponse> getIngredientsList();
}
