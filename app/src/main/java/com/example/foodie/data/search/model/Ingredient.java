package com.example.foodie.data.search.model;

import com.google.gson.annotations.SerializedName;

public class Ingredient {
    @SerializedName("idIngredient")
    private String id;
    @SerializedName("strIngredient")
    private String title;
    @SerializedName("strDescription")
    private String description;

    @SerializedName("strThumb")
    private String image;

    public Ingredient(String description, String id, String image, String title) {
        this.description = description;
        this.id = id;
        this.image = image;
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
