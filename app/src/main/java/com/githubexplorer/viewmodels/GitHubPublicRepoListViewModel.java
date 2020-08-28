package com.githubexplorer.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.githubexplorer.models.GithubPublicRepo;
import com.githubexplorer.repositories.GithubRepository;

import java.util.List;

public class GitHubPublicRepoListViewModel extends ViewModel {

    private GithubRepository githubRepository;

    public GitHubPublicRepoListViewModel() {
        githubRepository = GithubRepository.getInstance();
    }

    public LiveData<List<GithubPublicRepo>> getPublicRepo(){
        return githubRepository.getPublicRepo();
    }

    public void searchPublicRepo(){
        githubRepository.searchPublicRepo();
    }

}
