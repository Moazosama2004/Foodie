package com.example.foodie.data.auth.datasource.local;

import android.content.Context;

import com.example.foodie.utils.SharedPrefsImpl;
import com.example.foodie.utils.SharedPrefsService;

public class AuthLocalDataSource {
    private SharedPrefsService sharedPrefsService;

    public AuthLocalDataSource(Context context) {
        this.sharedPrefsService = SharedPrefsImpl.getInstance(context);
    }

    public void setUserLoggedIn() {
        sharedPrefsService.setUserLoggedIn(true);
    }
}
