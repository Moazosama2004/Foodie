package com.example.foodie.utils;

public interface SharedPrefsService {
    boolean userIsLoggedIn();

    void setUserLoggedIn(boolean isLoggedIn);

    boolean isOnboardingSeen();

    void setOnboardingSeen(boolean seen);
}
