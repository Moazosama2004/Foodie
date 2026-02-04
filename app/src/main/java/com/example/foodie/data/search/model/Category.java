package com.example.foodie.data.search.model;

import com.google.gson.annotations.SerializedName;

public class Category {
    @SerializedName("idCategory")
    private String id;
    @SerializedName("strCategory")
    private String title;

    @SerializedName("strCategoryThumb")
    private String image;
    @SerializedName("strCategoryDescription")
    private String desc;

    public Category(String desc, String id, String image, String title) {
        this.desc = desc;
        this.id = id;
        this.image = image;
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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
