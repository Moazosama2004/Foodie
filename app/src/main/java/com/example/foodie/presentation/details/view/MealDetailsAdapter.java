package com.example.foodie.presentation.details.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodie.R;
import com.example.foodie.data.home.model.MealIngredient;

import java.util.ArrayList;
import java.util.List;

public class MealDetailsAdapter extends RecyclerView.Adapter<MealDetailsAdapter.MealDetailsViewHolder>{
    private List<MealIngredient> ingredientList = new ArrayList<>();

    public void setIngredientList(List<MealIngredient> ingredientList) {
        this.ingredientList = ingredientList;
        notifyDataSetChanged();

    }

    @NonNull
    @Override
    public MealDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MealDetailsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food,parent ,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MealDetailsViewHolder holder, int position) {
        holder.foodName.setText(ingredientList.get(position).getName());
//        holder.foodImage.setImageResource(ingredientList.get(position).getImage());
        Glide.with(holder.itemView.getContext())
                .load(ingredientList.get(position).getImageUrl())
                .into(holder.foodImage);
    }

    @Override
    public int getItemCount() {
        return ingredientList != null ? ingredientList.size() : 0 ;
    }

    public class MealDetailsViewHolder extends RecyclerView.ViewHolder{
        ImageView foodImage;
        TextView foodName;
        public MealDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            foodImage = itemView.findViewById(R.id.food_image);
            foodName = itemView.findViewById(R.id.food_name);
        }
    }
}
