package com.example.foodie.presentation.search.view;

import com.example.foodie.data.search.model.Category;

import java.util.List;

public interface SearchView<T> {
    void showData(List<T> categories);

    void showError(String message);

    void showNoInternet(String message);
    void showLoading();
    void hideLoading();



}
