package com.example.foodie.utils;

import android.util.Log;

import com.example.foodie.data.home.model.response.Meal;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirebaseStorageImpl implements  StorageService{
    private final FirebaseFirestore firestore;

    public FirebaseStorageImpl() {
        firestore = FirebaseFirestore.getInstance();
    }

    @Override
    public void saveMeal(Meal meal) {
        firestore.collection("meals")
                .document(meal.getIdMeal())
                .set(meal)
                .addOnSuccessListener(unused ->
                        Log.d("FIREBASE", "Meal saved successfully")
                )
                .addOnFailureListener(e ->
                        Log.e("FIREBASE", "Error: " + e.getMessage())
                );
    }

    @Override
    public void deleteMeal(String id) {
        firestore.collection("meals")
                .document(id)
                .delete()
                .addOnSuccessListener(unused ->
                        Log.d("FIREBASE", "Meal deleted successfully")
                )
                .addOnFailureListener(e ->
                        Log.e("FIREBASE", "Delete error: " + e.getMessage())
                );
    }
}
