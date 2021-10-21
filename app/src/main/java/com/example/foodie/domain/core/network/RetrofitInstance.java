package com.example.foodie.domain.core.network;


import com.example.foodie.domain.core.Credentials;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitInstance {
    private static Retrofit.Builder retrofitBuilder =
            new Retrofit.Builder().baseUrl(Credentials.unSplashBaseUrl).
                    addConverterFactory(GsonConverterFactory.create());
    private static  Retrofit retrofit = retrofitBuilder.build();
   // private static MovieApi movieApi = retrofit.create(MovieApi.class);
   // public static MovieApi getMovieApi(){
       // return movieApi;
    //}
}
