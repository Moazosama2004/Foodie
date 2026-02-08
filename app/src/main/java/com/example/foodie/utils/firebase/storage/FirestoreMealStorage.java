package com.example.foodie.utils.firebase.storage;

import com.example.foodie.data.home.model.response.Meal;
import com.example.foodie.utils.services.MealStorage;
import com.example.foodie.utils.services.StorageCallback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FirestoreMealStorage implements MealStorage {

    private final FirebaseFirestore firestore;
    private final FirebaseAuth auth;

    public FirestoreMealStorage() {
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
    }

    private String getUserId() {
        if (auth.getCurrentUser() == null) {
            throw new IllegalStateException("User not logged in");
        }
        return auth.getCurrentUser().getUid();
    }

    @Override
    public void saveMeal(Meal meal, StorageCallback callback) {
        String userId = getUserId();

        firestore.collection("users")
                .document(userId)
                .collection("meals")
                .document(meal.getIdMeal())
                .set(meal)
                .addOnSuccessListener(unused -> callback.onSuccess())
                .addOnFailureListener(e -> callback.onError(e.getMessage()));
    }

    @Override
    public void deleteMeal(String mealId, StorageCallback callback) {
        String userId = getUserId();

        firestore.collection("users")
                .document(userId)
                .collection("meals")
                .document(mealId)
                .delete()
                .addOnSuccessListener(unused -> callback.onSuccess())
                .addOnFailureListener(e -> callback.onError(e.getMessage()));
    }

    @Override
    public void getAllMealsById(StorageCallback callback) {
        String userId = getUserId();

        firestore.collection("users")
                .document(userId)
                .collection("meals")
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    List<Meal> meals = new ArrayList<>();
                    for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                        Meal meal = doc.toObject(Meal.class);
                        meals.add(meal);
                    }
                    callback.onSuccessWithResult(meals);
                })
                .addOnFailureListener(e -> callback.onError(e.getMessage()));
    }

}
