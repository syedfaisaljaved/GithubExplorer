package com.githubexplorer.repositories;

import androidx.lifecycle.LiveData;

import com.githubexplorer.models.GithubPublicRepo;
import com.githubexplorer.models.Owner;
import com.githubexplorer.request.GithubApiClient;

import java.util.List;

public class GithubRepository {

    private static GithubRepository instance;
    private GithubApiClient githubApiClient;

    public static GithubRepository getInstance(){
        if (instance == null){
            instance = new GithubRepository();
        }
        return instance;
    }

    private GithubRepository() {
        this.githubApiClient = GithubApiClient.getInstance();
    }

    public LiveData<List<GithubPublicRepo>> getPublicRepo(){
        return githubApiClient.getPublicRepoList();
    }

    public LiveData<List<GithubPublicRepo>> getUserRepo(){
        return githubApiClient.getUserRepoList();
    }

    public LiveData<List<Owner>> getContributors(){
        return githubApiClient.getContributorList();
    }

    public void searchPublicRepo(){
        githubApiClient.searchPublicRepo();
    }

    public void fetchContributors(String user, String repo){
        githubApiClient.fetchContributors(user,repo);
    }

    public void fetchUSerRepositories(String username){
        githubApiClient.fetchUserRepo(username);
    }

}
