package com.example.foodie.presentation.calender.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.foodie.data.calender.model.CalendarMeal;
import com.example.foodie.data.home.model.response.Meal;
import com.example.foodie.databinding.FragmentPlanBinding;
import com.example.foodie.presentation.auth.view.AuthActivity;
import com.example.foodie.presentation.calender.presenter.CalenderPresenter;
import com.example.foodie.presentation.calender.presenter.CalenderPresenterImpl;
import com.example.foodie.presentation.details.view.MealDetailsActivity;
import com.example.foodie.utils.CustomAlertDialog;
import com.example.foodie.utils.sharedprefs.SharedPrefsManager;
import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PlanFragment extends Fragment implements CalenderView, OnMealClickListener, OnDeleteClickListener, CustomAlertDialog.OnConfirmationListener {

    private final CompositeDisposable disposables = new CompositeDisposable();
    private FragmentPlanBinding binding;
    private CalenderPresenter presenter;
    private CalendarMealAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new CalenderPresenterImpl(requireContext(), this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPlanBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new CalendarMealAdapter(this, this);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        disposables.add(SharedPrefsManager.getInstance(requireContext()).isLoggedIn()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(isLoggedIn -> {
                    if (isLoggedIn) {
                        loadCalendarData();
                    } else {
                        showEmptyDay();
                        CustomAlertDialog.showGuestModeAlert(requireContext(), this);
                    }
                }, throwable -> {
                    Log.e("PlanFragment", "Error checking login status", throwable);
                    showError(throwable.getMessage());
                })
        );
    }

    private void loadCalendarData() {
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        binding.dateView.setText(today);

        disposables.add(presenter.syncMeals()
                .flatMap(meals -> presenter.getMealsByDate(today))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dayMeals -> {
                    if (dayMeals == null || dayMeals.isEmpty()) {
                        showEmptyDay();
                    } else {
                        showMeals(dayMeals);
                    }
                }, throwable -> {
                    Log.e("PlanFragment", "Error", throwable);
                    showError(throwable.getMessage());
                })
        );

        binding.calendar.setOnDateChangeListener((calenderView, year, month, dayOfMonth) -> {
            String date = year + "-" + String.format("%02d", month + 1) + "-" + String.format("%02d", dayOfMonth);
            binding.dateView.setText(date);

            disposables.add(presenter.getMealsByDate(date)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(meals -> {
                        if (meals == null || meals.isEmpty()) {
                            showEmptyDay();
                        } else {
                            showMeals(meals);
                        }
                    }, throwable -> {
                        Log.e("PlanFragment", "Error loading meals", throwable);
                        showError(throwable.getMessage());
                    })
            );
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        disposables.clear();
        binding = null;
    }

    @Override
    public void showProgress() {
    }

    @Override
    public void hideProgress() {
    }

    @Override
    public void showError(String message) {
        if (getView() != null && message != null) {
            Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void showMeals(List<CalendarMeal> meals) {
        if (binding != null) {
            Log.d("PlanFragment", "showMeals: " + meals.size());
            binding.recyclerView.setVisibility(View.VISIBLE);
            binding.emptyView.setVisibility(View.GONE);
            adapter.setMeals(meals);
        }
    }

    @Override
    public void showEmptyDay() {
        if (binding != null) {
            binding.recyclerView.setVisibility(View.GONE);
            binding.emptyView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void goToMealDetails(Meal meal) {
        Log.d("PlanFragment", "goToMealDetails: " + meal.getStrMeal());
        Intent intent = new Intent(getActivity(), MealDetailsActivity.class);
        intent.putExtra("MEAL_KEY", meal);
        startActivity(intent);
    }

    @Override
    public void onMealClick(String id) {
        disposables.add(presenter.getMealsByMealId(id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(meal -> {
                    if (meal != null) {
                        Meal m = new Meal();
                        m.setIdMeal(meal.getMealId());
                        m.setStrMeal(meal.getMealName());
                        m.setStrMealThumb(meal.getMealImage());
                        goToMealDetails(m);
                    }
                }, throwable -> {
                    Log.e("PlanFragment", "Error fetching meal by id", throwable);
                    showError(throwable.getMessage());
                })
        );
    }

    @Override
    public void onDeleteClick(CalendarMeal meal) {
        Log.d("PlanFragment", "onDeleteClick: " + meal.getMealName());
        disposables.add(presenter.deleteMealsByDate(meal).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            Log.d("PlanFragment", "Meal deleted successfully");
                            String selectedDate = binding.dateView.getText().toString();
                            presenter.getMealsByDate(selectedDate)
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(this::showMeals, throwable -> showError(throwable.getMessage()));
                        },
                        throwable -> {
                            Log.e("PlanFragment", "Error deleting meal", throwable);
                            showError(throwable.getMessage());
                        }
                )
        );
    }

    @Override
    public void onConfirm() {
        if (getActivity() != null) {
            Intent intent = new Intent(getContext(), AuthActivity.class);
            startActivity(intent);
        }
    }
}
