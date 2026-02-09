package com.example.foodie.utils.firebase.storage;

import com.example.foodie.data.core.model.User;
import com.example.foodie.utils.services.UserStorage;
import com.google.firebase.firestore.FirebaseFirestore;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class FirestoreUserStorage implements UserStorage {

    private final FirebaseFirestore firestore = FirebaseFirestore.getInstance();

    @Override
    public Completable saveUser(User user) {
        return Completable.create(emitter ->
                firestore.collection("users")
                        .document(user.getUserId())
                        .set(user)
                        .addOnSuccessListener(aVoid -> emitter.onComplete())
                        .addOnFailureListener(emitter::onError)
        );
    }

    @Override
    public Completable deleteUser(String userId) {
        return Completable.create(emitter ->
                firestore.collection("users")
                        .document(userId)
                        .delete()
                        .addOnSuccessListener(aVoid -> emitter.onComplete())
                        .addOnFailureListener(emitter::onError)
        );
    }

    @Override
    public Single<User> getUserById(String userId) {
        return Single.create(emitter ->
                firestore.collection("users")
                        .document(userId)
                        .get()
                        .addOnSuccessListener(documentSnapshot -> {
                            if (documentSnapshot.exists()) {
                                User user = documentSnapshot.toObject(User.class);
                                emitter.onSuccess(user);
                            } else {
                                emitter.onError(new Exception("User not found"));
                            }
                        })
                        .addOnFailureListener(emitter::onError)
        );
    }
}
