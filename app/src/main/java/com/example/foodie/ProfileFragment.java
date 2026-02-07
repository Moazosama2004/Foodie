package com.example.foodie;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.foodie.utils.sharedprefs.SharedPrefsManager;


public class ProfileFragment extends Fragment {
    private TextView userName;
    private EditText emailEditText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userName = view.findViewById(R.id.profile_username);
        emailEditText = view.findViewById(R.id.profile_email);
        emailEditText.setEnabled(false);
        userName.setText(SharedPrefsManager.getInstance(requireContext()).getUsername());
        emailEditText.setText(SharedPrefsManager.getInstance(requireContext()).getEmail());


    }
}