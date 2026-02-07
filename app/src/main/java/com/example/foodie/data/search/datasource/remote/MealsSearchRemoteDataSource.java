package com.example.foodie.data.search.datasource.remote;

import android.util.Log;

import com.example.foodie.data.home.model.response.Meal;
import com.example.foodie.data.search.api.MealSearchApi;
import com.example.foodie.data.search.api.MealSearchService;
import com.example.foodie.data.search.api.MealsSearchNetworkResponse;
import com.example.foodie.data.search.model.Area;
import com.example.foodie.data.search.model.AreaResponse;
import com.example.foodie.data.search.model.Category;
import com.example.foodie.data.search.model.CategoryResponse;
import com.example.foodie.data.search.model.FilteredMeal;
import com.example.foodie.data.search.model.FilteredResponse;
import com.example.foodie.data.search.model.Ingredient;
import com.example.foodie.data.search.model.IngredientsResponse;
import com.example.foodie.data.search.model.MealByIdResponse;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MealsSearchRemoteDataSource {
    private final MealSearchService mealSearchService;

    public MealsSearchRemoteDataSource() {
        this.mealSearchService = MealSearchApi.getService();
    }

    public void getCategories(MealsSearchNetworkResponse<Category> callback) {
        mealSearchService.getCategoriesList().enqueue(new Callback<CategoryResponse>() {
            @Override
            public void onResponse(Call<CategoryResponse> call, Response<CategoryResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    CategoryResponse categoryResponse = response.body();

                    if (categoryResponse.getCategories() != null) {
                        Log.d("Categories", categoryResponse.getCategories().toString());
                        callback.onSuccess(categoryResponse.getCategories());
                    } else {
                        callback.onFailure("No categories found");
                    }

                } else {
                    callback.onFailure("Server error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<CategoryResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    callback.noInternet("Network Connection");
                } else {
                    callback.onFailure("Conversion Error! Please try again.");
                }
            }
        });
    }

    public void getAreas(MealsSearchNetworkResponse<Area> callback) {
        mealSearchService.getAreasList().enqueue(new Callback<AreaResponse>() {
            @Override
            public void onResponse(Call<AreaResponse> call, Response<AreaResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    AreaResponse categoryResponse = response.body();

                    if (categoryResponse.getAreas() != null) {
                        callback.onSuccess(categoryResponse.getAreas());
                    } else {
                        callback.onFailure("No categories found");
                    }

                } else {
                    callback.onFailure("Server error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<AreaResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    callback.noInternet("Network Connection");
                } else {
                    callback.onFailure("Conversion Error! Please try again.");
                }
            }
        });
    }


    public void getIngredients(MealsSearchNetworkResponse<Ingredient> callback) {
        Log.d("Ingredients", "1 getIngredients");
        mealSearchService.getIngredientsList().enqueue(new Callback<IngredientsResponse>() {
            @Override
            public void onResponse(Call<IngredientsResponse> call, Response<IngredientsResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("Ingredients", "2 getIngredients");
                    IngredientsResponse ingredientsResponse = response.body();
                    Log.d("Ingredients", "3 getIngredients");
                    if (ingredientsResponse.getIngredients() != null) {
                        callback.onSuccess(ingredientsResponse.getIngredients());
                    } else {
                        callback.onFailure("No ingredients found");
                    }

                } else {
                    callback.onFailure("Server error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<IngredientsResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    callback.noInternet("Network Connection");
                } else {
                    callback.onFailure("Conversion Error! Please try again.");
                }
            }
        });
    }

    public void getFilteredMealsByArea(String country, MealsSearchNetworkResponse<FilteredMeal> callback) {
        mealSearchService.getFilteredMealsByArea(country).enqueue(new Callback<FilteredResponse>() {
            @Override
            public void onResponse(Call<FilteredResponse> call, Response<FilteredResponse> response) {
                if (response.code() == 200 && response.body() != null) {
                    FilteredResponse filteredResponse = response.body();
                    List<FilteredMeal> filteredMeals = filteredResponse.getFilteredMeals();
                    callback.onSuccess(filteredMeals);
                } else {
                    callback.onFailure("Server error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<FilteredResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    callback.noInternet("Network Connection");
                } else {
                    callback.onFailure("Conversion Error! Please try again.");
                }
            }
        });
    }

    public void getFilteredMealsByCategory(String category, MealsSearchNetworkResponse<FilteredMeal> callback) {
        mealSearchService.getFilteredMealsByCategory(category).enqueue(new Callback<FilteredResponse>() {
            @Override
            public void onResponse(Call<FilteredResponse> call, Response<FilteredResponse> response) {
                if (response.code() == 200 && response.body() != null) {
                    FilteredResponse filteredResponse = response.body();
                    List<FilteredMeal> filteredMeals = filteredResponse.getFilteredMeals();
                    callback.onSuccess(filteredMeals);
                } else {
                    callback.onFailure("Server error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<FilteredResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    callback.noInternet("Network Connection");
                } else {
                    callback.onFailure("Conversion Error! Please try again.");
                }
            }
        });
    }

    public void getFilteredMealsByIngredient(String ingredient, MealsSearchNetworkResponse<FilteredMeal> callback) {
        mealSearchService.getFilteredMealsByIngredient(ingredient).enqueue(new Callback<FilteredResponse>() {
            @Override
            public void onResponse(Call<FilteredResponse> call, Response<FilteredResponse> response) {
                if (response.code() == 200 && response.body() != null) {
                    FilteredResponse filteredResponse = response.body();
                    List<FilteredMeal> filteredMeals = filteredResponse.getFilteredMeals();
                    callback.onSuccess(filteredMeals);
                } else {
                    callback.onFailure("Server error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<FilteredResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    callback.noInternet("Network Connection");
                } else {
                    callback.onFailure("Conversion Error! Please try again.");
                }
            }
        });
    }

    public void getMealById(String id, MealsSearchNetworkResponse<Meal> callback) {
        mealSearchService.getMealById(id).enqueue(new Callback<MealByIdResponse>() {
            @Override
            public void onResponse(Call<MealByIdResponse> call, Response<MealByIdResponse> response) {
                if (response.code() == 200 && response.body() != null) {
                    MealByIdResponse filteredResponse = response.body();
                    List<Meal> meal = filteredResponse.getMeals();
                    callback.onSuccess(meal);
                } else {
                    callback.onFailure("Server error: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<MealByIdResponse> call, Throwable t) {
                if (t instanceof IOException) {
                    callback.noInternet("Network Connection");
                } else {
                    callback.onFailure("Conversion Error! Please try again.");
                }
            }
        });
    }
}
