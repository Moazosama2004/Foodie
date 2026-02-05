package com.example.foodie.presentation.details.view;

public interface DetailsView {
    void showProgress();

    void hideProgress();

    void showError(String message);

    void onSuccess();
}
