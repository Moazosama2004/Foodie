package com.example.foodie.data.home.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.text.Normalizer;

public class MealIngredient implements Parcelable {

    private final String name;
    private final String measure;

    public MealIngredient(String name, String measure) {
        this.name = name != null ? name.trim() : "";
        this.measure = measure != null ? measure.trim() : "";
    }

    protected MealIngredient(Parcel in) {
        name = in.readString();
        measure = in.readString();
    }

    public static final Creator<MealIngredient> CREATOR = new Creator<MealIngredient>() {
        @Override
        public MealIngredient createFromParcel(Parcel in) {
            return new MealIngredient(in);
        }

        @Override
        public MealIngredient[] newArray(int size) {
            return new MealIngredient[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(measure);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String getName() {
        return name;
    }

    public String getMeasure() {
        return measure;
    }

    public String getImageUrl() {
        if (name.isEmpty()) return "";
        String normalized = Normalizer.normalize(name, Normalizer.Form.NFD)
                .toLowerCase()
                .replace(" ", "_");
        return "https://www.themealdb.com/images/ingredients/" + normalized + ".png";
    }
}
