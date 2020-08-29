package com.githubexplorer.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.githubexplorer.models.GithubPublicRepo;
import com.githubexplorer.models.Owner;
import com.githubexplorer.repositories.GithubRepository;

import java.util.List;

public class GitHubContributorsViewModel extends ViewModel {

    private GithubRepository githubRepository;

    public GitHubContributorsViewModel() {
        githubRepository = GithubRepository.getInstance();
    }

    public LiveData<List<Owner>> getContributors(){
        return githubRepository.getContributors();
    }

    public void fetchContributors(String user, String repo){
        githubRepository.fetchContributors(user,repo);
    }

}
