// SearchFragment.java
package com.example.foodie.presentation.search.view;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.foodie.R;
import com.example.foodie.data.home.model.response.Meal;
import com.example.foodie.data.search.model.Area;
import com.example.foodie.data.search.model.Category;
import com.example.foodie.data.search.model.Ingredient;
import com.example.foodie.databinding.FragmentSearchBinding;
import com.example.foodie.presentation.details.view.MealDetailsActivity;
import com.example.foodie.presentation.search.presenter.SearchPresenter;
import com.example.foodie.presentation.search.presenter.SearchPresenterImpl;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class SearchFragment extends Fragment implements SearchView, onCardClickListener, OnMealCardListener {

    private FragmentSearchBinding binding;
    private SearchPresenter presenter;

    private CategoriesMealsAdapter categoryAdapter;
    private FoodAdapter foodAdapter;
    private IngredientAdapter ingredientAdapter;
    private FilteredMealsAdapter filteredMealsAdapter;

    private List<Category> allCategories = new ArrayList<>();
    private List<Ingredient> allIngredients = new ArrayList<>();
    private List<Area> allAreas = new ArrayList<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private boolean isConnected = true;

    private int checkedId = -1;
    private Disposable searchDisposable;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SearchPresenterImpl(this);

        categoryAdapter = new CategoriesMealsAdapter(this);
        foodAdapter = new FoodAdapter(this);
        ingredientAdapter = new IngredientAdapter(this);
        filteredMealsAdapter = new FilteredMealsAdapter(this);

        presenter.getCategories();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        observeNetwork();
        // default adapter
        binding.rvCategories.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.rvCategories.setAdapter(categoryAdapter);

        setupChips();
        setupSearch();
    }

    private void showNetworkErrorOverlay(String message) {
        binding.networkErrorOverlay.setVisibility(View.VISIBLE);

        // لو حابب تغير النص ديناميكي
        // TextView title = binding.networkErrorOverlay.findViewById(R.id.error_title);
        // title.setText(message);

        binding.retryBtn.setOnClickListener(v -> {
            if (isConnected) {
                hideNetworkErrorOverlay();
                resetByChip();
            }
        });
    }

    private void hideNetworkErrorOverlay() {
        binding.networkErrorOverlay.setVisibility(View.GONE);
    }

    private void setupChips() {
        binding.chipGroup.setOnCheckedStateChangeListener((group, checkedIds) -> {
            int selectedColor = getResources().getColor(R.color.primary, null);
            int defaultColor = getResources().getColor(R.color.white, null);

            for (int i = 0; i < group.getChildCount(); i++) {
                Chip c = (Chip) group.getChildAt(i);
                c.setChipBackgroundColor(ColorStateList.valueOf(defaultColor));
            }

            if (!checkedIds.isEmpty()) {
                checkedId = checkedIds.get(0);
                Chip chip = group.findViewById(checkedId);
                chip.setChipBackgroundColor(ColorStateList.valueOf(selectedColor));

                if (checkedId == R.id.category_chip) {
                    binding.rvCategories.setAdapter(categoryAdapter);
                    binding.rvCategories.setLayoutManager(new GridLayoutManager(getContext(), 2));
                    presenter.getCategories();
                } else if (checkedId == R.id.ingredient_chip) {
                    binding.rvCategories.setAdapter(ingredientAdapter);
                    binding.rvCategories.setLayoutManager(new GridLayoutManager(getContext(), 3));
                    presenter.getIngredients();
                } else if (checkedId == R.id.country_chip) {
                    binding.rvCategories.setAdapter(foodAdapter);
                    binding.rvCategories.setLayoutManager(new GridLayoutManager(getContext(), 3));
                    presenter.getAreas();
                }
            }
        });
    }

    private void setupSearch() {
        binding.searchTxt.addTextChangedListener(new TextWatcher() {
            @Override public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override public void afterTextChanged(Editable s) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterData();
            }
        });
    }
    private void observeNetwork() {
        compositeDisposable.add(
                com.example.utils.NetworkUtil.observeNetwork(requireContext())
                        .distinctUntilChanged()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(connected -> {
                            isConnected = connected;

                            if (!connected) {
                                showNoInternet("No Internet Connection");
                            } else {
                                hideNetworkErrorOverlay();
                                resetByChip();
                            }
                        }, throwable -> {
                            Log.e("SearchFragment", "Network error", throwable);
                        })
        );
    }

    private void resetByChip() {
        if (checkedId == R.id.category_chip) {
            binding.rvCategories.setLayoutManager(new GridLayoutManager(getContext(), 2));
            binding.rvCategories.setAdapter(categoryAdapter);
            presenter.getCategories();
        } else if (checkedId == R.id.ingredient_chip) {
            binding.rvCategories.setLayoutManager(new GridLayoutManager(getContext(), 3));
            binding.rvCategories.setAdapter(ingredientAdapter);
            presenter.getIngredients();
        } else if (checkedId == R.id.country_chip) {
            binding.rvCategories.setLayoutManager(new GridLayoutManager(getContext(), 3));
            binding.rvCategories.setAdapter(foodAdapter);
            presenter.getAreas();
        }else {
            binding.rvCategories.setLayoutManager(new GridLayoutManager(getContext(), 2));
            binding.rvCategories.setAdapter(categoryAdapter);
            presenter.getCategories();

        }
    }

    @Override
    public void showData(List data) {
        if (data == null || data.isEmpty()) return;

        if (data.get(0) instanceof Category) {
            allCategories.clear();
            for (Object o : data) allCategories.add((Category)o);
            filterData();
        } else if (data.get(0) instanceof Ingredient) {
            allIngredients.clear();
            for (Object o : data) allIngredients.add((Ingredient)o);
            filterData();
        } else if (data.get(0) instanceof Area) {
            allAreas.clear();
            for (Object o : data) allAreas.add((Area)o);
            filterData();
        } else {
            filteredMealsAdapter.setMeals(data);
        }
    }

    private void filterData() {
        String query = binding.searchTxt.getText().toString().trim().toLowerCase();

        if (checkedId == R.id.category_chip) {
            List<Category> filtered = new ArrayList<>();
            for (Category c : allCategories) {
                if (query.isEmpty() || c.getTitle().toLowerCase().contains(query))
                    filtered.add(c);
            }
            categoryAdapter.setCategories(filtered);
        } else if (checkedId == R.id.ingredient_chip) {
            List<Ingredient> filtered = new ArrayList<>();
            for (Ingredient i : allIngredients) {
                if (query.isEmpty() || i.getTitle().toLowerCase().contains(query))
                    filtered.add(i);
            }
            ingredientAdapter.setIngredientList(filtered);
        } else if (checkedId == R.id.country_chip) {
            List<Area> filtered = new ArrayList<>();
            for (Area a : allAreas) {
                if (query.isEmpty() || a.getStrArea().toLowerCase().contains(query))
                    filtered.add(a);
            }
            foodAdapter.setFoodList(filtered);
        }
    }


    @Override
    public void showError(String message) {
        Log.d("SearchFragment", "Error: " + message);
        showNetworkErrorOverlay(message);
    }

    @Override
    public void showNoInternet(String message) {
        Log.d("SearchFragment", "NoInternet: " + message);
        showNetworkErrorOverlay(message);
    }

    @Override public void showLoading() {}
    @Override public void hideLoading() {}

    @Override
    public void goToMealDetails(Meal meal) {
        Intent intent = new Intent(getActivity(), MealDetailsActivity.class);
        intent.putExtra("MEAL_KEY", meal);
        startActivity(intent);
    }

    @Override
    public void onCardClick(String query) {
        if (checkedId == -1) return;

        if (!isConnected) {
            showNoInternet("Check your internet connection");
            return;
        }
        binding.rvCategories.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.rvCategories.setAdapter(filteredMealsAdapter);

        if (checkedId == R.id.category_chip)
            presenter.getFilteredMealsByCategory(query);
        else if (checkedId == R.id.ingredient_chip)
            presenter.getFilteredMealsByIngredient(query);
        else if (checkedId == R.id.country_chip)
            presenter.getFilteredMealsByArea(query);
    }

    @Override
    public void onMealCardClick(String mealId) {
        if (!isConnected) {
            showNoInternet("Check your internet connection");
            return;
        }
        presenter.getMealById(mealId);
    }
}
