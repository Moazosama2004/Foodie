package com.example.foodie.utils;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.example.foodie.R;
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

public class FirebaseAuthImpl implements FirebaseAuthService {

    public static final int RC_GOOGLE_SIGN_IN = 1001;
    private static final String TAG = "FirebaseAuthImpl";
    private final FirebaseAuth firebaseAuth;
    private final Activity activity;
    private GoogleSignInClient googleSignInClient;

    public FirebaseAuthImpl(Activity activity) {
        this.firebaseAuth = FirebaseAuth.getInstance();
        this.activity = activity;

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(activity.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(activity, gso);
    }

    @Override
    public void login(String email, String password) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Login successful");
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                    } else {
                        Log.e(TAG, "Login failed", task.getException());
                    }
                });
    }

    @Override
    public void register(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Registration successful");
                        FirebaseUser user = firebaseAuth.getCurrentUser();
                    } else {
                        Log.e(TAG, "Registration failed", task.getException());
                    }
                });
    }

    // ================= GOOGLE SIGN-IN =================
    @Override
    public void firebaseWithGoogle() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        activity.startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN);
    }

    public void handleGoogleSignInResult(Intent data) {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            firebaseAuthWithGoogle(account.getIdToken());
        } catch (ApiException e) {
            Log.e(TAG, "Google Sign-In failed", e);
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(activity, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Firebase Google Sign-In success");
                    } else {
                        Log.e(TAG, "Firebase Google auth failed", task.getException());
                    }
                });
    }

    @Override
    public void logout() {
        firebaseAuth.signOut();
        googleSignInClient.signOut();
        Log.d(TAG, "User logged out");
    }
}
