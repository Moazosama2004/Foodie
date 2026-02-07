package com.example.foodie.utils.sharedprefs;

public interface UserSessionManager {
    boolean isLoggedIn();
    void setLoggedIn(boolean loggedIn);
}
