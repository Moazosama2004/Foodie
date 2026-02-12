package com.example.foodie.presentation.onboarding.presenter;

import android.content.Context;
import android.util.Log;

import com.example.foodie.data.onboarding.OnboardingRepo;
import com.example.foodie.presentation.onboarding.view.OnboardingView;

import java.util.concurrent.TimeUnit;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class OnboardingPresenterImpl implements OnboardingPresenter {
    private final OnboardingRepo repo;
    private final OnboardingView view;

    private final CompositeDisposable disposables = new CompositeDisposable();

    public OnboardingPresenterImpl(Context context, OnboardingView view) {
        this.repo = new OnboardingRepo(context);
        this.view = view;
    }

    @Override
    public void decideStartDestination() {
        disposables.add(
                repo.isOnboardingSeen()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                seen -> {
                                    if (!seen) {
                                        view.showOnboarding();

                                        disposables.add(
                                                repo.setOnboardingSeen(true)
                                                        .subscribe(
                                                                () -> {
                                                                },
                                                                throwable -> Log.e("Onboarding", throwable.getMessage())
                                                        )
                                        );
                                    } else {
                                        checkLogin();
                                    }
                                },
                                throwable -> Log.e("Onboarding", throwable.getMessage())
                        )
        );
    }


    private void checkLogin() {
        disposables.add(
                repo.userIsLoggedIn()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                isLoggedIn -> {
                                    if (isLoggedIn) {
                                        view.showHome();
                                    } else {
                                        view.showLogin();
                                    }
                                },
                                throwable -> Log.e("Onboarding", throwable.getMessage())
                        )
        );
    }


    @Override
    public void splashLogic() {
        disposables.add(
                io.reactivex.rxjava3.core.Completable
                        .timer(3500, TimeUnit.MILLISECONDS)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(this::decideStartDestination)
        );
    }

    @Override
    public void clear() {
        disposables.clear();
    }
}
