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
import com.example.foodie.data.search.model.Category;

import java.util.ArrayList;
import java.util.List;

public class CategoriesMealsAdapter extends RecyclerView.Adapter<CategoriesMealsAdapter.CategoryViewHolder> {

    private List<Category> categories = new ArrayList<>();
    private final onCardClickListener onCardClickListener;

    public CategoriesMealsAdapter(onCardClickListener onCardClickListener) {
        this.onCardClickListener = onCardClickListener;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public CategoriesMealsAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CategoriesMealsAdapter.CategoryViewHolder holder, int position) {
        Category category = categories.get(position);
        holder.categoryName.setText(category.getTitle());
        Glide.with(holder.itemView.getContext())
                .load(category.getImage())
                .into(holder.categoryImage);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCardClickListener.onCardClick(category.getTitle());
            }
        });

    }

    @Override
    public int getItemCount() {
        return categories != null ? categories.size() : 0;
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        ImageView categoryImage;
        TextView categoryName;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryImage = itemView.findViewById(R.id.imgCategory);
            categoryName = itemView.findViewById(R.id.txtCategory);
            Glide.with(itemView.getContext())
                    .load(categoryImage)
                    .centerCrop()
                    .into(categoryImage);
        }
    }
}
