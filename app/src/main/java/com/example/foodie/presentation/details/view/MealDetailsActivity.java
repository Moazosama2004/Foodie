package com.example.foodie.presentation.details.view;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Lifecycle;

import com.bumptech.glide.Glide;
import com.example.foodie.R;
import com.example.foodie.data.home.model.response.Meal;
import com.example.foodie.databinding.ActivityMealDetailsBinding;
import com.example.foodie.presentation.auth.view.AuthActivity;
import com.example.foodie.presentation.details.presenter.DetailsPresenterImpl;
import com.example.foodie.utils.CustomAlertDialog;
import com.example.foodie.utils.sharedprefs.SharedPrefsManager;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;

import java.util.Calendar;
import java.util.Locale;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MealDetailsActivity extends AppCompatActivity implements DetailsView, CustomAlertDialog.OnConfirmationListener {

    private ActivityMealDetailsBinding binding;
    private DetailsPresenterImpl presenter;
    private Meal meal;
    private MealDetailsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMealDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Presenter
        presenter = new DetailsPresenterImpl(getApplicationContext(), this);

        // Adapter
        adapter = new MealDetailsAdapter();
        binding.rvIngredients.setAdapter(adapter);

        getLifecycle().addObserver(binding.youtubePlayerView);

        meal = getIntent().getParcelableExtra("MEAL_KEY");
        if (meal == null) {
            Toast.makeText(this, "Failed to load meal details", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        setupMealViews();
        setupClicks();
    }

    private void setupMealViews() {
        Glide.with(this)
                .load(meal.getStrMealThumb())
                .placeholder(R.drawable.meal_of_the_day)
                .into(binding.imgMeal);

        binding.tvMealName.setText(meal.getStrMeal());
        binding.tvMealType.setText(meal.getStrCategory());
        binding.tvMealCountry.setText(meal.getStrArea());
        binding.tvInstructions.setText(meal.getStrInstructions());

        // Ingredients
        if (meal.getIngredients() != null) {
            adapter.setIngredientList(meal.getIngredients());
            Log.d("MealDetailsActivity", "Ingredients count: " + meal.getIngredients().size());
        }

        String youtubeUrl = meal.getStrYoutube();
        if (youtubeUrl != null && !youtubeUrl.isEmpty()) {
            binding.youtubePlayerView.setVisibility(android.view.View.VISIBLE);
            binding.youtubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                    String videoId = extractVideoId(youtubeUrl);
                    if (videoId != null) {
                        youTubePlayer.cueVideo(videoId, 0);
                    }
                }
            });
        } else {
            binding.youtubePlayerView.setVisibility(android.view.View.GONE);
        }
    }

    @SuppressLint("CheckResult")
    private void setupClicks() {
        binding.addToFav.setOnClickListener(v -> {
            SharedPrefsManager.getInstance(this)
                    .isLoggedIn()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            (isLoggedIn -> {
                                if (isLoggedIn) {
                                    if (meal != null) {
                                        presenter.saveMealLocal(meal);
                                        presenter.saveMealRemote(meal)
                                                .subscribe(
                                                        () -> Toast.makeText(this, "Added to favorites remotely!", Toast.LENGTH_SHORT).show(),
                                                        throwable -> Toast.makeText(this, "Failed to save remotely: " + throwable.getMessage(), Toast.LENGTH_SHORT).show()
                                                );
                                        Toast.makeText(this, "Added to favorites locally!", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(this, "Error: Meal not found", Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    CustomAlertDialog.showGuestModeAlert(this, this);
                                }
                            }),
                            throwable -> Toast.makeText(this, "Error: " + throwable.getMessage(), Toast.LENGTH_SHORT).show()
                    );
        });


        binding.addToCalender.setOnClickListener(v -> {
            SharedPrefsManager.getInstance(this)
                    .isLoggedIn()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(isLoggedIn -> {
                        if (isLoggedIn) {
                            final Calendar calendar = Calendar.getInstance();
                            int year = calendar.get(Calendar.YEAR);
                            int month = calendar.get(Calendar.MONTH);
                            int day = calendar.get(Calendar.DAY_OF_MONTH);

                            DatePickerDialog datePickerDialog = new DatePickerDialog(
                                    this,
                                    (view, selectedYear, selectedMonth, selectedDay) -> {
                                        String selectedDate = String.format(Locale.getDefault(),
                                                "%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay);
                                        if (meal != null) {
                                            presenter.addToCalender(meal, selectedDate)
                                                    .subscribe(
                                                            () -> Toast.makeText(this, "Added to Calendar!", Toast.LENGTH_SHORT).show(),
                                                            throwable -> Toast.makeText(this, "Failed to add to Calendar", Toast.LENGTH_SHORT).show()
                                                    );
                                        }
                                        Log.d("MealDetailsActivity", "Selected Date: " + selectedDate);
                                    },
                                    year, month, day
                            );

                            datePickerDialog.getDatePicker().setMinDate(calendar.getTimeInMillis() - 1000);
                            calendar.add(Calendar.DAY_OF_YEAR, 7);
                            datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());

                            datePickerDialog.show();
                        } else {
                            CustomAlertDialog.showGuestModeAlert(this, this);
                        }
                    }, throwable -> {
                        Toast.makeText(this, "Error: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    });
        });

    }

    private String extractVideoId(String youtubeUrl) {
        if (youtubeUrl.contains("watch?v=")) {
            return youtubeUrl.substring(youtubeUrl.indexOf("watch?v=") + 8);
        } else if (youtubeUrl.contains("youtu.be/")) {
            return youtubeUrl.substring(youtubeUrl.lastIndexOf("/") + 1);
        }
        return null;
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
        Log.d("DetailsPresenter", "hhhhhhh");
    }

    @Override
    public void showError(Throwable throwable) {

    }

    @Override
    public void showGuestDialoug() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (binding.youtubePlayerView != null && getLifecycle().getCurrentState().isAtLeast(Lifecycle.State.CREATED)) {
            binding.youtubePlayerView.release();
        }
        binding = null;
    }

    @Override
    public void onConfirm() {
        Intent intent = new Intent(this, AuthActivity.class);
        startActivity(intent);
        finish();
    }
}
