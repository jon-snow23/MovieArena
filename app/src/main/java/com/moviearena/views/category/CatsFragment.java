package com.moviearena.views.category;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.moviearena.R;
import com.moviearena.Utils.SharedModel;
import com.moviearena.adapters.RecyclerCatsAdapter;
import com.moviearena.databinding.FragmentCatsBinding;
import com.moviearena.models.MovieModel;
import com.moviearena.views.Movie.MovieFragment;
import com.nafis.bottomnavigation.NafisBottomNavigation;

import java.util.ArrayList;


public class CatsFragment extends Fragment {


    CatsViewModel viewModel;
    RecyclerCatsAdapter adapter = new RecyclerCatsAdapter();
    FragmentCatsBinding binding;
    NafisBottomNavigation nav;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cats, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentCatsBinding.bind(view);
        viewModel = new ViewModelProvider(this).get(CatsViewModel.class);

        nav = getActivity().findViewById(R.id.nav);
        nav.setVisibility(View.GONE);

        onClicks();


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void onClicks(){
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().onBackPressed();
            }
        });
        binding.title.setText(SharedModel.getSelectedTitle());
        adapter.setList(SharedModel.getSelectedCat());
        binding.recycler.setAdapter(adapter);
        binding.bar.setVisibility(View.GONE);
        adapter.setOnItemClick(new RecyclerCatsAdapter.OnItemClick() {
            @Override
            public void OnClick(int MovieId) {
                SharedModel.setId(MovieId);
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_frame , new MovieFragment() , "movie")
                        .addToBackStack("movie").commit();
            }
        });
    }

    private void getData(){
        if (SharedModel.getCat()==1){
            getPopular();
        }
        else if (SharedModel.getCat() == 2){
            getTop();
        }
        else {
            getUpcoming();
        }
    }

    private void getPopular(){
        binding.title.setText("Popular Movies");
        viewModel.getPopularMovies();
        viewModel.Popular.observe(getViewLifecycleOwner(), new Observer<MovieModel>() {
            @Override
            public void onChanged(MovieModel movieModel) {
                adapter.setList((ArrayList<MovieModel.ResultsDTO>) movieModel.getResults());
                binding.recycler.setAdapter(adapter);
                binding.bar.setVisibility(View.GONE);
                adapter.setOnItemClick(new RecyclerCatsAdapter.OnItemClick() {
                    @Override
                    public void OnClick(int MovieId) {
                        SharedModel.setId(MovieId);
                        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_frame , new MovieFragment() , "movie")
                                .addToBackStack("movie").commit();
                    }
                });
            }
        });
    }
    private void getTop(){
        binding.title.setText("Top Rated Movies");
        viewModel.getTopMovies();
        viewModel.Top.observe(getViewLifecycleOwner(), new Observer<MovieModel>() {
            @Override
            public void onChanged(MovieModel movieModel) {
                adapter.setList((ArrayList<MovieModel.ResultsDTO>) movieModel.getResults());
                binding.recycler.setAdapter(adapter);
                binding.bar.setVisibility(View.GONE);
                adapter.setOnItemClick(new RecyclerCatsAdapter.OnItemClick() {
                    @Override
                    public void OnClick(int MovieId) {
                        SharedModel.setId(MovieId);
                        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_frame , new MovieFragment() , "movie")
                                .addToBackStack("movie").commit();
                    }
                });
            }
        });
    }
    private void getUpcoming(){
        binding.title.setText("Upcoming Movies");
        viewModel.getUpcoming();
        viewModel.Upcoming.observe(getViewLifecycleOwner(), new Observer<MovieModel>() {
            @Override
            public void onChanged(MovieModel movieModel) {
                adapter.setList((ArrayList<MovieModel.ResultsDTO>) movieModel.getResults());
                binding.recycler.setAdapter(adapter);
                binding.bar.setVisibility(View.GONE);
                adapter.setOnItemClick(new RecyclerCatsAdapter.OnItemClick() {
                    @Override
                    public void OnClick(int MovieId) {
                        SharedModel.setId(MovieId);
                        requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_frame , new MovieFragment() , "movie")
                                .addToBackStack("movie").commit();
                    }
                });
            }
        });

    }

}