package com.example.foodie.presentation.fav.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodie.MealDetailsActivity;
import com.example.foodie.R;
import com.example.foodie.data.home.model.response.Meal;
import com.example.foodie.presentation.fav.presenter.FavPresenter;
import com.example.foodie.presentation.fav.presenter.FavPresenterImpl;

import java.util.List;


public class FavFragment extends Fragment implements FavView, OnDeleteClickListener {

    private FavouriteMealsAdapter adapter;
    private FavPresenter presenter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new FavouriteMealsAdapter(this , this);
        presenter = new FavPresenterImpl(requireContext().getApplicationContext(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_fav, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView favRecyclerView = view.findViewById(R.id.fav_recycler_view);
        favRecyclerView.setAdapter(adapter);
        favRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        presenter.getFavMeals().observe(
                getViewLifecycleOwner(),
                favMeals -> {
                    if (favMeals != null) {
                        adapter.setFavouriteMeals(favMeals);
                    }
                }
        );

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
    public void showFavMeals(List<Meal> favMeals) {
//        adapter.setFavouriteMeals(favMeals);
    }

    @Override
    public LifecycleOwner getLifecycleOwner() {
        return getViewLifecycleOwner();
    }

    @Override
    public void goToDetails(Meal meal) {
        Intent intent = new Intent(getContext(), MealDetailsActivity.class);
        intent.putExtra("MEAL_KEY", meal);
        startActivity(intent);
    }


    @Override
    public void onDeleteClick(Meal meal) {
        presenter.deleteFromFavLocal(meal);
        Log.d("TAG", "onDeleteClick: " + meal.getIdMeal());
        presenter.deleteFromFavRemote(meal.getIdMeal());
    }
}