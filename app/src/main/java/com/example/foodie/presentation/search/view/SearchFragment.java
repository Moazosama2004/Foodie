package com.example.foodie.presentation.search.view;

import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodie.R;
import com.example.foodie.data.search.model.Area;
import com.example.foodie.data.search.model.Category;
import com.example.foodie.presentation.search.presenter.SearchPresenter;
import com.example.foodie.presentation.search.presenter.SearchPresenterImpl;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;

import java.util.List;

public class SearchFragment extends Fragment implements SearchView {
    FoodAdapter foodAdapter;
    IngredientAdapter ingredientAdapter;
    List<Area> foodList;
    private ChipGroup chipGroup;
    private RecyclerView rvCategories;
    private SearchPresenter presenter;
    private CategoriesMealsAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SearchPresenterImpl(this);
        adapter = new CategoriesMealsAdapter();
        foodAdapter = new FoodAdapter();
        ingredientAdapter = new IngredientAdapter();
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
        rvCategories.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rvCategories.setAdapter(adapter);

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
                    int checkedId = checkedIds.get(0);
                    Chip chip = group.findViewById(checkedId);
                    chip.setChipBackgroundColor(ColorStateList.valueOf(selectedColor));

                    // Handle each chip
                    if (checkedId == R.id.category_chip) {
                        Log.d("Chip", "Category chip selected");
                        rvCategories.setLayoutManager(new GridLayoutManager(getContext(), 2));
                        rvCategories.setAdapter(adapter);
                    } else if (checkedId == R.id.ingredient_chip) {
                        Log.d("Chip", "Ingredient chip selected");
                        presenter.getIngredients();
                        rvCategories.setLayoutManager(new GridLayoutManager(getContext(), 3));
                        rvCategories.setAdapter(ingredientAdapter);
                    } else if (checkedId == R.id.country_chip) {
                        presenter.getAreas();
                        Log.d("Chip", "Country chip selected");
                        rvCategories.setLayoutManager(new GridLayoutManager(getContext(), 3));
                        rvCategories.setAdapter(foodAdapter);
                    }
                } else {

                    Log.d("Chip", "No chip selected");
                }
            }
        });


    }

    @Override
    public void showData(List categories) {
        if (categories.get(0) instanceof Category) adapter.setCategories(categories);
        else if (categories.get(0) instanceof Area) foodAdapter.setFoodList(categories);
        else ingredientAdapter.setIngredientList(categories);
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
}
