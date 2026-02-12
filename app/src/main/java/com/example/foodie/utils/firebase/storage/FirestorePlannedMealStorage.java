package com.example.foodie.utils.firebase.storage;

import com.example.foodie.data.calender.model.CalendarMeal;
import com.example.foodie.utils.services.PlannedMealStorage;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class FirestorePlannedMealStorage implements PlannedMealStorage {

    private final FirebaseFirestore firestore;
    private final FirebaseAuth auth;

    public FirestorePlannedMealStorage() {
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
    public Completable saveMeal(CalendarMeal meal) {
        return Completable.create(emitter -> {
            String userId = getUserId();

            firestore.collection("users")
                    .document(userId)
                    .collection("plannedMeals")
                    .document(meal.getMealId() + "_" + meal.getDate()) // unique per date
                    .set(meal)
                    .addOnSuccessListener(unused -> emitter.onComplete())
                    .addOnFailureListener(emitter::onError);
        });
    }

    @Override
    public Completable deleteMeal(String mealId) {
        return Completable.create(emitter -> {
            String userId = getUserId();

            firestore.collection("users")
                    .document(userId)
                    .collection("plannedMeals")
                    .document(mealId)
                    .delete()
                    .addOnSuccessListener(unused -> emitter.onComplete())
                    .addOnFailureListener(emitter::onError);
        });
    }

    @Override
    public Single<List<CalendarMeal>> getAllMeals() {
        return Single.create(emitter -> {
            String userId = getUserId();

            firestore.collection("users")
                    .document(userId)
                    .collection("plannedMeals")
                    .get()
                    .addOnSuccessListener(querySnapshot -> {
                        List<CalendarMeal> meals = new ArrayList<>();

                        for (DocumentSnapshot doc : querySnapshot.getDocuments()) {
                            CalendarMeal meal = doc.toObject(CalendarMeal.class);
                            if (meal != null) meals.add(meal);
                        }

                        emitter.onSuccess(meals);
                    })
                    .addOnFailureListener(emitter::onError);
        });
    }
}
