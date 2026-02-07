package com.example.foodie.presentation.fav.view;

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

public class FavouriteMealsAdapter extends RecyclerView.Adapter<FavouriteMealsAdapter.FavouriteMealsViewHolder> {

    private List<Meal> favouriteMeals = new ArrayList<>();
    private final OnDeleteClickListener onDeleteClickListener;

    private final FavView favView;

    public FavouriteMealsAdapter(FavView favView, OnDeleteClickListener onDeleteClickListener) {
        this.onDeleteClickListener = onDeleteClickListener;
        this.favView = favView;
    }

    public void setFavouriteMeals(List<Meal> favouriteMeals) {
        this.favouriteMeals = favouriteMeals;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavouriteMealsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavouriteMealsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favourite, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteMealsViewHolder holder, int position) {
        holder.mealName.setText(favouriteMeals.get(position).getStrMeal());
        holder.removeFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDeleteClickListener.onDeleteClick(favouriteMeals.get(position));
            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favView.goToDetails(favouriteMeals.get(position));
            }
        });
        Glide.with(holder.itemView.getContext())
                .load(favouriteMeals.get(position).getStrMealThumb())
                .into(holder.mealImage);
    }


    @Override
    public int getItemCount() {
        return favouriteMeals == null ? 0 : favouriteMeals.size();
    }

    public class FavouriteMealsViewHolder extends RecyclerView.ViewHolder {
        TextView mealName;
        ImageView mealImage;
        ImageView removeFav;


        public FavouriteMealsViewHolder(@NonNull View itemView) {
            super(itemView);
            mealName = itemView.findViewById(R.id.tv_fav_food_name);
            mealImage = itemView.findViewById(R.id.img_fav_food);
            removeFav = itemView.findViewById(R.id.btn_remove_fav);
        }
    }
}
