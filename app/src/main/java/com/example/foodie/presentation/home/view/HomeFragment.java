package com.example.foodie.presentation.home.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.foodie.R;
import com.example.foodie.data.home.model.response.Meal;
import com.example.foodie.presentation.home.presenter.HomePresenter;
import com.example.foodie.presentation.home.presenter.HomePresenterImpl;

import java.util.List;

public class HomeFragment extends Fragment implements  HomeView{
    private ConstraintLayout mealOfTheDayLayout;
    private HomePresenter homePresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homePresenter = new HomePresenterImpl(this);
        homePresenter.getRandomeMeal();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mealOfTheDayLayout = view.findViewById(R.id.meal_of_the_day_layout);

    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void showMeals(List<Meal> meals) {

    }

    @Override
    public void showOneMeal(Meal meal) {
        showImageofMeal(meal);
    }

    public void showImageofMeal(Meal meal) {
        Glide.with(this)
                .load(meal.getStrMealThumb())
                .centerCrop()
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                        mealOfTheDayLayout.setBackground(resource);
                    }

                    @Override
                    public void onLoadCleared(Drawable placeholder) {
                        // optional: خلي background افتراضي لو الصورة اتلغي تحميلها
                    }
                });
    }
}