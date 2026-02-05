package com.example.foodie.presentation.fav.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.foodie.R;

public class FavouriteMealsAdapter extends RecyclerView.Adapter<FavouriteMealsAdapter.FavouriteMealsViewHolder> {
    private String[] favouriteMeals = {
            "Beef",
            "Eggs"
    };


    @NonNull
    @Override
    public FavouriteMealsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavouriteMealsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favourite, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteMealsViewHolder holder, int position) {
        holder.mealName.setText(favouriteMeals[position]);
    }

    @Override
    public int getItemCount() {
        return favouriteMeals.length;
    }

    public class FavouriteMealsViewHolder extends RecyclerView.ViewHolder {
        TextView mealName;

        public FavouriteMealsViewHolder(@NonNull View itemView) {
            super(itemView);
            mealName = itemView.findViewById(R.id.tv_fav_food_name);
        }
    }
}
