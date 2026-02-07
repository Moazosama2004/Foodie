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
import com.example.foodie.presentation.auth.presenter.AuthPresenter;
import com.example.foodie.presentation.auth.presenter.AuthPresenterImpl;
import com.example.foodie.presentation.home.view.HomeActivity;

public class SignupFragment extends Fragment implements AuthView {
    private Button btnSignup;
    private TextView txtLogin;
    private TextView nameTxt;
    private TextView emailTxt;
    private TextView passwordTxt;
    private ProgressBar progressBar;
    private AuthPresenter authPresenter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authPresenter = new AuthPresenterImpl(this, getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_signup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnSignup = view.findViewById(R.id.signup_btn);
        txtLogin = view.findViewById(R.id.textView8);
        nameTxt = view.findViewById(R.id.signup_name_txt);
        emailTxt = view.findViewById(R.id.signup_email_txt);
        passwordTxt = view.findViewById(R.id.signup_password_txt);
        progressBar=  view.findViewById(R.id.signup_progress);


        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authPresenter.register(emailTxt.getText().toString(), passwordTxt.getText().toString());
            }
        });


    }

    @Override
    public void showLoading() {
        btnSignup.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        progressBar.setVisibility(View.GONE);
        btnSignup.setVisibility(View.VISIBLE);
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