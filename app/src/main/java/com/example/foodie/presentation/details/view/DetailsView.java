package com.example.foodie.presentation.details.view;

import com.example.foodie.data.home.model.response.Meal;

public interface DetailsView {
    void showProgress();
    void hideProgress();
    void showError(String message);
    void onSuccess();
}
