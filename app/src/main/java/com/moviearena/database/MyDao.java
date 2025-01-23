package com.moviearena.database;


import static com.moviearena.Utils.Consts.query;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.moviearena.models.MovieDetailsModel;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao

public interface MyDao {


    @Insert
    Completable insert (List<MovieDetailsModel> list);

    @Query(query)

    Single <List<MovieDetailsModel>> getAll();


    @Delete
    Completable delete (MovieDetailsModel d);

}

