package com.githubexplorer.request;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.githubexplorer.util.Constant.BASE_URL;

public class ServiceGenerator {

    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static GithubApi githubApi = retrofit.create(GithubApi.class);

    public static GithubApi getGithubApi(){
        return githubApi;
    }
}
