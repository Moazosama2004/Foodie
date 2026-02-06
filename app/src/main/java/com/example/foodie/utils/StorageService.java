package com.example.foodie.utils;

import com.example.foodie.data.home.model.response.Meal;

import java.util.List;

public interface StorageService {
    void saveMeal(Meal meal);
    void deleteMeal(String id);
}
