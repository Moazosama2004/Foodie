package com.example.foodie.presentation.details.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodie.R;
import com.example.foodie.data.core.model.FavMeal;
import com.example.foodie.data.home.model.response.Meal;
import com.example.foodie.presentation.details.presenter.DetailsPresenter;
import com.example.foodie.presentation.details.presenter.DetailsPresenterImpl;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MealDetailsActivity extends AppCompatActivity implements DetailsView {

    private RecyclerView recyclerView;
    private MealDetailsAdapter adapter;
    private Meal meal;
    private ImageView imgMeal;
    private TextView tvMealName;
    private TextView tvMealType;
    private TextView tvMealCountry;
    private TextView tvInstructions;
    private FloatingActionButton addToFav;

    private DetailsPresenter presenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_meal_details);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        presenter = new DetailsPresenterImpl(getApplicationContext() , this);
        adapter = new MealDetailsAdapter();
        recyclerView = findViewById(R.id.rv_ingredients);
        recyclerView.setAdapter(adapter);
        imgMeal = findViewById(R.id.img_meal);
        tvMealName = findViewById(R.id.tv_meal_name);
        tvMealType = findViewById(R.id.tv_meal_type);
        addToFav = findViewById(R.id.add_to_fav);
        tvMealCountry = findViewById(R.id.tv_meal_country);
        tvInstructions = findViewById(R.id.tv_instructions);

        meal = getIntent().getParcelableExtra("MEAL_KEY");
        Log.d("addToFav", meal.toString());

        addToFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("addToFav", "Clicked");
                if (meal != null) {
                    Log.d("addToFav", "added");
                    presenter.addToFav(meal);
                }else {
                    Log.d("addToFav", "addToFav meal is null");
                }
            }
        });

        if (meal != null) {
            Log.d("Meal", meal.toString());
            Glide.with(this)
                    .load(meal.getStrMealThumb())
                    .placeholder(R.drawable.meal_of_the_day)
//                    .error(R.drawable.error)
                    .into(imgMeal);
            tvMealName.setText(meal.getStrMeal());
            tvMealType.setText(meal.getStrCategory());
            tvMealCountry.setText(meal.getStrArea());
            tvInstructions.setText(meal.getStrInstructions());
            adapter.setIngredientList(meal.getIngredients());
        } else {
            Log.d("Meal", "meal is null");
        }

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
    public void onSuccess() {

    }

}