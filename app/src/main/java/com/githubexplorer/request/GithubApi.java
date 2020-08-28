package com.githubexplorer.request;

import com.githubexplorer.models.GithubPublicRepo;
import com.githubexplorer.models.Owner;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GithubApi {

    @GET("repositories")
    Call<List<GithubPublicRepo>> searchPublicRepositories();

    @GET("repos/{user}/{repo}/contributors")
    Call<List<Owner>> getContributors(@Path("user") String user, @Path("repo") String repo);

    @GET("users/{username}/repos")
    Call<List<GithubPublicRepo>> getUserRepositories(@Path("username") String username);

}
