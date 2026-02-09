package com.example.foodie.data.search.api;

import com.example.foodie.data.search.model.AreaResponse;
import com.example.foodie.data.search.model.CategoryResponse;
import com.example.foodie.data.search.model.FilteredResponse;
import com.example.foodie.data.search.model.IngredientsResponse;
import com.example.foodie.data.search.model.MealByIdResponse;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MealSearchService {

    @GET("categories.php")
    Single<CategoryResponse> getCategoriesList();

    @GET("list.php?a=list")
    Single<AreaResponse> getAreasList();

    @GET("list.php?i=list")
    Single<IngredientsResponse> getIngredientsList();

    @GET("filter.php?")
    Single<FilteredResponse> getFilteredMealsByArea(@Query("a") String country);

    @GET("filter.php?")
    Single<FilteredResponse> getFilteredMealsByCategory(@Query("c") String category);

    @GET("filter.php?")
    Single<FilteredResponse> getFilteredMealsByIngredient(@Query("i") String ingredient);

    @GET("lookup.php?")
    Single<MealByIdResponse> getMealById(@Query("i") String id);
}