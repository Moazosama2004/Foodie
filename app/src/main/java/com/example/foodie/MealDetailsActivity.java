package com.example.foodie;

import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodie.data.home.model.response.Meal;

public class MealDetailsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MealDetailsAdapter adapter;
    private Meal meal;
    private ImageView imgMeal;
    private TextView tvMealName;
    private TextView tvMealType;
    private TextView tvMealCountry;
    private TextView tvInstructions;


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

        imgMeal = findViewById(R.id.img_meal);
        tvMealName = findViewById(R.id.tv_meal_name);
        tvMealType = findViewById(R.id.tv_meal_type);

        tvMealCountry = findViewById(R.id.tv_meal_country);

        tvInstructions = findViewById(R.id.tv_instructions);

        Meal meal = getIntent().getParcelableExtra("MEAL_KEY");

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
        } else {
            Log.d("Meal", "meal is null");
        }
        adapter = new MealDetailsAdapter();
        recyclerView = findViewById(R.id.rv_ingredients);
        recyclerView.setAdapter(adapter);
    }
}