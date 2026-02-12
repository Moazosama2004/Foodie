package com.example.foodie.presentation.search.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodie.R;
import com.example.foodie.data.search.model.FilteredMeal;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class FilteredMealsAdapter extends RecyclerView.Adapter<FilteredMealsAdapter.MealViewHolder> {

    OnMealCardListener onMealCardListener;
    private List<FilteredMeal> meals = new ArrayList<>();


    public FilteredMealsAdapter(OnMealCardListener onMealCardListener) {
        this.onMealCardListener = onMealCardListener;
    }

    public void setMeals(List<FilteredMeal> meals) {
        this.meals = meals;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_filtered_meal_card, parent, false);
        return new MealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealViewHolder holder, int position) {
        FilteredMeal meal = meals.get(position);

        holder.txtMealName.setText(meal.getStrMeal());

        Glide.with(holder.itemView.getContext())
                .load(meal.getStrMealThumb())
//                .placeholder(R.drawable.placeholder)
                .into(holder.imgMeal);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMealCardListener.onMealCardClick(meal.getIdMeal());
            }
        });
    }

    @Override
    public int getItemCount() {
        return meals.size();
    }

    static class MealViewHolder extends RecyclerView.ViewHolder {

        ImageView imgMeal;
        TextView txtMealName;


        public MealViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMeal = itemView.findViewById(R.id.imgMeal);
            txtMealName = itemView.findViewById(R.id.txtMealName);
        }
    }
}

