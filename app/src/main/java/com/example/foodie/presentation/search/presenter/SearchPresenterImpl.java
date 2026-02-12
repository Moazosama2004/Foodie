package com.example.foodie.presentation.search.presenter;

import android.util.Log;

import com.example.foodie.data.search.MealSearchRepo;
import com.example.foodie.presentation.search.view.SearchView;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class SearchPresenterImpl implements SearchPresenter {

    private final MealSearchRepo repo;
    private final SearchView view;
    private final CompositeDisposable disposables;

    public SearchPresenterImpl(SearchView view) {
        this.repo = new MealSearchRepo();
        this.view = view;
        this.disposables = new CompositeDisposable();
    }

    @Override
    public void getCategories() {
        disposables.add(
                repo.getCategories()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                categories -> view.showData(categories),
                                throwable -> view.showError(throwable.getMessage())
                        )
        );
    }

    @Override
    public void getAreas() {
        disposables.add(
                repo.getAreas()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                areas -> view.showData(areas),
                                throwable -> view.showError(throwable.getMessage())
                        )
        );
    }

    @Override
    public void getIngredients() {
        Log.d("SearchPresenter", "getIngredients called");
        disposables.add(
                repo.getIngredients()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                ingredients -> view.showData(ingredients),
                                throwable -> view.showError(throwable.getMessage())
                        )
        );
    }

    @Override
    public void getFilteredMealsByArea(String country) {
        disposables.add(
                repo.getFilteredMealsByArea(country)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                meals -> view.showData(meals),
                                throwable -> view.showError(throwable.getMessage())
                        )
        );
    }

    @Override
    public void getFilteredMealsByCategory(String category) {
        disposables.add(
                repo.getFilteredMealsByCategory(category)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                meals -> view.showData(meals),
                                throwable -> view.showError(throwable.getMessage())
                        )
        );
    }

    @Override
    public void getFilteredMealsByIngredient(String ingredient) {
        Log.d("SearchPresenter", "getFilteredMealsByIngredient called");
        disposables.add(
                repo.getFilteredMealsByIngredient(ingredient)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                meals -> view.showData(meals),
                                throwable -> view.showError(throwable.getMessage())
                        )
        );
    }

    @Override
    public void getMealById(String id) {
        disposables.add(
                repo.getMealById(id)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                meal -> {
                                    if (meal != null) {
                                        view.goToMealDetails(meal);
                                    } else {
                                        view.showError("Meal not found");
                                    }
                                },
                                throwable -> view.showError(throwable.getMessage())
                        )
        );
    }

    public void clear() {
        disposables.clear();
    }
}
