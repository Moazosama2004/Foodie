package com.example.foodie.presentation.calender.view;

import android.content.Intent;
import android.os.Bundle;
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
import com.google.android.material.snackbar.Snackbar;
import java.util.List;

public class PlanFragment extends Fragment
        implements CalenderView,
        OnMealClickListener,
        OnDeleteClickListener,
        CustomAlertDialog.OnConfirmationListener {

    private FragmentPlanBinding binding;
    private CalenderPresenter presenter;
    private CalendarMealAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new CalenderPresenterImpl(requireContext(), this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentPlanBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view,
                              @Nullable Bundle savedInstanceState) {

        adapter = new CalendarMealAdapter(this, this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setAdapter(adapter);

        presenter.checkLoginAndLoad();

        binding.calendar.setOnDateChangeListener((calendarView, year, month, day) -> {
            String date = year + "-" +
                    String.format("%02d", month + 1) + "-" +
                    String.format("%02d", day);

            presenter.loadMealsByDate(date);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        presenter.clear();
        binding = null;
    }

    @Override
    public void showProgress() { }

    @Override
    public void hideProgress() { }

    @Override
    public void showError(String message) {
        if (getView() != null && message != null) {
            Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
        }
    }

    @Override
    public void showMeals(List<CalendarMeal> meals) {
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
        Intent intent = new Intent(getActivity(), MealDetailsActivity.class);
        intent.putExtra("MEAL_KEY", meal);
        startActivity(intent);
    }

    @Override
    public void showDate(String date) {
        binding.dateView.setText(date);
    }

    @Override
    public void showGuestAlert() {
        CustomAlertDialog.showGuestModeAlert(requireContext(), this);
    }

    @Override
    public void onMealClick(String id) {
        presenter.onMealSelected(id);
    }

    @Override
    public void onDeleteClick(CalendarMeal meal) {
        String selectedDate = binding.dateView.getText().toString();
        presenter.deleteMeal(meal, selectedDate);
    }

    @Override
    public void onConfirm() {
        startActivity(new Intent(getContext(), AuthActivity.class));
    }
}
