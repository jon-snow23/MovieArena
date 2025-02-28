package com.moviearena.views.Person;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import com.moviearena.Utils.SharedModel;
import com.moviearena.models.PersonDetailsModel;
import com.moviearena.models.PersonImagesModel;
import com.moviearena.models.PersonMoviesModel;
import com.moviearena.network.RetroConnection;

import java.util.Locale;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PersonViewModel extends ViewModel {

    MutableLiveData<PersonDetailsModel> Details = new MutableLiveData<>();
    MutableLiveData<PersonImagesModel> Images = new MutableLiveData<>();
    MutableLiveData<PersonMoviesModel> Movies = new MutableLiveData<>();

    public void getDetails(){
        RetroConnection.getServices().getPersonDetails(SharedModel.getCastId(), Locale.getDefault().getLanguage())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<PersonDetailsModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(PersonDetailsModel personDetailsModel) {
                        Details.setValue(personDetailsModel);
                        getImages();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    private void getImages(){
        RetroConnection.getServices().getPersonImages(SharedModel.getCastId(), Locale.getDefault().getLanguage())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<PersonImagesModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(PersonImagesModel personImagesModel) {
                        Images.setValue(personImagesModel);
                        getMovies();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

    }

    private void getMovies(){
        RetroConnection.getServices().getPersonMovies(SharedModel.getCastId(), Locale.getDefault().getLanguage())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<PersonMoviesModel>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(PersonMoviesModel personMoviesModel) {
                        Movies.setValue(personMoviesModel);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

    }
}
