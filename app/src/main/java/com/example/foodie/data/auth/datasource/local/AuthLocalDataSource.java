package com.example.foodie.data.auth.datasource.local;

import android.content.Context;

import com.example.foodie.utils.sharedprefs.SharedPrefsManager;

public class AuthLocalDataSource {
    private final SharedPrefsManager sharedPrefsService;

    public AuthLocalDataSource(Context context) {
        this.sharedPrefsService = SharedPrefsManager.getInstance(context);
    }

    public void setUserLoggedIn() {
        sharedPrefsService.setLoggedIn(true);
    }
}
