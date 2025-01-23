package com.moviearena.views.Search;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.moviearena.R;
import com.moviearena.Utils.SharedModel;
import com.moviearena.adapters.RecyclerCatsAdapter;
import com.moviearena.databinding.FragmentSearchBinding;
import com.moviearena.models.MovieModel;
import com.moviearena.views.Movie.MovieFragment;
import com.moviearena.views.WatchList.BackListener;
import com.nafis.bottomnavigation.NafisBottomNavigation;

import java.util.ArrayList;


public class SearchFragment extends Fragment implements BackListener {


    FragmentSearchBinding binding;
    RecyclerCatsAdapter adapter = new RecyclerCatsAdapter();
    SearchViewModel viewModel ;
    NafisBottomNavigation nav;
    public static BackListener listener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentSearchBinding.bind(view);
        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        nav = getActivity().findViewById(R.id.nav);
        nav.setVisibility(View.VISIBLE);
        onClicks();
    }
    private void onClicks(){
        binding.search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                viewModel.Search(newText);
                viewModel.Movies.observe(getViewLifecycleOwner(), new Observer<MovieModel>() {
                    @Override
                    public void onChanged(MovieModel movieModel) {
                        binding.recycler.setVisibility(View.VISIBLE);
                        adapter.setList((ArrayList<MovieModel.ResultsDTO>) movieModel.getResults());
                        binding.recycler.setAdapter(adapter);

                        adapter.setOnItemClick(new RecyclerCatsAdapter.OnItemClick() {
                            @Override
                            public void OnClick(int MovieId) {
                                SharedModel.setId(MovieId);
                                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.home_frame , new MovieFragment() , "m")
                                        .addToBackStack("m").commit();
                            }
                        });

                    }
                });
                return false;
            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        listener = null;
    }
    @Override
    public void onPause() {
        super.onPause();
        listener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        listener = this;
    }
    @Override
    public void onBackPressed() {
        nav.show(1,true);
    }
}