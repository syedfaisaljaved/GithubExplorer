package com.githubexplorer;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.githubexplorer.adapters.GithubRepoListAdapter;
import com.githubexplorer.models.GithubPublicRepo;
import com.githubexplorer.models.Owner;
import com.githubexplorer.viewmodels.GitHubUserRepoListViewModel;

import java.util.List;

public class GithubUserActivity extends BaseActivity implements GithubRepoListAdapter.OnRepoClickListener {

    //UI components
    private TextView userName;
    private ImageView image;
    private RecyclerView recyclerView;

    //vars
    private GitHubUserRepoListViewModel gitHubUserRepoListViewModel;
    private GithubRepoListAdapter githubRepoListAdapter;
    private Owner user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        userName = findViewById(R.id.username);
        image = findViewById(R.id.image);
        recyclerView = findViewById(R.id.recycler_view);
        gitHubUserRepoListViewModel = new ViewModelProvider(this).get(GitHubUserRepoListViewModel.class);

        showProgressBar(true);
        initRecyclerView();
        subscribeObservers();
        getIncomingIntent();
    }

    private void getIncomingIntent(){
        if (getIntent().hasExtra("user")){
            user = getIntent().getParcelableExtra("user");
            setProperties();
            fetchUserRepositories(user.getLogin());
        }
    }


    private void initRecyclerView() {
        githubRepoListAdapter = new GithubRepoListAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(githubRepoListAdapter);
    }

    private void subscribeObservers() {
        gitHubUserRepoListViewModel.getUserRepo().observe(this, new Observer<List<GithubPublicRepo>>() {
            @Override
            public void onChanged(List<GithubPublicRepo> githubUserRepos) {
                if (githubUserRepos != null){
                    githubRepoListAdapter.setPublicRepoList(githubUserRepos);
                    showProgressBar(false);
                }

            }
        });
    }

    private void setProperties() {
        userName.setText(user.getLogin());

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_blank_image);

        Glide.with(this)
                .setDefaultRequestOptions(requestOptions)
                .load(user.getAvatar_url())
                .apply(RequestOptions.circleCropTransform())
                .into(image);
    }

    public void fetchUserRepositories(String username) {
        gitHubUserRepoListViewModel.fetchUSerRepositories(username);
    }

    @Override
    public void onClick(int position) {

    }
}
