package com.example.foodie.presentation.onboarding.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.foodie.databinding.FragmentViewPagerBinding;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerFragment extends Fragment {

    private FragmentViewPagerBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentViewPagerBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        ArrayList<Fragment> fragments = new ArrayList<>(List.of(
                new FirstFragment(),
                new SecondFragment()
        ));

        ViewPagerAdapter adapter = new ViewPagerAdapter(
                fragments,
                requireActivity().getSupportFragmentManager(),
                getLifecycle()
        );

        binding.viewPager.setAdapter(adapter);
        return view;
    }
}