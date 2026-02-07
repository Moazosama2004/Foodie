package com.example.foodie.presentation.fav.presenter;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.foodie.data.core.FavMealsRepo;
import com.example.foodie.data.core.model.User;
import com.example.foodie.data.home.model.response.Meal;
import com.example.foodie.presentation.fav.view.FavView;
import com.example.foodie.utils.services.StorageCallback;

import java.util.List;
import java.util.concurrent.Executors;

public class FavPresenterImpl implements FavPresenter {
    private final FavView view;
    private final FavMealsRepo favMealsRepo;

    public FavPresenterImpl(Context context, FavView view) {
        this.view = view;
        this.favMealsRepo = new FavMealsRepo(context);
    }

    @Override
    public LiveData<List<Meal>> getFavMeals() {
        return favMealsRepo.getAllFavMeals();
    }


    @Override
    public void deleteFromFavLocal(Meal meal) {
        Executors.newSingleThreadExecutor().execute(() ->
                favMealsRepo.deleteMealLocal(meal)
        );
    }

    @Override
    public void deleteFromFavRemote(String id) {
        favMealsRepo.deleteMealRemote(id, new StorageCallback() {
            @Override
            public void onSuccess() {
                Log.d("deleteFromFavRemote", "onSuccess: ");
                view.onSuccess();
            }

            @Override
            public void onError(String message) {
                view.showError(message);
            }

            @Override
            public void onSuccessWithResult(List<Meal> meals) {

            }

            @Override
            public void onSuccessWithUserData(User user) {

            }
        });
    }
}
