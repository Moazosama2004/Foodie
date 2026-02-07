package com.example.foodie.presentation.home.view;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.foodie.MealDetailsActivity;
import com.example.foodie.R;
import com.example.foodie.data.home.model.response.Meal;
import com.example.foodie.presentation.home.presenter.HomePresenter;
import com.example.foodie.presentation.home.presenter.HomePresenterImpl;
import com.example.foodie.utils.sharedprefs.SharedPrefsManager;

import java.util.List;

public class HomeFragment extends Fragment implements HomeView {
    private ConstraintLayout mealOfTheDayLayout;
    private HomePresenter homePresenter;
    private RecyclerView recyclerView;
    private TextView userName;

    private PopularMealsAdapter adapter;
    private Button showDetailsBtn;
    private EditText searchTxt;
    private Meal meal;

    private FrameLayout loadingOverlay;
    private LottieAnimationView loadingLottie;
    private boolean randomMealLoaded = false;
    private boolean popularMealsLoaded = false;

    private FrameLayout networkErrorOverlay;
    private Button retryBtn;

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
                intent.putExtra("MEAL_KEY", meal);
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

        // init views
        mealOfTheDayLayout = view.findViewById(R.id.meal_of_the_day_layout);
        recyclerView = view.findViewById(R.id.meals_recycler_view);
        showDetailsBtn = view.findViewById(R.id.show_details_btn);
        searchTxt = view.findViewById(R.id.search_txt);
        loadingOverlay = view.findViewById(R.id.loading_overlay);
        loadingLottie = view.findViewById(R.id.loading_lottie);
        networkErrorOverlay = view.findViewById(R.id.network_error_overlay);
        retryBtn = view.findViewById(R.id.retry_btn);
        userName = view.findViewById(R.id.username);


        userName.setText(SharedPrefsManager.getInstance(requireContext()).getUsername());



        // set up adapter
        adapter = new PopularMealsAdapter();
        adapter.setOnMealClickListener(meal -> {
            Intent intent = new Intent(getActivity(), MealDetailsActivity.class);
            intent.putExtra("MEAL_KEY", meal);
            startActivity(intent);
        });
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setClipToPadding(true);
        recyclerView.setClipChildren(true);
        recyclerView.setPadding(50, 0, 50, 0);

        // retry button
        retryBtn.setOnClickListener(v -> {
            networkErrorOverlay.setVisibility(View.GONE);
            loadingOverlay.setVisibility(View.VISIBLE);
            randomMealLoaded = false;
            popularMealsLoaded = false;
            homePresenter.getRandomMeal();
            homePresenter.getPopularMeals();
        });

        // search click
        searchTxt.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_bottom_nav_bar_home_to_bottom_nav_bar_search)
        );

        // show details click
        showDetailsBtn.setOnClickListener(v -> {
            if (meal != null) {
                Intent intent = new Intent(getActivity(), MealDetailsActivity.class);
                intent.putExtra("MEAL_KEY", meal);
                startActivity(intent);
            }
        });

        // init presenter AFTER all views are ready
        homePresenter = new HomePresenterImpl(this);
        homePresenter.getRandomMeal();
        homePresenter.getPopularMeals();
    }
    // حماية ضد null
    @Override
    public void showProgress() {
        if (loadingOverlay != null) loadingOverlay.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        if (loadingOverlay != null) loadingOverlay.setVisibility(View.GONE);
    }

    @Override
    public void showNetworkError(String message) {
        if (loadingOverlay != null) loadingOverlay.setVisibility(View.GONE);
        if (networkErrorOverlay != null) networkErrorOverlay.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void showPopularMeals(List<Meal> meals) {
        Log.d("Meals", meals.toString());
        adapter.setPopularMeals(meals);
        popularMealsLoaded = true;
        Log.d("Meals", "showPopularMeals:" + popularMealsLoaded);
        hideLoadingIfDataReady();
    }

    @Override
    public void showOneMeal(Meal meal) {
        this.meal = meal;
        showImageofMeal(meal);
        randomMealLoaded = true;
        Log.d("Meals", "showOneMeal:"+ randomMealLoaded) ;
        hideLoadingIfDataReady();
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

    private void hideLoadingIfDataReady() {
        Log.d("Meals", "hideLoadingIfDataReady:" + randomMealLoaded + " " + popularMealsLoaded);
        if (randomMealLoaded && popularMealsLoaded) {
            loadingOverlay.setVisibility(View.GONE);
        } else {
            new android.os.Handler().postDelayed(() -> {
                if (!randomMealLoaded || !popularMealsLoaded) {
                    loadingOverlay.setVisibility(View.GONE);
                    networkErrorOverlay.setVisibility(View.VISIBLE);
                }
            }, 5000);
        }
    }

}