package com.example.foodie.data.home.model.response;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.example.foodie.data.home.model.MealIngredient;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Meal implements Parcelable {

    @SerializedName("idMeal")
    private String idMeal;

    @SerializedName("strMeal")
    private String strMeal;

    @SerializedName("strMealAlternate")
    private String strMealAlternate;

    @SerializedName("strCategory")
    private String strCategory;

    @SerializedName("strArea")
    private String strArea;

    @SerializedName("strInstructions")
    private String strInstructions;

    @SerializedName("strMealThumb")
    private String strMealThumb;

    @SerializedName("strTags")
    private String strTags;

    @SerializedName("strYoutube")
    private String strYoutube;

    // Ingredients
    @SerializedName("strIngredient1")
    private String strIngredient1;
    @SerializedName("strIngredient2")
    private String strIngredient2;
    @SerializedName("strIngredient3")
    private String strIngredient3;
    @SerializedName("strIngredient4")
    private String strIngredient4;
    @SerializedName("strIngredient5")
    private String strIngredient5;
    @SerializedName("strIngredient6")
    private String strIngredient6;
    @SerializedName("strIngredient7")
    private String strIngredient7;
    @SerializedName("strIngredient8")
    private String strIngredient8;
    @SerializedName("strIngredient9")
    private String strIngredient9;
    @SerializedName("strIngredient10")
    private String strIngredient10;
    @SerializedName("strIngredient11")
    private String strIngredient11;
    @SerializedName("strIngredient12")
    private String strIngredient12;
    @SerializedName("strIngredient13")
    private String strIngredient13;
    @SerializedName("strIngredient14")
    private String strIngredient14;
    @SerializedName("strIngredient15")
    private String strIngredient15;
    @SerializedName("strIngredient16")
    private String strIngredient16;
    @SerializedName("strIngredient17")
    private String strIngredient17;
    @SerializedName("strIngredient18")
    private String strIngredient18;
    @SerializedName("strIngredient19")
    private String strIngredient19;
    @SerializedName("strIngredient20")
    private String strIngredient20;

    // Measures
    @SerializedName("strMeasure1")
    private String strMeasure1;
    @SerializedName("strMeasure2")
    private String strMeasure2;
    @SerializedName("strMeasure3")
    private String strMeasure3;
    @SerializedName("strMeasure4")
    private String strMeasure4;
    @SerializedName("strMeasure5")
    private String strMeasure5;
    @SerializedName("strMeasure6")
    private String strMeasure6;
    @SerializedName("strMeasure7")
    private String strMeasure7;
    @SerializedName("strMeasure8")
    private String strMeasure8;
    @SerializedName("strMeasure9")
    private String strMeasure9;
    @SerializedName("strMeasure10")
    private String strMeasure10;
    @SerializedName("strMeasure11")
    private String strMeasure11;
    @SerializedName("strMeasure12")
    private String strMeasure12;
    @SerializedName("strMeasure13")
    private String strMeasure13;
    @SerializedName("strMeasure14")
    private String strMeasure14;
    @SerializedName("strMeasure15")
    private String strMeasure15;
    @SerializedName("strMeasure16")
    private String strMeasure16;
    @SerializedName("strMeasure17")
    private String strMeasure17;
    @SerializedName("strMeasure18")
    private String strMeasure18;
    @SerializedName("strMeasure19")
    private String strMeasure19;
    @SerializedName("strMeasure20")
    private String strMeasure20;

    @SerializedName("strSource")
    private String strSource;

    @SerializedName("strImageSource")
    private String strImageSource;

    @SerializedName("strCreativeCommonsConfirmed")
    private String strCreativeCommonsConfirmed;

    @SerializedName("dateModified")
    private String dateModified;

    // ===== Getters =====

    public String getIdMeal() {
        return idMeal;
    }

    public String getStrMeal() {
        return strMeal;
    }

    public String getStrMealAlternate() {
        return strMealAlternate;
    }

    public String getStrCategory() {
        return strCategory;
    }

    public String getStrArea() {
        return strArea;
    }

    public String getStrInstructions() {
        return strInstructions;
    }

    public String getStrMealThumb() {
        return strMealThumb;
    }

    public String getStrTags() {
        return strTags;
    }

    public String getStrYoutube() {
        return strYoutube;
    }

    public String getStrSource() {
        return strSource;
    }

    public String getStrImageSource() {
        return strImageSource;
    }

    public String getStrCreativeCommonsConfirmed() {
        return strCreativeCommonsConfirmed;
    }

    public String getDateModified() {
        return dateModified;
    }

    // ===== Helpers =====

    public String getStrIngredient(int index) {
        switch (index) {
            case 1:
                return strIngredient1;
            case 2:
                return strIngredient2;
            case 3:
                return strIngredient3;
            case 4:
                return strIngredient4;
            case 5:
                return strIngredient5;
            case 6:
                return strIngredient6;
            case 7:
                return strIngredient7;
            case 8:
                return strIngredient8;
            case 9:
                return strIngredient9;
            case 10:
                return strIngredient10;
            case 11:
                return strIngredient11;
            case 12:
                return strIngredient12;
            case 13:
                return strIngredient13;
            case 14:
                return strIngredient14;
            case 15:
                return strIngredient15;
            case 16:
                return strIngredient16;
            case 17:
                return strIngredient17;
            case 18:
                return strIngredient18;
            case 19:
                return strIngredient19;
            case 20:
                return strIngredient20;
            default:
                return null;
        }
    }

    public String getStrMeasure(int index) {
        switch (index) {
            case 1:
                return strMeasure1;
            case 2:
                return strMeasure2;
            case 3:
                return strMeasure3;
            case 4:
                return strMeasure4;
            case 5:
                return strMeasure5;
            case 6:
                return strMeasure6;
            case 7:
                return strMeasure7;
            case 8:
                return strMeasure8;
            case 9:
                return strMeasure9;
            case 10:
                return strMeasure10;
            case 11:
                return strMeasure11;
            case 12:
                return strMeasure12;
            case 13:
                return strMeasure13;
            case 14:
                return strMeasure14;
            case 15:
                return strMeasure15;
            case 16:
                return strMeasure16;
            case 17:
                return strMeasure17;
            case 18:
                return strMeasure18;
            case 19:
                return strMeasure19;
            case 20:
                return strMeasure20;
            default:
                return null;
        }
    }

    // ===== Ingredients List (ready for UI) =====
    public List<MealIngredient> getIngredients() {
        List<MealIngredient> list = new ArrayList<>();

        for (int i = 1; i <= 20; i++) {
            String ingredient = getStrIngredient(i);
            String measure = getStrMeasure(i);

            if (ingredient != null && !ingredient.trim().isEmpty()) {
                list.add(new MealIngredient(ingredient, measure));
            }
        }
        return list;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected Meal(Parcel in) {
        idMeal = in.readString();
        strMeal = in.readString();
        strCategory = in.readString();
        strArea = in.readString();
        strInstructions = in.readString();
        strMealThumb = in.readString();
        strIngredient1 = in.readString();
        strIngredient2 = in.readString();
        strIngredient3 = in.readString();
        strMeasure1 = in.readString();
        strMeasure2 = in.readString();
        strMeasure3 = in.readString();
    }

    public static final Creator<Meal> CREATOR = new Creator<Meal>() {
        @Override
        public Meal createFromParcel(Parcel in) {
            return new Meal(in);
        }

        @Override
        public Meal[] newArray(int size) {
            return new Meal[size];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(idMeal);
        parcel.writeString(strMeal);
        parcel.writeString(strCategory);
        parcel.writeString(strArea);
        parcel.writeString(strInstructions);
        parcel.writeString(strMealThumb);
        parcel.writeString(strIngredient1);
        parcel.writeString(strIngredient2);
        parcel.writeString(strIngredient3);
        parcel.writeString(strMeasure1);
        parcel.writeString(strMeasure2);
        parcel.writeString(strMeasure3);
    }
}
