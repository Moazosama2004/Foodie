package com.example.foodie.presentation.calender.view;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodie.R;
import com.example.foodie.data.calender.model.CalendarMeal;

import java.util.ArrayList;
import java.util.List;

public class CalendarMealAdapter extends RecyclerView.Adapter<CalendarMealAdapter.MealViewHolder> {

    private List<CalendarMeal> meals = new ArrayList<>();
    private final OnMealClickListener onMealClickListener;

    public CalendarMealAdapter(OnMealClickListener onMealClickListener) {
        this.onMealClickListener = onMealClickListener;
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.calender_meal_item, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        CalendarMeal meal = meals.get(position);
        holder.nameText.setText(meal.getMealName());

        // Load image if available using Glide
        if (meal.getMealImage() != null && !meal.getMealImage().isEmpty()) {
            Glide.with(holder.itemView.getContext())
                    .load(meal.getMealImage())
                    .into(holder.mealImage);
        } else {
//            holder.mealImage.setImageResource(R.drawable.placeholder_image); // placeholder
        }

        holder.itemView.setOnClickListener(v -> {
            onMealClickListener.onMealClick(meal.getMealId());
        });
    }

    @Override
    public int getItemCount() {
        return meals == null ? 0 : meals.size();
    }

    public void setMeals(List<CalendarMeal> meals) {
        this.meals = meals;
        notifyDataSetChanged();
    }

    static class MealViewHolder extends RecyclerView.ViewHolder {
        TextView nameText;
        ImageView mealImage;

        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            mealImage = itemView.findViewById(R.id.img_details);
            nameText = itemView.findViewById(R.id.name_details);
        }
    }
}
