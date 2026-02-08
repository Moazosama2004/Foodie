package com.example.foodie.presentation.home.presenter;

import com.example.foodie.data.home.MealHomeRepo;
import com.example.foodie.data.home.api.MealHomeNetworkResponse;
import com.example.foodie.data.home.model.response.Meal;
import com.example.foodie.presentation.home.view.HomeView;

import java.io.IOException;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class HomePresenterImpl implements HomePresenter {
    private final HomeView homeView;
    private final MealHomeRepo mealHomeRepo;
    private CompositeDisposable compositeDisposable;


    public HomePresenterImpl(HomeView homeView) {
        this.mealHomeRepo = new MealHomeRepo();
        this.homeView = homeView;
        this.compositeDisposable = new CompositeDisposable();
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
    public void destroy() {
        compositeDisposable.dispose();
    }
}
