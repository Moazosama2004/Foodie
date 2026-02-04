package com.example.foodie.presentation.onboarding.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.foodie.R;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerFragment extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_view_pager, container, false);

        ArrayList<Fragment> fragments = new ArrayList<>(List.of(
                new FirstFragment(),
                new SecondFragment()
        ));

        ViewPagerAdapter adapter = new ViewPagerAdapter(
                fragments,
                requireActivity().getSupportFragmentManager(),
                getLifecycle()
        );

        ViewPager2 viewPager = view.findViewById(R.id.view_pager);
        viewPager.setAdapter(adapter);
        return view;
    }
}