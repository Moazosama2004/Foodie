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
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodie.R;
import com.example.foodie.data.home.model.response.Meal;
import com.example.foodie.databinding.FragmentFavBinding;
import com.example.foodie.presentation.details.view.MealDetailsActivity;
import com.example.foodie.presentation.fav.presenter.FavPresenter;
import com.example.foodie.presentation.fav.presenter.FavPresenterImpl;

import java.util.List;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class FavFragment extends Fragment implements FavView, OnDeleteClickListener {

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentFavBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView favRecyclerView = view.findViewById(R.id.fav_recycler_view);
        favRecyclerView.setAdapter(adapter);
        favRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // RxJava load favorites
        disposables.add(
                presenter.getFavMeals()
                        .subscribe(
                                favMeals -> adapter.setFavouriteMeals(favMeals),
                                throwable -> showError(throwable.getMessage())
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
                                favMeals -> adapter.setFavouriteMeals(favMeals),
                                throwable -> showError(throwable.getMessage())
                        )
        );
    }

    @Override
    public void onDestroyView() {
        disposables.clear();
        super.onDestroyView();
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
    }

    @Override
    public void showFavMeals(List<Meal> favMeals) {
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
        binding.emptyView.setVisibility(View.VISIBLE);
        binding.favRecyclerView.setVisibility(View.GONE);
    }

    @Override
    public void showError(Throwable throwable) {
    }

    @Override
    public void onDeleteSuccess(String mealId) {
    }
}
