package com.example.foodie.presentation.home.view;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.foodie.MealDetailsActivity;
import com.example.foodie.R;
import com.example.foodie.data.home.model.response.Meal;
import com.example.foodie.presentation.home.presenter.HomePresenter;
import com.example.foodie.presentation.home.presenter.HomePresenterImpl;

import java.util.List;

public class HomeFragment extends Fragment implements HomeView {
    private ConstraintLayout mealOfTheDayLayout;
    private HomePresenter homePresenter;
    private RecyclerView recyclerView;

    private PopularMealsAdapter adapter;
    private Button showDetailsBtn;
    private Meal meal;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homePresenter = new HomePresenterImpl(this);
        homePresenter.getRandomMeal();
        homePresenter.getPopularMeals();
        adapter = new PopularMealsAdapter();
        adapter.setOnMealClickListener(new OnMealClickListener() {
            @Override
            public void onMealClick(Meal meal) {
                Intent intent = new Intent(getActivity(), MealDetailsActivity.class);
                startActivity(intent);
            }
        });


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
        recyclerView = view.findViewById(R.id.meals_recycler_view);
        showDetailsBtn = view.findViewById(R.id.show_details_btn);
        showDetailsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MealDetailsActivity.class);
                intent.putExtra("MEAL_KEY", meal);
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setClipToPadding(true);
        recyclerView.setClipChildren(true);
        recyclerView.setPadding(50, 0, 50, 0);
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
    public void showPopularMeals(List<Meal> meals) {
        Log.d("Meals", meals.toString());
        adapter.setPopularMeals(meals);
    }

    @Override
    public void showOneMeal(Meal meal) {
        this.meal = meal;
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
                    }
                });
    }
}