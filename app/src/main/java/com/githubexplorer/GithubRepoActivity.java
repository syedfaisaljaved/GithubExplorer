package com.githubexplorer;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.githubexplorer.adapters.GithubContributorListAdapter;
import com.githubexplorer.models.GithubPublicRepo;
import com.githubexplorer.models.Owner;
import com.githubexplorer.viewmodels.GitHubContributorsViewModel;

import java.util.List;

public class GithubRepoActivity extends BaseActivity implements GithubContributorListAdapter.OnRepoClickListener {

    //UI components
    private TextView repoName, repoFullname, description;
    private ImageView image;
    private RecyclerView recyclerView;

    //vars
    private GitHubContributorsViewModel gitHubContributorsViewModel;
    private GithubContributorListAdapter githubContributorListAdapter;
    private GithubPublicRepo repo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo);

        repoName = findViewById(R.id.repo_name);
        repoFullname = findViewById(R.id.repo_fullname);
        description = findViewById(R.id.description_tv);
        image = findViewById(R.id.image);
        recyclerView = findViewById(R.id.recycler_view);
        gitHubContributorsViewModel = new ViewModelProvider(this).get(GitHubContributorsViewModel.class);

        showProgressBar(true);
        initRecyclerView();
        subscribeObservers();
        getIncomingIntent();
    }

    private void getIncomingIntent(){
        if (getIntent().hasExtra("repo")){
            repo = getIntent().getParcelableExtra("repo");
            setProperties();
            fetchContributors(repo.getOwner().getLogin(), repo.getName());
        }
    }


    private void initRecyclerView() {
        githubContributorListAdapter = new GithubContributorListAdapter(this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setAdapter(githubContributorListAdapter);
    }

    private void subscribeObservers() {
        gitHubContributorsViewModel.getContributors().observe(this, new Observer<List<Owner>>() {
            @Override
            public void onChanged(List<Owner> owners) {
                if (owners != null){
                    githubContributorListAdapter.setContributorList(owners);
                    showProgressBar(false);
                }
            }
        });
    }

    private void setProperties() {
        repoName.setText(repo.getName());
        repoFullname.setText(repo.getFull_name());
        description.setText(repo.getDescription());

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_blank_image);

        Glide.with(this)
                .setDefaultRequestOptions(requestOptions)
                .load(repo.getOwner().getAvatar_url())
                .apply(RequestOptions.circleCropTransform())
                .into(image);
    }

    public void fetchContributors(String user, String repo) {
        gitHubContributorsViewModel.fetchContributors(user, repo);
    }

    @Override
    public void onClick(int position) {
        Intent intent = new Intent(GithubRepoActivity.this, GithubUserActivity.class);
        intent.putExtra("user", githubContributorListAdapter.getSelectedContributor(position));
        startActivity(intent);
    }
}
