package com.example.foodie.data.search.api;

import java.util.List;

public interface MealsSearchNetworkResponse<T> {

    void onSuccess(List<T> data);

    void onFailure(String message);

    void noInternet(String message);
}
