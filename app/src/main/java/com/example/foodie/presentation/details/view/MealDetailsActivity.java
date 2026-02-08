package com.example.foodie;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodie.data.home.model.response.Meal;
import com.example.foodie.presentation.details.presenter.DetailsPresenterImpl;
import com.example.foodie.presentation.details.view.DetailsView;
import com.example.foodie.presentation.details.view.MealDetailsAdapter;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.Calendar;
import java.util.Locale;

public class MealDetailsActivity extends AppCompatActivity implements DetailsView {

    private RecyclerView recyclerView;
    private MealDetailsAdapter adapter;
    private Meal meal;
    private ImageView imgMeal;
    private TextView tvMealName;
    private TextView tvMealType;
    private TextView tvMealCountry;
    private TextView tvInstructions;
    private ImageView addToFav;
    private DetailsPresenterImpl presenter;
    private ImageView addToCalender;
    private YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_details);

        // Initialize presenter
        presenter = new DetailsPresenterImpl(getApplicationContext(), this);

        // Initialize adapter and views
        adapter = new MealDetailsAdapter();
        recyclerView = findViewById(R.id.rv_ingredients);
        recyclerView.setAdapter(adapter);
        imgMeal = findViewById(R.id.img_meal);
        tvMealName = findViewById(R.id.tv_meal_name);
        tvMealType = findViewById(R.id.tv_meal_type);
        addToFav = findViewById(R.id.add_to_fav);
        tvMealCountry = findViewById(R.id.tv_meal_country);
        tvInstructions = findViewById(R.id.tv_instructions);
        addToCalender = findViewById(R.id.add_to_calender);
        youTubePlayerView = findViewById(R.id.youtubePlayerView);

        // Attach lifecycle (IMPORTANT)
        getLifecycle().addObserver(youTubePlayerView);

        // Get meal from intent
        meal = getIntent().getParcelableExtra("MEAL_KEY");

        // Debug log with null check
        if (meal != null) {
            Log.d("MealDetailsActivity", "Meal received: " + meal);
        } else {
            Log.e("MealDetailsActivity", "Meal is NULL from intent!");
            // Show error and finish activity
            Toast.makeText(this, "Failed to load meal details", Toast.LENGTH_SHORT).show();
            finish();
            return; // Exit early
        }

        // Setup FAB click listener
        addToFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MealDetailsActivity", "FAB Clicked!");
                if (meal != null) {
                    Log.d("MealDetailsActivity", "Adding to favorites: " + meal.getStrMeal());
                    presenter.saveMealLocal(meal);
                    presenter.saveMealRemote(meal);
                    Toast.makeText(MealDetailsActivity.this, "Added to favorites!", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("MealDetailsActivity", "Cannot add to favorites - meal is null");
                    Toast.makeText(MealDetailsActivity.this, "Error: Meal not found", Toast.LENGTH_SHORT).show();
                }
            }
        });

        addToCalender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("MealDetailsActivity", " addToCalender Clicked!");

                // GET CURRENT DATE
                final Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        MealDetailsActivity.this,
                        (view, selectedYear, selectedMonth, selectedDay) -> {

                            String selectedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay);

                            if (meal != null) {
                                Log.d("MealDetailsActivity", "Adding to Calender: " + meal.getStrMeal());
                                presenter.addToCalender(meal, selectedDate);
                                Toast.makeText(MealDetailsActivity.this, "Added to Calender!", Toast.LENGTH_SHORT).show();
                            } else {
                                Log.e("MealDetailsActivity", "Cannot add to Calender - meal is null");
                                Toast.makeText(MealDetailsActivity.this, "Error: Meal not found", Toast.LENGTH_SHORT).show();
                            }
                            System.out.println("Selected Date: " + selectedDate);
                        },
                        year, month, day
                );

                datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis() - 1000);


                calendar.add(Calendar.DAY_OF_YEAR, 7);
                datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());

                datePickerDialog.show();


            }
        });

        // Load meal data
        if (meal != null) {
            Log.d("MealDetailsActivity", "Loading meal data");
            Glide.with(this)
                    .load(meal.getStrMealThumb())
                    .placeholder(R.drawable.meal_of_the_day)
                    .into(imgMeal);
            tvMealName.setText(meal.getStrMeal());
            tvMealType.setText(meal.getStrCategory());
            tvMealCountry.setText(meal.getStrArea());
            tvInstructions.setText(meal.getStrInstructions());


            // Load YouTube video if exists
            if (meal.getStrYoutube() != null && !meal.getStrYoutube().isEmpty()) {

                youTubePlayerView.setVisibility(View.VISIBLE);

                youTubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady(@NonNull YouTubePlayer youTubePlayer) {

                        // Extract video ID from URL
                        String videoId = extractVideoId(meal.getStrYoutube());

                        if (videoId != null) {
                            youTubePlayer.cueVideo(videoId, 0);
                        }
                    }
                });

                Log.d("VIDEO_URL", meal.getStrYoutube());
                Log.d("VIDEO_ID", extractVideoId(meal.getStrYoutube()));

            } else {
                // Hide video if no youtube link
                youTubePlayerView.setVisibility(View.GONE);
            }

            // Check if ingredients list is not null
            if (meal.getIngredients() != null) {
                adapter.setIngredientList(meal.getIngredients());
                Log.d("MealDetailsActivity", "Ingredients count: " + meal.getIngredients().size());
            } else {
                Log.w("MealDetailsActivity", "Ingredients list is null");
            }
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

    private String extractVideoId(String youtubeUrl) {
        if (youtubeUrl == null) return null;

        if (youtubeUrl.contains("watch?v=")) {
            return youtubeUrl.substring(youtubeUrl.indexOf("watch?v=") + 8);
        } else if (youtubeUrl.contains("youtu.be/")) {
            return youtubeUrl.substring(youtubeUrl.lastIndexOf("/") + 1);
        }
        return null;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (youTubePlayerView != null) {
            youTubePlayerView.release();
        }
    }
}