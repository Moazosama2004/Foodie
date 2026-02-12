package com.example.foodie.presentation.auth.presenter;

import android.content.Context;
import android.content.Intent;

import com.example.foodie.data.auth.datasource.AuthRepo;
import com.example.foodie.presentation.auth.view.AuthView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class AuthPresenterImpl implements AuthPresenter {

    private final AuthRepo authRepo;
    private final AuthView authView;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public AuthPresenterImpl(AuthView authView, Context context) {
        this.authView = authView;
        this.authRepo = new AuthRepo(context);
    }

    @Override
    public void login(String email, String password) {
        authView.showLoading();
        disposables.add(
                authRepo.login(email, password)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                    authView.hideLoading();
                                    authView.navigateToHome();
                                },
                                throwable -> {
                                    authView.hideLoading();
                                    authView.showError(throwable.getMessage());
                                }
                        )
        );
    }

    @Override
    public void register(String username, String email, String password) {
        authView.showLoading();
        disposables.add(
                authRepo.register(username, email, password)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                    authView.hideLoading();
                                    authView.navigateToHome();
                                },
                                throwable -> {
                                    authView.hideLoading();
                                    authView.showError(throwable.getMessage());
                                }
                        )
        );
    }


    @Override
    public void firebaseWithGoogle(String idToken) {
        authView.showLoading();
        disposables.add(
                authRepo.signInWithGoogle(idToken)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                () -> {
                                    authView.hideLoading();
                                    authView.navigateToHome();
                                },
                                throwable -> {
                                    authView.hideLoading();
                                    authView.showError(throwable.getMessage());
                                }
                        )
        );
    }


    // Helpers Fot init Firebase With Google
    public Intent getGoogleSignInIntent() {
        return authRepo.getAuthService().getGoogleSignInIntent();
    }

    public void handleGoogleSignInResult(Intent data) {
        String idToken = authRepo.getAuthService()
                .extractIdTokenFromIntent(data);

        loginWithGoogle(idToken);
    }

    private void loginWithGoogle(String idToken) {
        disposables.add(
                authRepo.signInWithGoogle(idToken)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe(d -> authView.showLoading())
                        .doFinally(() -> authView.hideLoading())
                        .subscribe(
                                () -> authView.navigateToHome(),
                                throwable -> authView.showError(throwable.getMessage())
                        )
        );
    }


    @Override
    public void clear() {
        disposables.clear();
    }

}
