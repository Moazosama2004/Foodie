package com.example.foodie.data.core.model;


import com.example.foodie.data.home.model.response.Meal;

import java.util.List;

public class User {
    private String userId;
    private String username;
    private String email;
    private List<Meal> meals;

    // Empty constructor for Firestore
    public User() {
    }

    public User(String userId, String username, String email, List<Meal> meals) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.meals = meals;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public void setMeals(List<Meal> meals) {
        this.meals = meals;
    }
}
