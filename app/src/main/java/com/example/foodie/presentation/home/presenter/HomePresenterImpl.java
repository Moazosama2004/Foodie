package com.example.foodie.presentation.home.presenter;

import com.example.foodie.data.home.MealHomeRepo;
import com.example.foodie.data.home.api.MealHomeNetworkResponse;
import com.example.foodie.data.home.model.response.Meal;
import com.example.foodie.presentation.home.view.HomeView;

import java.util.List;

public class HomePresenterImpl implements HomePresenter {
    private final HomeView homeView;
    private final MealHomeRepo mealHomeRepo;


    public HomePresenterImpl(HomeView homeView) {
        this.mealHomeRepo = new MealHomeRepo();
        this.homeView = homeView;
    }

    @Override
    public void getRandomMeal() {
        homeView.showProgress();
        mealHomeRepo.getRandomeMeal(new MealHomeNetworkResponse() {
            @Override
            public void onSuccessOneMeal(Meal meal) {
                homeView.hideProgress();
                homeView.showOneMeal(meal);
            }

            @Override
            public void onSuccessMeals(List<Meal> meals) {

            }

            @Override
            public void onFailure(String message) {
                homeView.hideProgress();
                homeView.showError(message);
            }

            @Override
            public void noInternet(String message) {
                homeView.hideProgress();
                homeView.showNetworkError(message);
            }
        });
    }

    @Override
    public void getPopularMeals() {
        homeView.showProgress();
        mealHomeRepo.getRandomMeals(10, new MealHomeNetworkResponse() {
            @Override
            public void onSuccessOneMeal(Meal meal) {
            }

            @Override
            public void onSuccessMeals(List<Meal> meals) {
                homeView.hideProgress();
                homeView.showPopularMeals(meals);
            }

            @Override
            public void onFailure(String message) {
                homeView.hideProgress();
                homeView.showError(message);
            }

            @Override
            public void noInternet(String message) {
                homeView.hideProgress();
                homeView.showNetworkError(message);
            }
        });
    }
}
