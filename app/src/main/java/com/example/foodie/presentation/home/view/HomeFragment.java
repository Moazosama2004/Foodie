package com.example.foodie.presentation.home.view;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.foodie.R;
import com.example.foodie.data.home.model.response.Meal;
import com.example.foodie.databinding.FragmentHomeBinding;
import com.example.foodie.presentation.details.view.MealDetailsActivity;
import com.example.foodie.presentation.home.presenter.HomePresenter;
import com.example.foodie.presentation.home.presenter.HomePresenterImpl;
import com.example.foodie.utils.sharedprefs.SharedPrefsManager;

import java.util.List;

public class HomeFragment extends Fragment implements HomeView {

    private FragmentHomeBinding binding;
    private HomePresenter homePresenter;
    private PopularMealsAdapter adapter;

    private Meal meal;

    private boolean randomMealLoaded = false;
    private boolean popularMealsLoaded = false;

    private final Handler handler = new Handler(Looper.getMainLooper());


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homePresenter = new HomePresenterImpl(this);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews();
        setupRecyclerView();
        setupClicks();

        showProgress();
        homePresenter.getRandomMeal();
        homePresenter.getPopularMeals();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        handler.removeCallbacksAndMessages(null);
        binding = null;
    }


    private void initViews() {
        binding.username.setText(
                SharedPrefsManager.getInstance(requireContext()).getUsername()
        );
    }

    private void setupRecyclerView() {
        adapter = new PopularMealsAdapter();
        adapter.setOnMealClickListener(meal -> {
            Intent intent = new Intent(getActivity(), MealDetailsActivity.class);
            intent.putExtra("MEAL_KEY", meal);
            startActivity(intent);
        });

        binding.mealsRecyclerView.setLayoutManager(
                new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        );
        binding.mealsRecyclerView.setAdapter(adapter);
        binding.mealsRecyclerView.setPadding(50, 0, 50, 0);
        binding.mealsRecyclerView.setClipToPadding(false);
    }

    private void setupClicks() {

        binding.retryBtn.setOnClickListener(v -> {
            binding.networkErrorOverlay.setVisibility(View.GONE);
            showProgress();

            randomMealLoaded = false;
            popularMealsLoaded = false;

            homePresenter.getRandomMeal();
            homePresenter.getPopularMeals();
        });

        binding.searchTxt.setOnClickListener(v -> {
            if (!isAdded()) return;
            NavController navController = Navigation.findNavController(requireView());
            navController.navigate(
                    R.id.action_bottom_nav_bar_home_to_bottom_nav_bar_search
            );
        });

        binding.showDetailsBtn.setOnClickListener(v -> {
            if (meal == null) return;

            Intent intent = new Intent(getActivity(), MealDetailsActivity.class);
            intent.putExtra("MEAL_KEY", meal);
            startActivity(intent);
        });
    }


    @Override
    public void showProgress() {
        if (binding != null) {
            binding.loadingOverlay.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideProgress() {
        if (binding != null) {
            binding.loadingOverlay.setVisibility(View.GONE);
        }
    }

    @Override
    public void showNetworkError(String message) {
        if (binding == null) return;

        binding.loadingOverlay.setVisibility(View.GONE);
        binding.networkErrorOverlay.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(String message) {
        Log.e("HomeFragment", message);
    }

    @Override
    public void showPopularMeals(List<Meal> meals) {
        adapter.setPopularMeals(meals);
        popularMealsLoaded = true;
        hideLoadingIfDataReady();
    }

    @Override
    public void showOneMeal(Meal meal) {
        this.meal = meal;
        randomMealLoaded = true;
        showMealImage(meal);
        hideLoadingIfDataReady();
    }


    private void showMealImage(Meal meal) {
        Glide.with(this)
                .load(meal.getStrMealThumb())
                .centerCrop()
                .into(new CustomTarget<Drawable>() {
                    @Override
                    public void onResourceReady(
                            @NonNull Drawable resource,
                            @Nullable Transition<? super Drawable> transition
                    ) {
                        if (binding != null) {
                            binding.mealOfTheDayLayout.setBackground(resource);
                        }
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });
    }

    private void hideLoadingIfDataReady() {

        if (randomMealLoaded && popularMealsLoaded) {
            hideProgress();
            return;
        }

        handler.postDelayed(() -> {
            if (!isAdded() || binding == null) return;

            if (!randomMealLoaded || !popularMealsLoaded) {
                hideProgress();
                binding.networkErrorOverlay.setVisibility(View.VISIBLE);
            }
        }, 5000);
    }
}
