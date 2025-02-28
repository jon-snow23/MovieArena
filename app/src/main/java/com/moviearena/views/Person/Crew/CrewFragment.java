package com.moviearena.views.Person.Crew;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.moviearena.R;
import com.moviearena.Utils.SharedModel;
import com.moviearena.adapters.RecyclerCrewMovieBigAdapter;
import com.moviearena.databinding.FragmentCrewBinding;
import com.moviearena.models.PersonMoviesModel;
import com.moviearena.views.Movie.MovieFragment;

import java.util.ArrayList;


public class CrewFragment extends Fragment {

    FragmentCrewBinding binding;
    RecyclerCrewMovieBigAdapter adapter = new RecyclerCrewMovieBigAdapter();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_crew, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding = FragmentCrewBinding.bind(view);

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requireActivity().onBackPressed();
            }
        });

        binding.title.setText(SharedModel.getSelectedactorname());
        adapter.setList((ArrayList<PersonMoviesModel.Crew>) SharedModel.getSelectedPersonMoviesModel().getCrew());
        binding.recycler.setAdapter(adapter);

        adapter.setOnItemClick(new RecyclerCrewMovieBigAdapter.OnItemClick() {
            @Override
            public void OnClick(int MovieId) {
                SharedModel.setId(MovieId);
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.home_frame , new MovieFragment() , "movie").commit();
            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}