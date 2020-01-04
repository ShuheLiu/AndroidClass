package com.example.myapplication.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by frank on 12/16/16.
 */

public class Client {

    // Trailing slash is needed
    public static final String BASE_URL = "http://192.168.1.15:8080/";
    //public static final String BASE_URL = "http://172.24.52.42:8080/";
    //public static final String BASE_URL = "http://10.0.2.2:8080/";

    public static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    //GitHubService service = retrofit.create(GitHubService.class);
}
