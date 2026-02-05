package com.example.foodie.presentation.onboarding.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.foodie.R;
import com.example.foodie.presentation.home.view.HomeActivity;
import com.example.foodie.presentation.onboarding.presenter.OnboardingPresenter;
import com.example.foodie.presentation.onboarding.presenter.OnboardingPresenterImpl;

public class SplashFragment extends Fragment implements OnboardingView {

    private OnboardingPresenter presenter;
    private View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new OnboardingPresenterImpl(
                requireContext(),
                this
        );
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            ViewGroup container,
            Bundle savedInstanceState
    ) {
        rootView = inflater.inflate(R.layout.fragment_splash, container, false);

        new Handler().postDelayed(() -> {
            presenter.decideStartDestination();
        }, 3000);

        return rootView;
    }

    @Override
    public void showOnboarding() {
        Navigation.findNavController(rootView)
                .navigate(R.id.action_splashFragment_to_viewPagerFragment);
    }

    @Override
    public void showLogin() {
        Navigation.findNavController(rootView)
                .navigate(R.id.action_splashFragment_to_loginFragment2);
    }

    @Override
    public void showHome() {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        startActivity(intent);
    }
}