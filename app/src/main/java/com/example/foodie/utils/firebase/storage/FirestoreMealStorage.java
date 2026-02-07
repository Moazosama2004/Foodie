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

        Map<String, Object> mealMap = new com.google.gson.Gson().fromJson(
                new com.google.gson.Gson().toJson(meal),
                Map.class
        );

        firestore.collection("users")
                .document(userId)
                .update("meals", com.google.firebase.firestore.FieldValue.arrayUnion(mealMap))
                .addOnSuccessListener(unused -> callback.onSuccess())
                .addOnFailureListener(e -> {
                    Map<String, Object> data = new HashMap<>();
                    List<Map<String, Object>> mealsList = new ArrayList<>();
                    mealsList.add(mealMap);
                    data.put("meals", mealsList);
                    firestore.collection("users")
                            .document(userId)
                            .set(data)
                            .addOnSuccessListener(unused1 -> callback.onSuccess())
                            .addOnFailureListener(e1 -> callback.onError(e1.getMessage()));
                });
    }

    @Override
    public void deleteMeal(String mealId, StorageCallback callback) {
        String userId = getUserId();

        firestore.collection("users")
                .document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    List<Map<String, Object>> mealsArray = (List<Map<String, Object>>) documentSnapshot.get("meals");
                    if (mealsArray == null) mealsArray = new ArrayList<>();

                    List<Map<String, Object>> updatedMeals = new ArrayList<>();
                    for (Map<String, Object> mealMap : mealsArray) {
                        Object id = mealMap.get("idMeal");
                        if (id != null && !id.equals(mealId)) {
                            updatedMeals.add(mealMap);
                        }
                    }

                    firestore.collection("users")
                            .document(userId)
                            .update("meals", updatedMeals)
                            .addOnSuccessListener(unused -> callback.onSuccess())
                            .addOnFailureListener(e -> callback.onError(e.getMessage()));
                })
                .addOnFailureListener(e -> callback.onError(e.getMessage()));
    }





    @Override
    public void getAllMealsById(StorageCallback callback) {
        String userId = getUserId();

        firestore.collection("users")
                .document(userId)
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        List<Meal> meals = new ArrayList<>();
                        List<Object> mealsArray = (List<Object>) documentSnapshot.get("meals");

                        if (mealsArray != null) {
                            for (Object obj : mealsArray) {
                                Meal m = new com.google.gson.Gson().fromJson(
                                        new com.google.gson.Gson().toJson(obj),
                                        Meal.class
                                );
                                meals.add(m);
                            }
                        }

                        callback.onSuccessWithResult(meals);
                    } else {
                        callback.onError("User not found");
                    }
                })
                .addOnFailureListener(e -> callback.onError(e.getMessage()));
    }
}
