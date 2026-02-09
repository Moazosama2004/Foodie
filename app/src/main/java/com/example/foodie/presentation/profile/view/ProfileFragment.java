package com.example.foodie.presentation.profile.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.foodie.R;
import com.example.foodie.databinding.FragmentProfileBinding;
import com.example.foodie.presentation.auth.view.AuthActivity;
import com.example.foodie.presentation.profile.presenter.ProfilePresenter;
import com.example.foodie.presentation.profile.presenter.ProfilePresenterImpl;
import com.example.foodie.utils.sharedprefs.SharedPrefsManager;


public class ProfileFragment extends Fragment implements ProfileView {
    private FragmentProfileBinding binding;
    private ProfilePresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ProfilePresenterImpl(getActivity(),requireContext(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding =  FragmentProfileBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.profileEmail.setEnabled(false);
        presenter.loadUser();
        binding.btnLogout.setOnClickListener(v -> presenter.logout());
    }

    @Override
    public void goToAuth() {
        Intent intent = new Intent(getActivity(), AuthActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void showUser(String username, String email) {
        binding.profileUsername.setText(username);
        binding.profileEmail.setText(email);
    }

    @Override
    public void showError(String message) {
        // Toast / Snackbar
    }

}