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
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodie.MealDetailsActivity;
import com.example.foodie.R;
import com.example.foodie.data.home.model.response.Meal;
import com.example.foodie.data.search.model.Area;
import com.example.foodie.data.search.model.Category;
import com.example.foodie.data.search.model.Ingredient;
import com.example.foodie.presentation.search.presenter.SearchPresenter;
import com.example.foodie.presentation.search.presenter.SearchPresenterImpl;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;

public class SearchFragment extends Fragment implements SearchView, onCardClickListener, OnMealCardListener {
    FoodAdapter foodAdapter;
    IngredientAdapter ingredientAdapter;
    List<Area> foodList;
    private ChipGroup chipGroup;
    private RecyclerView rvCategories;
    private SearchPresenter presenter;
    private CategoriesMealsAdapter adapter;
    private FilteredMealsAdapter filteredMealsAdapter;
    private int checkedId = -1;
    private EditText searchText;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SearchPresenterImpl(this);
        adapter = new CategoriesMealsAdapter(this);
        foodAdapter = new FoodAdapter(this);
        ingredientAdapter = new IngredientAdapter(this);
        filteredMealsAdapter = new FilteredMealsAdapter(this);

        presenter.getCategories();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        chipGroup = view.findViewById(R.id.chip_group);
        rvCategories = view.findViewById(R.id.rvCategories);
        searchText = view.findViewById(R.id.search_txt);
        rvCategories.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rvCategories.setAdapter(adapter);

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                String query = s.toString().trim();

                if (checkedId == -1) return;

                if (query.isEmpty()) {
                    resetByChip();
                    return;
                }

                rvCategories.setLayoutManager(new LinearLayoutManager(getContext()));
                rvCategories.setAdapter(filteredMealsAdapter);
                if (query.length() < 2) return;
                if (checkedId == R.id.category_chip) {
                    presenter.getFilteredMealsByCategory(query);

                } else if (checkedId == R.id.ingredient_chip) {
                    presenter.getFilteredMealsByIngredient(query);

                } else if (checkedId == R.id.country_chip) {
                    presenter.getFilteredMealsByArea(query);
                }
            }
        });


        chipGroup.setOnCheckedStateChangeListener(new ChipGroup.OnCheckedStateChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull ChipGroup group, @NonNull List<Integer> checkedIds) {

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

                    // Handle each chip
                    if (checkedId == R.id.category_chip) {
                        Log.d("Chip", "Category chip selected");
                        rvCategories.setAdapter(adapter);
                        rvCategories.setLayoutManager(new GridLayoutManager(getContext(), 2));
                    } else if (checkedId == R.id.ingredient_chip) {
                        Log.d("Chip", "Ingredient chip selected");
                        rvCategories.setAdapter(ingredientAdapter);
                        presenter.getIngredients();
                        rvCategories.setLayoutManager(new GridLayoutManager(getContext(), 3));
                    } else if (checkedId == R.id.country_chip) {
                        Log.d("Chip", "Country chip selected");
                        rvCategories.setAdapter(foodAdapter);
                        rvCategories.setLayoutManager(new GridLayoutManager(getContext(), 3));
                        presenter.getAreas();
                    }
                } else {

                    Log.d("Chip", "No chip selected");
                }
            }
        });


    }


    private void resetByChip() {

        if (checkedId == R.id.category_chip) {
            rvCategories.setLayoutManager(new GridLayoutManager(getContext(), 2));
            rvCategories.setAdapter(adapter);
            presenter.getCategories();

        } else if (checkedId == R.id.ingredient_chip) {
            rvCategories.setLayoutManager(new GridLayoutManager(getContext(), 3));
            rvCategories.setAdapter(ingredientAdapter);
            presenter.getIngredients();

        } else if (checkedId == R.id.country_chip) {
            rvCategories.setLayoutManager(new GridLayoutManager(getContext(), 3));
            rvCategories.setAdapter(foodAdapter);
            presenter.getAreas();
        }
    }

    @Override
    public void showData(List data) {
        if (data == null || data.isEmpty()) {
            Log.d("SearchFragment", "No data returned");
            return;
        }
        if (data.get(0) instanceof Category) adapter.setCategories(data);
        else if (data.get(0) instanceof Area) foodAdapter.setFoodList(data);
        else if (data.get(0) instanceof Ingredient) ingredientAdapter.setIngredientList(data);
        else filteredMealsAdapter.setMeals(data);
    }

    @Override
    public void showError(String message) {

    }

    @Override
    public void showNoInternet(String message) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void goToMealDetails(Meal meal) {
        Intent intent = new Intent(getActivity(), MealDetailsActivity.class);
        intent.putExtra("MEAL_KEY", meal);
        startActivity(intent);
    }

    @Override
    public void onCardClick(String query) {
        if (checkedId == -1) return;

        rvCategories.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCategories.setAdapter(filteredMealsAdapter);

        if (checkedId == R.id.category_chip) presenter.getFilteredMealsByCategory(query);
        else if (checkedId == R.id.ingredient_chip) presenter.getFilteredMealsByIngredient(query);
        else if (checkedId == R.id.country_chip) presenter.getFilteredMealsByArea(query);
        else {
        }

    }

    @Override
    public void onMealCardClick(String mealId) {
        presenter.getMealById(mealId);
    }
}
