package com.example.foodie.presentation.calender.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodie.MealDetailsActivity;
import com.example.foodie.R;
import com.example.foodie.data.calender.model.CalendarMeal;
import com.example.foodie.data.home.model.response.Meal;
import com.example.foodie.presentation.calender.presenter.CalenderPresenter;
import com.example.foodie.presentation.calender.presenter.CalenderPresenterImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PlanFragment extends Fragment implements CalenderView, OnMealClickListener {

    private CalendarView calendar;
    private TextView dateView;
    private RecyclerView recyclerView;
    private CalenderPresenter presenter;

    private View mealCard;
    private View emptyView;
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
        return inflater.inflate(R.layout.fragment_plan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        calendar = view.findViewById(R.id.calendar);
        dateView = view.findViewById(R.id.date_view);

        emptyView = view.findViewById(R.id.empty_view);
        recyclerView = view.findViewById(R.id.recycler_view);

        adapter = new CalendarMealAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Load today's meals AFTER recyclerView is initialized
        String today = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                .format(new Date());
        presenter.getMealsByDate(today);

        calendar.setOnDateChangeListener((calenderView, year, month, dayOfMonth) -> {
            String date = year + "-" +
                    String.format("%02d", month + 1) + "-" +
                    String.format("%02d", dayOfMonth);
            dateView.setText(date);
            presenter.getMealsByDate(date);
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
        recyclerView.setVisibility(View.VISIBLE);
        emptyView.setVisibility(View.GONE);
        adapter.setMeals(meals);
    }

    @Override
    public void showEmptyDay() {
        recyclerView.setVisibility(View.GONE);
        emptyView.setVisibility(View.VISIBLE);
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
        Log.d("PlanFragment", "onMealClick: " + id);
        presenter.getMealsByMealId(id);
    }
}
