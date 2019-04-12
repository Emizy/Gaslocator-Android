package com.example.hp.nevogas.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by HP on 3/14/2019.
 */

public class ApiClient {
    private static final String BASE_URL = "https://gaslocator.000webhostapp.com/api/";
    private static ApiClient apiClient;
    private Retrofit retrofit;

    private ApiClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized ApiClient getInstance(){
        if (apiClient == null){
            apiClient = new ApiClient();
        }
        return apiClient;
    }

    public Api getApi(){
        return retrofit.create(Api.class);
    }
}
