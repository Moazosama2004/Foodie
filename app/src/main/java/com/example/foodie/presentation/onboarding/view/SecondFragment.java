package com.example.foodie.presentation.onboarding.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.foodie.R;
import com.example.foodie.databinding.FragmentSecondBinding;
import com.example.foodie.presentation.auth.view.AuthActivity;

public class SecondFragment extends Fragment {

    private FragmentSecondBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSecondBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        binding.onboardingSecondNextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToLogin();
            }
        });
        return view;
    }

    private void goToLogin() {
        Intent intent = new Intent(getActivity(), AuthActivity.class);
        startActivity(intent);
        requireActivity().finish();
    }
}

