package com.example.foodie.utils.firebase.storage;

import com.example.foodie.data.core.model.User;
import com.example.foodie.utils.services.StorageCallback;
import com.example.foodie.utils.services.UserStorage;
import com.google.firebase.firestore.FirebaseFirestore;

public class FirestoreUserStorage implements UserStorage {
    private final FirebaseFirestore firestore;

    public FirestoreUserStorage() {
        firestore = FirebaseFirestore.getInstance();
    }

    @Override
    public void saveUser(User user, StorageCallback callback) {
        if (user.getUserId() == null || user.getUserId().isEmpty()) {
            callback.onError("User ID is null or empty");
            return;
        }

        firestore.collection("users")
                .document(user.getUserId())
                .set(user)
                .addOnSuccessListener(unused -> callback.onSuccess())
                .addOnFailureListener(e -> callback.onError(e.getMessage()));
    }

    @Override
    public void deleteUser(String userId, StorageCallback callback) {
        firestore.collection("users")
                .document(userId)
                .delete()
                .addOnSuccessListener(unused -> callback.onSuccess())
                .addOnFailureListener(e -> callback.onError(e.getMessage()));
    }
}
