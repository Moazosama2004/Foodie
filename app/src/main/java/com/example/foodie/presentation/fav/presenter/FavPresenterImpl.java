package com.example.foodie.presentation.fav.presenter;

import android.content.Context;

import com.example.foodie.data.fav.FavMealsRepo;
import com.example.foodie.data.home.model.response.Meal;
import com.example.foodie.presentation.fav.view.FavView;
import com.example.foodie.utils.sharedprefs.SharedPrefsManager;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavPresenterImpl implements FavPresenter {

    private final FavView view;
    private final FavMealsRepo repo;
    private final SharedPrefsManager prefsManager;
    private final CompositeDisposable disposables = new CompositeDisposable();

    public FavPresenterImpl(Context context, FavView view) {
        this.view = view;
        this.repo = new FavMealsRepo(context);
        this.prefsManager = SharedPrefsManager.getInstance(context);
    }

    @Override
    public void checkLoginAndLoad() {
        disposables.add(
                prefsManager.isLoggedIn()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(isLoggedIn -> {
                            if (isLoggedIn) {
                                loadFavMeals();
                            } else {
                                view.showEmptyFav();
                                view.showGuestAlert();
                            }
                        }, throwable -> view.showError(throwable.getMessage()))
        );
    }

    @Override
    public void loadFavMeals() {

        view.showProgress();

        disposables.add(
                repo.fetchAllFavMealsRemote()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(meals -> {
                            view.hideProgress();

                            if (meals == null || meals.isEmpty()) {
                                view.showEmptyFav();
                            } else {
                                view.showFavMeals(meals);
                            }
                        }, throwable -> {
                            view.hideProgress();
                            view.showError(throwable.getMessage());
                        })
        );
    }

    @Override
    public void deleteMeal(Meal meal) {

        view.showProgress();

        disposables.add(
                repo.deleteMealRemote(meal)
                        .andThen(repo.deleteMealLocal(meal))
                        .andThen(repo.fetchAllFavMealsRemote())
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(meals -> {
                            view.hideProgress();

                            if (meals.isEmpty()) {
                                view.showEmptyFav();
                            } else {
                                view.showFavMeals(meals);
                            }

                        }, throwable -> {
                            view.hideProgress();
                            view.showError(throwable.getMessage());
                        })
        );
    }


    @Override
    public void clear() {
        disposables.clear();
    }
}
