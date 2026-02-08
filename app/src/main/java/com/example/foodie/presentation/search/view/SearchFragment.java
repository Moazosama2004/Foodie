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

import java.util.List;

public class SearchFragment extends Fragment implements SearchView, onCardClickListener, OnMealCardListener {
    private FragmentSearchBinding binding;
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
        binding = FragmentSearchBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.rvCategories.setLayoutManager(new GridLayoutManager(getContext(), 2));
        binding.rvCategories.setAdapter(adapter);

        binding.searchTxt.addTextChangedListener(new TextWatcher() {
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

                binding.rvCategories.setLayoutManager(new LinearLayoutManager(getContext()));
                binding.rvCategories.setAdapter(filteredMealsAdapter);
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


        binding.chipGroup.setOnCheckedStateChangeListener(new ChipGroup.OnCheckedStateChangeListener() {
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
                        binding.rvCategories.setAdapter(adapter);
                        binding.rvCategories.setLayoutManager(new GridLayoutManager(getContext(), 2));
                    } else if (checkedId == R.id.ingredient_chip) {
                        Log.d("Chip", "Ingredient chip selected");
                        binding.rvCategories.setAdapter(ingredientAdapter);
                        presenter.getIngredients();
                        binding.rvCategories.setLayoutManager(new GridLayoutManager(getContext(), 3));
                    } else if (checkedId == R.id.country_chip) {
                        Log.d("Chip", "Country chip selected");
                        binding.rvCategories.setAdapter(foodAdapter);
                        binding.rvCategories.setLayoutManager(new GridLayoutManager(getContext(), 3));
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
            binding.rvCategories.setLayoutManager(new GridLayoutManager(getContext(), 2));
            binding.rvCategories.setAdapter(adapter);
            presenter.getCategories();

        } else if (checkedId == R.id.ingredient_chip) {
            binding.rvCategories.setLayoutManager(new GridLayoutManager(getContext(), 3));
            binding.rvCategories.setAdapter(ingredientAdapter);
            presenter.getIngredients();

        } else if (checkedId == R.id.country_chip) {
            binding.rvCategories.setLayoutManager(new GridLayoutManager(getContext(), 3));
            binding.rvCategories.setAdapter(foodAdapter);
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
