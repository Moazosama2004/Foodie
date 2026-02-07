package com.example.foodie.utils.firebase.storage;

import com.example.foodie.data.home.model.response.Meal;
import com.example.foodie.utils.services.MealStorage;
import com.example.foodie.utils.services.StorageCallback;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirestoreMealStorage implements MealStorage {
    private final FirebaseFirestore firestore;

    public FirestoreMealStorage() {
        firestore = FirebaseFirestore.getInstance();
    }


    @Override
    public void saveMeal(Meal meal, StorageCallback callback) {
        firestore.collection("meals")
                .document(meal.getIdMeal())
                .set(meal)
                .addOnSuccessListener(unused ->
                        callback.onSuccess()
                )
                .addOnFailureListener(e ->
                        callback.onError(e.getMessage())
                );
    }

    @Override
    public void deleteMeal(String id, StorageCallback callback) {
        firestore.collection("meals")
                .document(id)
                .delete()
                .addOnSuccessListener(unused ->
                        callback.onSuccess()
                )
                .addOnFailureListener(e ->
                        callback.onError(e.getMessage())
                );
    }
}
