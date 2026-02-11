package com.example.foodie.presentation.fav.presenter;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.foodie.data.core.FavMealsRepo;
import com.example.foodie.data.core.model.User;
import com.example.foodie.data.home.model.response.Meal;
import com.example.foodie.presentation.fav.view.FavView;

import java.util.List;
import java.util.concurrent.Executors;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class FavPresenterImpl implements FavPresenter {
    private final FavView view;
    private final FavMealsRepo favMealsRepo;

    public FavPresenterImpl(Context context, FavView view) {
        this.view = view;
        this.favMealsRepo = new FavMealsRepo(context);
    }

    @Override
    public Single<List<Meal>> getFavMeals() {
        view.showProgress();
        return favMealsRepo.fetchAllFavMealsRemote()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess(favMeals -> {
                    if(favMeals.isEmpty()) {
                        view.showEmptyFav();
                    }
                    view.hideProgress();
                    view.showFavMeals(favMeals);
                })
                ;
    }


    @Override
    public Completable deleteFromFavLocal(Meal meal) {
        view.showProgress();
        return Completable.fromRunnable(() -> favMealsRepo.deleteMealLocal(meal))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(() -> {
                    view.hideProgress();
                    view.onDeleteSuccess(meal.getIdMeal());
                })
                .doOnError(e -> view.showError(e.getMessage()));
    }

    @Override
    public Completable deleteFromFavRemote(Meal meal) {

        return favMealsRepo.deleteMealRemote(meal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(() -> {
                    view.hideProgress();
                    view.onDeleteSuccess(meal.getIdMeal());
                })
                .doOnError(e -> view.showError(e.getMessage()));
    }

}
