package com.example.foodie.onboarding.screens;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.foodie.R;
import com.example.foodie.auth.AuthActivity;
import com.example.foodie.home.HomeActivity;

public class SecondFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second, container, false);
        Button next = view.findViewById(R.id.onboarding_second_next_btn);
        ViewPager2 viewPager = getActivity().findViewById(R.id.view_pager);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() , AuthActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

}

