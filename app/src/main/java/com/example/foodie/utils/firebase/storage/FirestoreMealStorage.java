package com.example.foodie.utils.firebase.storage;

import com.example.foodie.data.home.model.response.Meal;
import com.example.foodie.utils.services.MealStorage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

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

    public Completable saveMeal(Meal meal) {
        return Completable.create(emitter -> {
            String userId = getUserId();
            firestore.collection("users")
                    .document(userId)
                    .collection("meals")
                    .document(meal.getIdMeal())
                    .set(meal)
                    .addOnSuccessListener(unused -> emitter.onComplete())
                    .addOnFailureListener(emitter::onError);
        });
    }

    public Completable deleteMeal(String mealId) {
        return Completable.create(emitter -> {
            String userId = getUserId();
            firestore.collection("users")
                    .document(userId)
                    .collection("meals")
                    .document(mealId)
                    .delete()
                    .addOnSuccessListener(unused -> emitter.onComplete())
                    .addOnFailureListener(emitter::onError);
        });
    }


    @Override
    public Single<List<Meal>> getAllMeals() {
        return Single.create(emitter -> {
            String userId = getUserId();
            firestore.collection("users")
                    .document(userId)
                    .collection("meals")
                    .get()
                    .addOnSuccessListener(querySnapshot -> {
                        List<Meal> meals = new ArrayList<>();
                        for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                            Meal meal = doc.toObject(Meal.class);
                            if (meal != null) meals.add(meal);
                        }
                        emitter.onSuccess(meals);
                    })
                    .addOnFailureListener(emitter::onError);
        });
    }
}
