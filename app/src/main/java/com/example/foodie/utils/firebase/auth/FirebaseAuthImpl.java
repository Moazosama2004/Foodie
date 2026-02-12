package com.example.foodie.utils.firebase.auth;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.example.foodie.R;
import com.example.foodie.utils.services.AuthService;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FirebaseAuthImpl implements AuthService {

    public static final int RC_GOOGLE_SIGN_IN = 1001;
    private static final String TAG = "FirebaseAuthImpl";

    private final FirebaseAuth firebaseAuth;
    private final Activity activity;
    private final GoogleSignInClient googleSignInClient;

    public FirebaseAuthImpl(Activity activity) {
        this.activity = activity;
        this.firebaseAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(
                GoogleSignInOptions.DEFAULT_SIGN_IN
        )
                .requestIdToken(activity.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(activity, gso);
    }

    // Email - Password Config
    public Single<FirebaseUser> getCurrentUser() {
        return Single.create(emitter -> {
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user != null) emitter.onSuccess(user);
            else emitter.onError(new IllegalStateException("No FirebaseUser logged in"));
        });
    }

    @Override
    public Single<String> getCurrentUserId() {
        return Single.fromCallable(() -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            return user != null ? user.getUid() : null;
        }).subscribeOn(Schedulers.io());
    }


    @Override
    public Completable login(String email, String password) {
        return Completable.create(emitter ->
                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnSuccessListener(authResult -> {
                            Log.d(TAG, "Login success");
                            emitter.onComplete();
                        })
                        .addOnFailureListener(e -> {
                            Log.e(TAG, "Login failed", e);
                            emitter.onError(e);
                        })
        ).subscribeOn(Schedulers.io());
    }

    @Override
    public Completable register(String email, String password) {
        return Completable.create(emitter ->
                firebaseAuth.createUserWithEmailAndPassword(email, password)
                        .addOnSuccessListener(authResult -> {
                            Log.d(TAG, "Register success");
                            emitter.onComplete();
                        })
                        .addOnFailureListener(e -> {
                            Log.e(TAG, "Register failed", e);
                            emitter.onError(e);
                        })
        ).subscribeOn(Schedulers.io());
    }

    // Google Config

    public Single<String> handleGoogleSignInResult(Intent data) {
        return Single.create(emitter -> {
            try {
                GoogleSignInAccount account =
                        GoogleSignIn.getSignedInAccountFromIntent(data)
                                .getResult(ApiException.class);

                emitter.onSuccess(account.getIdToken());

            } catch (ApiException e) {
                emitter.onError(e);
            }
        });
    }

    @Override
    public Completable signInWithGoogle(String idToken) {
        return Completable.create(emitter -> {
            AuthCredential credential =
                    GoogleAuthProvider.getCredential(idToken, null);

            firebaseAuth.signInWithCredential(credential)
                    .addOnSuccessListener(authResult -> {
                        Log.d(TAG, "Google login success");
                        emitter.onComplete();
                    })
                    .addOnFailureListener(e -> {
                        Log.e(TAG, "Google login failed", e);
                        emitter.onError(e);
                    });
        }).subscribeOn(Schedulers.io());
    }


    public Intent getGoogleSignInIntent() {
        return googleSignInClient.getSignInIntent();
    }

    public String extractIdTokenFromIntent(Intent data) {
        try {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            GoogleSignInAccount account = task.getResult(ApiException.class);
            return account.getIdToken();
        } catch (ApiException e) {
            Log.e(TAG, "Google Sign-In failed", e);
            return null;
        }
    }


    // Logout from all States
    @Override
    public Completable logout() {
        return Completable.fromAction(() -> {
            firebaseAuth.signOut();
            googleSignInClient.signOut();
        }).subscribeOn(Schedulers.io());
    }

}
