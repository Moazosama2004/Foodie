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
import com.example.foodie.presentation.calender.presenter.CalenderPresenter;
import com.example.foodie.presentation.calender.presenter.CalenderPresenterImpl;
import com.example.foodie.presentation.details.view.MealDetailsActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class PlanFragment extends Fragment implements CalenderView, OnMealClickListener ,OnDeleteClickListener{

    private FragmentPlanBinding binding;
    private CalenderPresenter presenter;
    private CalendarMealAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new CalenderPresenterImpl(requireContext().getApplicationContext(), this);
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .format(new Date());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPlanBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new CalendarMealAdapter(this,this);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Load today's meals AFTER recyclerView is initialized
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .format(new Date());

        presenter.syncMeals()
                .flatMap(meals -> presenter.getMealsByDate(today))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(dayMeals -> {
                    if (dayMeals == null || dayMeals.isEmpty()) {
                        showEmptyDay();
                    } else {
                        showMeals(dayMeals);
                    }
                }, throwable -> {
                    Log.e("PlanFragment", "Error", throwable);
                });


        binding.calendar.setOnDateChangeListener((calenderView, year, month, dayOfMonth) -> {
            String date = year + "-" + String.format("%02d", month + 1) + "-" + String.format("%02d", dayOfMonth);
            binding.dateView.setText(date);

            presenter.getMealsByDate(date)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(meals -> {
                        if (meals == null || meals.isEmpty()) {
                            showEmptyDay();
                        } else {
                            showMeals(meals);
                        }
                    }, throwable -> {
                        Log.e("PlanFragment", "Error loading meals", throwable);
                    });
        });

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
    public void showMeals(List<CalendarMeal> meals) {
        Log.d("PlanFragment", "showMeals: " + meals.size());
        binding.recyclerView.setVisibility(View.VISIBLE);
        binding.emptyView.setVisibility(View.GONE);
        adapter.setMeals(meals);
    }

    @Override
    public void showEmptyDay() {
        binding.recyclerView.setVisibility(View.GONE);
        binding.emptyView.setVisibility(View.VISIBLE);
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
        presenter.getMealsByMealId(id)
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
                });
    }

    @Override
    public void onDeleteClick(CalendarMeal meal) {
        Log.d("PlanFragment", "onDeleteClick: " + meal.getMealName());
        presenter.deleteMealsByDate(meal) .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        () -> {
                            Log.d("PlanFragment", "Meal deleted successfully");
                            String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                                    .format(new Date());
                            presenter.getMealsByDate(today)
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe(this::showMeals, throwable -> showError(throwable.getMessage()));
                        },
                        throwable -> {
                            Log.e("PlanFragment", "Error deleting meal", throwable);
                            showError(throwable.getMessage());
                        }
                );;
    }
}
