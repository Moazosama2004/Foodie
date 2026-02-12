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

public class LoginFragment extends Fragment implements AuthView {

    private AuthPresenterImpl authPresenter;
    private FragmentLoginBinding binding;
    private ActivityResultLauncher<Intent> googleSignInLauncher;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authPresenter = new AuthPresenterImpl(this, requireActivity());

        googleSignInLauncher = registerForActivityResult(
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.btnLogin.setOnClickListener(v -> {
            String email = binding.loginEmailTxt.getText().toString().trim();
            String password = binding.loginPasswordTxt.getText().toString().trim();
            if (!email.isEmpty() && !password.isEmpty()) {
                authPresenter.login(email, password);
            } else {
                showError("Email or Password cannot be empty");
            }
        });

        binding.googleBtn.setOnClickListener(v -> startGoogleSignIn());

        binding.txtRegister.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_signupFragment)
        );

    }

    private void startGoogleSignIn() {
        Intent signInIntent = authPresenter.getGoogleSignInIntent();
        googleSignInLauncher.launch(signInIntent);
    }

    private void handleGoogleSignInResult(Intent data) {
        authPresenter.handleGoogleSignInResult(data);
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
        new androidx.appcompat.app.AlertDialog.Builder(requireContext())
                .setTitle("Login Failed")
                .setMessage(message)
                .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                .setCancelable(false)
                .show();
    }

    @Override
    public void navigateToHome() {
        authPresenter.setUserLoggedIn();
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        startActivity(intent);
        requireActivity().finish();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        authPresenter.clear();
        binding = null;
    }
}
