package com.githubexplorer.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.githubexplorer.models.GithubPublicRepo;
import com.githubexplorer.repositories.GithubRepository;

import java.util.List;

public class GitHubUserRepoListViewModel extends ViewModel {

    private GithubRepository githubRepository;

    public GitHubUserRepoListViewModel() {
        githubRepository = GithubRepository.getInstance();
    }

    public LiveData<List<GithubPublicRepo>> getUserRepo(){
        return githubRepository.getUserRepo();
    }

    public void fetchUSerRepositories(String username){
        githubRepository.fetchUSerRepositories(username);
    }
}
