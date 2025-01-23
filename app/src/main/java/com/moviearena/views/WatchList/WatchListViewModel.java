package com.moviearena.views.WatchList;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.moviearena.database.MyRoomDatabase;
import com.moviearena.models.MovieDetailsModel;

import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class WatchListViewModel extends ViewModel {

    MutableLiveData<List<MovieDetailsModel>> LocalList = new MutableLiveData<>();
    MutableLiveData<Integer> error = new MutableLiveData<>();

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

}
