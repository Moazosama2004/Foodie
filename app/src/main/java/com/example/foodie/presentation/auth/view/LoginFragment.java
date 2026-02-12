package com.example.foodie.presentation.auth.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.foodie.R;
import com.example.foodie.databinding.FragmentLoginBinding;
import com.example.foodie.presentation.auth.presenter.AuthPresenterImpl;
import com.example.foodie.presentation.home.view.HomeActivity;
import com.example.foodie.utils.CustomAlertDialog;

public class LoginFragment extends Fragment implements AuthView {

    private AuthPresenterImpl authPresenter;
    private FragmentLoginBinding binding;
    private ActivityResultLauncher<Intent> googleSignInLauncher;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authPresenter = new AuthPresenterImpl(this, requireActivity());

        googleSignInLauncher = launchGoogleSignIn();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.txtGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHome();
            }
        });

        binding.btnLogin.setOnClickListener(v -> {
            loginWithValidation();
        });

        binding.googleBtn.setOnClickListener(v -> startGoogleSignIn());

        binding.txtRegister.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_signupFragment)
        );

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
        if (getActivity() != null) {
            CustomAlertDialog.showError(getActivity(), message);
        }
    }

    @Override
    public void navigateToHome() {
        goToHome();
    }


    // Helpers

    private void loginWithValidation() {
        String email = binding.loginEmailTxt.getText().toString().trim();
        String password = binding.loginPasswordTxt.getText().toString().trim();
        if (!email.isEmpty() && !password.isEmpty()) {
            authPresenter.login(email, password);
        } else {
            showError("Email or Password cannot be empty");
        }
    }

    @NonNull
    private ActivityResultLauncher<Intent> launchGoogleSignIn() {
        return registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    Intent data = result.getData();
                    if (data != null) {
                        handleGoogleSignInResult(data);
                    } else {
                        showError("Google Sign-In failed: no data returned");
                    }
                }
        );
    }

    private void goToHome() {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        startActivity(intent);
        requireActivity().finish();
    }

    private void startGoogleSignIn() {
        Intent signInIntent = authPresenter.getGoogleSignInIntent();
        googleSignInLauncher.launch(signInIntent);
    }

    private void handleGoogleSignInResult(Intent data) {
        authPresenter.handleGoogleSignInResult(data);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        authPresenter.clear();
        binding = null;
    }
}
