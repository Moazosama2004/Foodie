//package com.example.foodie.data.core.model;
//
//import androidx.annotation.NonNull;
//import androidx.room.Entity;
//import androidx.room.PrimaryKey;
//
//@Entity(tableName = "fav_meals")
//public class FavMeal {
//
//    @PrimaryKey
//    @NonNull
//    private String idMeal;
//
//    private String name;
//    private String image;
//
//    public FavMeal(@NonNull String idMeal, String name, String image) {
//        this.idMeal = idMeal;
//        this.name = name;
//        this.image = image;
//    }
//
//    @NonNull
//    public String getIdMeal() {
//        return idMeal;
//    }
//
//    public void setIdMeal(@NonNull String idMeal) {
//        this.idMeal = idMeal;
//    }
//
//    public String getImage() {
//        return image;
//    }
//
//    public void setImage(String image) {
//        this.image = image;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//}