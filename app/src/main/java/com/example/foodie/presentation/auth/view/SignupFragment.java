package com.example.foodie.presentation.auth.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.foodie.R;
import com.example.foodie.databinding.FragmentSignupBinding;
import com.example.foodie.presentation.auth.presenter.AuthPresenter;
import com.example.foodie.presentation.auth.presenter.AuthPresenterImpl;
import com.example.foodie.presentation.home.view.HomeActivity;

public class SignupFragment extends Fragment implements AuthView {
    private AuthPresenter authPresenter;
    private FragmentSignupBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authPresenter = new AuthPresenterImpl(this, getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSignupBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authPresenter.register(binding.signupNameTxt.getText().toString(),binding.signupEmailTxt.getText().toString(), binding.signupPasswordTxt.getText().toString());
            }
        });


    }

    @Override
    public void showLoading() {
        binding.signupBtn.setVisibility(View.INVISIBLE);
        binding.signupProgress.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        binding.signupProgress.setVisibility(View.GONE);
        binding.signupBtn.setVisibility(View.VISIBLE);
    }


    @Override
    public void showError(String message) {
        showErrorDialog(message);
    }

    @Override
    public void navigateToHome() {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        startActivity(intent);
        requireActivity().finish();

    }

    private void showErrorDialog(String message) {
        hideLoading();

        new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                .setTitle("Registration Failed")
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .setCancelable(false)
                .show();
    }
}