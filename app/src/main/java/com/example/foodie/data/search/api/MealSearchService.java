package com.example.foodie.data.search.api;

import com.example.foodie.data.search.model.AreaResponse;
import com.example.foodie.data.search.model.CategoryResponse;
import com.example.foodie.data.search.model.FilteredResponse;
import com.example.foodie.data.search.model.IngredientsResponse;
import com.example.foodie.data.search.model.MealByIdResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MealSearchService {
    @GET("categories.php")
    Call<CategoryResponse> getCategoriesList();

    @GET("list.php?a=list")
    Call<AreaResponse> getAreasList();

    @GET("list.php?i=list")
    Call<IngredientsResponse> getIngredientsList();

    @GET("filter.php?")
    Call<FilteredResponse> getFilteredMealsByArea(@Query("a") String country);

//    @GET("filter.php?c={category}")
    @GET("filter.php?")
    Call<FilteredResponse> getFilteredMealsByCategory(@Query("c") String category);

    @GET("filter.php?")
    Call<FilteredResponse> getFilteredMealsByIngredient(@Query("i") String ingredient);

    @GET("lookup.php?")
    Call<MealByIdResponse> getMealById(@Query("i") String id);

}
