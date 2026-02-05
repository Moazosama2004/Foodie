package com.example.foodie.presentation.home.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodie.R;
import com.example.foodie.data.home.model.response.Meal;

import java.util.ArrayList;
import java.util.List;

public class PopularMealsAdapter extends RecyclerView.Adapter<PopularMealsAdapter.PopularMealViewHolder> {

    private List<Meal> meals = new ArrayList<>();
    private OnMealClickListener onMealClickListener;

    public void setPopularMeals(List<Meal> meals) {
        this.meals = meals;
        notifyDataSetChanged();
    }

    public void setOnMealClickListener(OnMealClickListener listener) {
        this.onMealClickListener = listener;
    }

    @NonNull
    @Override
    public PopularMealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_popular_meal, parent, false);
        return new PopularMealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularMealViewHolder holder, int position) {
        Meal meal = meals.get(position);
        holder.mealName.setText(meal.getStrMeal());
        Glide.with(holder.itemView.getContext())
                .load(meal.getStrMealThumb())
                .into(holder.mealImage);

        holder.itemView.setOnClickListener(v -> {
            if (onMealClickListener != null) {
                onMealClickListener.onMealClick(meal);
            }
        });
    }

    @Override
    public int getItemCount() {
        return meals != null ? meals.size() : 0;
    }

    static class PopularMealViewHolder extends RecyclerView.ViewHolder {
        ImageView mealImage;
        TextView mealName;

        public PopularMealViewHolder(@NonNull View itemView) {
            super(itemView);
            mealImage = itemView.findViewById(R.id.imageMeal);
            mealName = itemView.findViewById(R.id.textMealName);
            Glide.with(itemView.getContext())
                    .load(mealImage)
                    .centerCrop()
                    .into(mealImage);
        }
    }
}
