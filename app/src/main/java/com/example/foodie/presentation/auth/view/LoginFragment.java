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
import com.example.foodie.presentation.auth.presenter.AuthPresenter;
import com.example.foodie.presentation.auth.presenter.AuthPresenterImpl;
import com.example.foodie.presentation.home.view.HomeActivity;

public class LoginFragment extends Fragment implements AuthView {

    private Button btnLogin;
    private TextView txtRegister;
    private TextView emailTxt;
    private TextView passwordTxt;
    private ImageView googleBtn;
    private ProgressBar loading;
    private AuthPresenter authPresenter;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authPresenter = new AuthPresenterImpl(this, getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnLogin = view.findViewById(R.id.btn_login);
        txtRegister = view.findViewById(R.id.txt_register);
        emailTxt = view.findViewById(R.id.login_email_txt);
        passwordTxt = view.findViewById(R.id.login_password_txt);
        googleBtn = view.findViewById(R.id.google_btn);
        loading = view.findViewById(R.id.login_progress);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authPresenter.login(emailTxt.getText().toString(), passwordTxt.getText().toString());
            }
        });


        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authPresenter.firebaseWithGoogle();
            }
        });


        txtRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_signupFragment);
            }
        });
    }

    @Override
    public void showLoading() {
        loading.setVisibility(View.VISIBLE);
        btnLogin.setVisibility(View.GONE);
    }

    @Override
    public void hideLoading() {
        loading.setVisibility(View.GONE);
        btnLogin.setVisibility(View.VISIBLE);
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