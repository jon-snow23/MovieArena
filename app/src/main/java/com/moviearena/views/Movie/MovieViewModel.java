package com.moviearena.views.Movie;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.moviearena.Utils.SharedModel;
import com.moviearena.database.MyRoomDatabase;
import com.moviearena.models.MovieDetailsModel;
import com.moviearena.models.MovieImagesModel;
import com.moviearena.models.MovieModel;
import com.moviearena.models.PersonModel;
import com.moviearena.models.VideoModel;
import com.moviearena.network.RetroConnection;

import java.util.List;
import java.util.Locale;

import io.reactivex.CompletableObserver;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MovieViewModel extends ViewModel {

    MutableLiveData<MovieDetailsModel> MovieDetails = new MutableLiveData<>();
    MutableLiveData<MovieModel> SimilarMovies =new MutableLiveData<>();
    MutableLiveData<PersonModel> Credits = new MutableLiveData<>();
    MutableLiveData<MovieImagesModel> Images = new MutableLiveData<>();
    MutableLiveData<VideoModel> Videos = new MutableLiveData<>();
    MutableLiveData<List<MovieDetailsModel>> LocalList = new MutableLiveData<>();
    MutableLiveData<Integer> error = new MutableLiveData<>();

    MutableLiveData<Integer> Cached = new MutableLiveData<>();
    MutableLiveData<Integer> Removed = new MutableLiveData<>();

    public void getMovieDetails(){
        RetroConnection.getServices().getMovieDetails(SharedModel.getId() , Locale.getDefault().getLanguage())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<MovieDetailsModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(MovieDetailsModel movieDetailsModel) {
                        MovieDetails.setValue(movieDetailsModel);
                        getImages();
                    }

                    @Override
                    public void onError(Throwable e) {
                        //getMovieDetails();
                        Log.e("TAG", "onError: details"+SharedModel.getId());

                    }
                });
    }
    private void getImages(){
        RetroConnection.getServices().getImages(SharedModel.getId(), Locale.getDefault().getLanguage())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<MovieImagesModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(MovieImagesModel movieImagesModel) {
                        Images.setValue(movieImagesModel);
                        getVideos();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG", "onError: images" );

                    }
                });
    }
    private void getVideos(){
        RetroConnection.getServices().getVideos(SharedModel.getId(), Locale.getDefault().getLanguage())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<VideoModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(VideoModel videoModel) {
                        Videos.setValue(videoModel);
                        getCast();

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }
    private void getCast(){
        RetroConnection.getServices().getCredits(SharedModel.getId(), Locale.getDefault().getLanguage())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<PersonModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(PersonModel personModel) {
                        Credits.setValue(personModel);
                        getSimilar();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG", "onError: cast" );

                    }
                });
    }
    private void getSimilar(){
        RetroConnection.getServices().getSimilar(SharedModel.getId(), Locale.getDefault().getLanguage())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<MovieModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(MovieModel movieModel) {
                        SimilarMovies.setValue(movieModel);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("TAG", "onError: sim" );

                    }
                });
    }

    public void getLocalMovies(Context context){
        MyRoomDatabase.getInstance(context).getDao().getAll()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<MovieDetailsModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(List<MovieDetailsModel> movieDetailsModels) {
                        LocalList.setValue(movieDetailsModels);
                    }

                    @Override
                    public void onError(Throwable e) {
                        error.setValue(1);

                    }
                });
    }


    public  void cache(Context context , List<MovieDetailsModel> list){

        MyRoomDatabase.getInstance(context).getDao().insert(list).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Cached.setValue(1);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });

    }

    public  void delete(Context context , MovieDetailsModel model){

        MyRoomDatabase.getInstance(context).getDao().delete(model).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new CompletableObserver() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onComplete() {
                        Removed.setValue(1);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }
                });

    }

}
