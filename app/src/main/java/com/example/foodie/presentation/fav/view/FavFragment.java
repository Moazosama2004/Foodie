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
import com.example.foodie.utils.sharedprefs.SharedPrefsManager;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavFragment extends Fragment implements FavView, OnDeleteClickListener, CustomAlertDialog.OnConfirmationListener {

    private final CompositeDisposable disposables = new CompositeDisposable();
    private FavouriteMealsAdapter adapter;
    private FavPresenter presenter;
    private FragmentFavBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new FavouriteMealsAdapter(this, this);
        presenter = new FavPresenterImpl(requireContext(), this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFavBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.favRecyclerView.setAdapter(adapter);
        binding.favRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        disposables.add(SharedPrefsManager.getInstance(requireContext()).isLoggedIn()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isLoggedIn -> {
                    if (isLoggedIn) {
                        loadFavorites();
                    } else {
                        showEmptyFav();
                        CustomAlertDialog.showGuestModeAlert(requireContext(), this);
                    }
                }, this::showError)
        );
    }

    private void loadFavorites() {
        disposables.add(
                presenter.getFavMeals()
                        .subscribe(
                                favMeals -> {
                                    if (favMeals.isEmpty()) {
                                        showEmptyFav();
                                    } else {
                                        adapter.setFavouriteMeals(favMeals);
                                        binding.favRecyclerView.setVisibility(View.VISIBLE);
                                        binding.emptyView.setVisibility(View.GONE);
                                    }
                                },
                                this::showError
                        )
        );
    }

    @Override
    public void onDeleteClick(Meal meal) {
        disposables.add(
                presenter.deleteFromFavRemote(meal)
                        .andThen(presenter.deleteFromFavLocal(meal))
                        .andThen(presenter.getFavMeals())
                        .subscribe(
                                favMeals -> {
                                    if (favMeals.isEmpty()) {
                                        showEmptyFav();
                                    } else {
                                        adapter.setFavouriteMeals(favMeals);
                                    }
                                },
                                this::showError
                        )
        );
    }

    @Override
    public void onDestroyView() {
        disposables.clear();
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void showProgress() {
        if (binding != null) {
            binding.loadingOverlay.setVisibility(View.VISIBLE);
            binding.favRecyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void hideProgress() {
        if (binding != null) {
            binding.loadingOverlay.setVisibility(View.GONE);
            binding.favRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showError(String message) {
        if (getView() != null && message != null) {
            Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void showFavMeals(List<Meal> favMeals) {
        // Handled in subscription
    }


    @Override
    public void goToDetails(Meal meal) {
        Intent intent = new Intent(getContext(), MealDetailsActivity.class);
        intent.putExtra("MEAL_KEY", meal);
        startActivity(intent);
    }

    @Override
    public void onSuccess() {
    }

    @Override
    public void showEmptyFav() {
        if (binding != null) {
            binding.emptyView.setVisibility(View.VISIBLE);
            binding.favRecyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void showError(Throwable throwable) {
        if (throwable != null) {
            showError(throwable.getMessage());
        }
    }

    @Override
    public void onDeleteSuccess(String mealId) {
    }

    @Override
    public void onConfirm() {
        if (getActivity() != null) {
            Intent intent = new Intent(getContext(), AuthActivity.class);
            startActivity(intent);
        }
    }
}
