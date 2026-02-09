package com.example.foodie.presentation.home.presenter;

import android.content.Context;
import android.util.Log;

import com.example.foodie.data.home.MealHomeRepo;
import com.example.foodie.presentation.home.view.HomeView;
import com.example.foodie.utils.sharedprefs.SharedPrefsManager;

import java.io.IOException;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomePresenterImpl implements HomePresenter {
    private final HomeView homeView;
    private final MealHomeRepo mealHomeRepo;
    private final CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final SharedPrefsManager sharedPrefsManager;


    public HomePresenterImpl(Context context, HomeView homeView) {
        this.mealHomeRepo = new MealHomeRepo();
        this.homeView = homeView;
        sharedPrefsManager = SharedPrefsManager.getInstance(context);
    }

    @Override
    public void getRandomMeal() {
        homeView.showProgress();
        compositeDisposable.add(mealHomeRepo.getRandomeMeal()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        meal -> {
                            homeView.hideProgress();
                            homeView.showRandomMeal(meal);
                        },
                        throwable -> {
                            if (throwable instanceof IOException) {
                                homeView.showNetworkError("No Internet Connection");
                            } else {
                                homeView.showError("Something went wrong");
                            }
                        }
                ));
    }

    @Override
    public void getPopularMeals() {
        homeView.showProgress();

        compositeDisposable.add(
                mealHomeRepo.getRandomMeals(10)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                meals -> {
                                    homeView.hideProgress();
                                    homeView.showPopularMeals(meals);
                                },
                                throwable -> {
                                    homeView.hideProgress();
                                    if (throwable instanceof IOException) {
                                        homeView.showNetworkError("No Internet Connection");
                                    } else {
                                        homeView.showError("Something went wrong");
                                    }
                                }
                        )
        );
    }

    @Override
    public void loadUserName() {
        Log.e("HomePresenter", "loadUserName()");

        compositeDisposable.add(
                sharedPrefsManager.getUsername()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                username -> {
                                    homeView.showUserData(
                                            username.isEmpty() ? "Guest" : username
                                    );
                                    Log.e("HomePresenter", username);

                                },
                                throwable -> Log.e("HomePresenter", throwable.getMessage())
                        )
        );
    }


    @Override
    public void destroy() {
        compositeDisposable.dispose();
    }
}
