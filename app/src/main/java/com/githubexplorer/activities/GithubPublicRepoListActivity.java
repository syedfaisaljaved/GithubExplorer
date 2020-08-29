package com.githubexplorer.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.githubexplorer.R;
import com.githubexplorer.adapters.GithubRepoListAdapter;
import com.githubexplorer.models.GithubPublicRepo;
import com.githubexplorer.viewmodels.GitHubPublicRepoListViewModel;

import java.util.List;



public class GithubPublicRepoListActivity extends BaseActivity implements GithubRepoListAdapter.OnRepoClickListener {

    private static final String TAG = "GithubPublicRepoListAct";

    //UI components
    private RecyclerView recyclerView;

    //vars
    private GitHubPublicRepoListViewModel mGitHubPublicRepoListViewModel;
    private GithubRepoListAdapter githubRepoListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_public_repo_list);

        recyclerView = findViewById(R.id.recycler_view);
        mGitHubPublicRepoListViewModel = new ViewModelProvider(this).get(GitHubPublicRepoListViewModel.class);

        showProgressBar(true);
        initRecyclerView();
        subscribeObservers();
        searchPublicRepo();
    }

    private void initRecyclerView() {
        githubRepoListAdapter = new GithubRepoListAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(githubRepoListAdapter);
    }

    private void subscribeObservers() {
        mGitHubPublicRepoListViewModel.getPublicRepo().observe(this, new Observer<List<GithubPublicRepo>>() {
            @Override
            public void onChanged(List<GithubPublicRepo> githubPublicRepos) {
                if (githubPublicRepos != null){
                    githubRepoListAdapter.setPublicRepoList(githubPublicRepos);
                    showProgressBar(false);
                }

            }
        });
    }

    public void searchPublicRepo() {
        mGitHubPublicRepoListViewModel.searchPublicRepo();
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(GithubPublicRepoListActivity.this, GithubRepoActivity.class);
        intent.putExtra("repo", githubRepoListAdapter.getSelectedRepo(position));
        startActivity(intent);
    }
}
