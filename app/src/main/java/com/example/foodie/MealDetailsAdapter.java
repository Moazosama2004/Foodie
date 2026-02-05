package com.example.foodie;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodie.data.search.model.Ingredient;

import java.sql.Array;
import java.util.Arrays;
import java.util.List;

public class MealDetailsAdapter extends RecyclerView.Adapter<MealDetailsAdapter.MealDetailsViewHolder>{
    private List<Ingredient> ingredientList = Arrays.asList(
      new Ingredient("hhhhhhh" , "1" , "url" , "url") ,
      new Ingredient("hhhhhhh" , "1" , "url" , "url") ,
      new Ingredient("hhhhhhh" , "1" , "url" , "url") ,
      new Ingredient("hhhhhhh" , "1" , "url" , "url") ,
      new Ingredient("hhhhhhh" , "1" , "url" , "url") ,
      new Ingredient("hhhhhhh" , "1" , "url" , "url") ,
      new Ingredient("hhhhhhh" , "1" , "url" , "url")
    );

    public MealDetailsAdapter() {
        this.ingredientList = ingredientList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MealDetailsAdapter.MealDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MealDetailsViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_food,parent ,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MealDetailsAdapter.MealDetailsViewHolder holder, int position) {
        holder.foodName.setText(ingredientList.get(position).getTitle());
//        holder.foodImage.setImageResource(ingredientList.get(position).getImage());
        Glide.with(holder.itemView.getContext())
                .load("https://www.themealdb.com/images/media/meals/wvpsxx1468256321.jpg")
                .into(holder.foodImage);
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
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
