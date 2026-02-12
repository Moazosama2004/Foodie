package com.example.foodie.presentation.fav.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.foodie.data.home.model.response.Meal;
import com.example.foodie.databinding.FragmentFavBinding;
import com.example.foodie.presentation.auth.view.AuthActivity;
import com.example.foodie.presentation.details.view.MealDetailsActivity;
import com.example.foodie.presentation.fav.presenter.FavPresenter;
import com.example.foodie.presentation.fav.presenter.FavPresenterImpl;
import com.example.foodie.utils.CustomAlertDialog;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class FavFragment extends Fragment
        implements FavView,
        OnDeleteClickListener,
        CustomAlertDialog.OnConfirmationListener {

    private FavouriteMealsAdapter adapter;
    private FavPresenter presenter;
    private FragmentFavBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new FavPresenterImpl(requireContext(), this);
        adapter = new FavouriteMealsAdapter(this, this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentFavBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {

        binding.favRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.favRecyclerView.setAdapter(adapter);

        presenter.checkLoginAndLoad();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.clear();
        binding = null;
    }

    @Override
    public void showProgress() {
        binding.loadingOverlay.setVisibility(View.VISIBLE);
        binding.favRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void hideProgress() {
        binding.loadingOverlay.setVisibility(View.GONE);
        binding.favRecyclerView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(String message) {
        if (getView() != null && message != null) {
            Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void showFavMeals(List<Meal> meals) {
        binding.emptyView.setVisibility(View.GONE);
        binding.favRecyclerView.setVisibility(View.VISIBLE);
        adapter.setFavouriteMeals(meals);
    }

    @Override
    public void showEmptyFav() {
        binding.emptyView.setVisibility(View.VISIBLE);
        binding.favRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void goToDetails(Meal meal) {
        Intent intent = new Intent(getContext(), MealDetailsActivity.class);
        intent.putExtra("MEAL_KEY", meal);
        startActivity(intent);
    }

    @Override
    public void showGuestAlert() {
        CustomAlertDialog.showGuestModeAlert(requireContext(), this);
    }

    @Override
    public void onDeleteClick(Meal meal) {
        presenter.deleteMeal(meal);
    }


    @Override
    public void onConfirm() {
        startActivity(new Intent(getContext(), AuthActivity.class));
    }
}
