package com.example.foodie.presentation.auth.view;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.foodie.databinding.FragmentSignupBinding;
import com.example.foodie.presentation.auth.presenter.AuthPresenter;
import com.example.foodie.presentation.auth.presenter.AuthPresenterImpl;
import com.example.foodie.presentation.home.view.HomeActivity;
import com.example.foodie.utils.CustomAlertDialog;

public class SignupFragment extends Fragment implements AuthView {
    private AuthPresenter authPresenter;
    private FragmentSignupBinding binding;
    private EditText passwordEditText;


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

        binding.txtGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToHome();
            }
        });


        binding.signupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                authPresenter.register(binding.signupNameTxt.getText().toString(), binding.signupEmailTxt.getText().toString(), binding.signupPasswordTxt.getText().toString());
            }
        });
        passwordEditText = binding.signupPasswordTxt;
        passwordEditText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return switchPasswordVisability(event);
            }
        });

    }

    private boolean switchPasswordVisability(MotionEvent event) {
        final int DRAWABLE_END = 2; // Right drawable

        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (event.getRawX() >= (passwordEditText.getRight() - passwordEditText.getCompoundDrawables()[DRAWABLE_END].getBounds().width())) {

                if (passwordEditText.getInputType() == (InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD)) {
                    // Show password
                    passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    passwordEditText.setSelection(passwordEditText.getText().length());
                } else {
                    // Hide password
                    passwordEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    passwordEditText.setSelection(passwordEditText.getText().length());
                }
                return true;
            }
        }
        return false;
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
        hideLoading();
        if (getActivity() != null) {
            CustomAlertDialog.showError(getActivity(), message);
        }
    }

    @Override
    public void navigateToHome() {
        goToHome();
    }

    // Helpers
    private void goToHome() {
        Intent intent = new Intent(getActivity(), HomeActivity.class);
        startActivity(intent);
        requireActivity().finish();
    }
}