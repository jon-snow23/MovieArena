package com.moviearena.network;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.moviearena.Utils.Consts;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroConnection {

    private static Retrofit retrofit;
    private static final Gson gson = new GsonBuilder().setLenient().create();

    private static synchronized Retrofit getRetrofit() {

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @NonNull
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                Request originalRequest = chain.request();
                HttpUrl originalUrl = originalRequest.url();

                // Add the API key as a query parameter
                HttpUrl urlWithApiKey = originalUrl.newBuilder()
                        .addQueryParameter("api_key", Consts.API_KEY)
                        .build();

                // Build the new request with the updated URL
                Request newRequest = originalRequest.newBuilder()
                        .url(urlWithApiKey)
                        .build();

                return chain.proceed(newRequest);
            }
        }).build();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(Consts.BASE_URL)
                    .client(client)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    public static RetroServices getServices() {
        return getRetrofit().create(RetroServices.class);
    }
}
