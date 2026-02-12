package com.example.foodie.presentation.profile.presenter;

import android.app.Activity;
import android.content.Context;

import com.example.foodie.data.auth.datasource.AuthRepo;
import com.example.foodie.presentation.profile.view.ProfileView;
import com.example.foodie.utils.sharedprefs.SharedPrefsManager;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

public class ProfilePresenterImpl implements ProfilePresenter {
    private final SharedPrefsManager prefs;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final AuthRepo authRepo;
    private final ProfileView view;

    public ProfilePresenterImpl(Activity activity, Context context, ProfileView view) {
        authRepo = new AuthRepo(activity);
        this.view = view;
        this.prefs = SharedPrefsManager.getInstance(context);
    }

    @Override
    public void loadUser() {
        disposables.add(
                prefs.isLoggedIn()
                        .flatMap(isLoggedIn -> {
                            if (isLoggedIn) {
                                return prefs.getUsername()
                                        .zipWith(prefs.getEmail(), (username, email) -> new String[]{username, email});
                            } else {
                                return Single.just(new String[]{"Guest", "no Email"});
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                data -> view.showUser(data[0], data[1]),
                                throwable -> view.showError(throwable.getMessage())
                        )
        );
    }

    @Override
    public void logout() {
        disposables.add(
                prefs.clearUser()
                        .andThen(prefs.setLoggedIn(false))
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                view::goToAuth,
                                throwable -> view.showError(throwable.getMessage())
                        )
        );
    }

    @Override
    public void clear() {
        disposables.clear();
    }
}
