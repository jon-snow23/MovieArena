package com.moviearena.views.Home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.moviearena.R;
import com.moviearena.databinding.FragmentHomeBinding;
import com.moviearena.views.MyHome.MyHomeFragment;
import com.moviearena.views.Search.SearchFragment;
import com.moviearena.views.WatchList.WatchListFragment;
import com.nafis.bottomnavigation.NafisBottomNavigation;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;


public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentHomeBinding.bind(view);
        binding.nav.add(new NafisBottomNavigation.Model(2,R.drawable.ic_baseline_search_24));
        binding.nav.add(new NafisBottomNavigation.Model(1,R.drawable.ic_baseline_home_24));
        binding.nav.add(new NafisBottomNavigation.Model(3, R.drawable.ic_baseline_bookmark_border_24));

        binding.nav.show(1,true);

        binding.nav.setOnClickMenuListener(new Function1<NafisBottomNavigation.Model, Unit>() {
            @Override
            public Unit invoke(NafisBottomNavigation.Model model) {
                switch (model.getId()){
                    case 1:
                        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_frame,new MyHomeFragment()).commit();
                        break;
                    case 2:
                        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_frame,new SearchFragment()).commit();
                        break;
                    case 3:
                        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_frame,new WatchListFragment()).commit();
                        break;
                }
                return null;
            }
        });

        binding.nav.setOnShowListener(model -> {
            switch (model.getId()){
                case 1:
                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_frame,new MyHomeFragment()).commit();
                    break;
                case 2:
                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_frame,new SearchFragment()).commit();
                    break;
                case 3:
                    requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_frame,new WatchListFragment()).commit();
                    break;
            }
            return null;
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}