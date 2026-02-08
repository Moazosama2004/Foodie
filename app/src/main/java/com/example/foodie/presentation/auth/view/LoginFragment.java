package com.example.foodie.presentation.auth.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.foodie.R;
import com.example.foodie.databinding.FragmentLoginBinding;
import com.example.foodie.presentation.auth.presenter.AuthPresenter;
import com.example.foodie.presentation.auth.presenter.AuthPresenterImpl;
import com.example.foodie.presentation.home.view.HomeActivity;

public class LoginFragment extends Fragment implements AuthView {

    private AuthPresenter authPresenter;

    private FragmentLoginBinding binding;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authPresenter = new AuthPresenterImpl(this, getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authPresenter.login(binding.loginEmailTxt.getText().toString(), binding.loginPasswordTxt.getText().toString());
            }
        });


        binding.googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authPresenter.firebaseWithGoogle();
            }
        });


        binding.txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_signupFragment);
            }
        });
    }

    @Override
    public void showLoading() {
        binding.loginProgress.setVisibility(View.VISIBLE);
        binding.btnLogin.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        binding.loginProgress.setVisibility(View.GONE);
        binding.btnLogin.setVisibility(View.VISIBLE);
    }

    @Override
    public void showError(String message) {
        showErrorDialog(message);
    }

    @Override
    public void navigateToHome() {
        authPresenter.setUserLoggedIn();
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        startActivity(intent);
        requireActivity().finish();
    }

    private void showErrorDialog(String message) {
        hideLoading();

        new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                .setTitle("Login Failed")
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .setCancelable(false)
                .show();
    }
}