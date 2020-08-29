package com.githubexplorer.request;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.githubexplorer.util.AppExecuters;
import com.githubexplorer.models.GithubPublicRepo;
import com.githubexplorer.models.Owner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class GithubApiClient {

    private static final String TAG = "GithubApiClient";

    private static GithubApiClient instance;

    //public repo list
    private MutableLiveData<List<GithubPublicRepo>> mPublicRepoList;
    private RetrievePublicRepoRunnable retrievePublicRepoListRunnable;

    //repo contributers
    private MutableLiveData<List<Owner>> mContributerList;
    private RetrieveContributorRunnable retrieveContributorRunnable;

    //user repos
    private MutableLiveData<List<GithubPublicRepo>> mUserRepoList;
    private RetrieveUserRepoRunnable retrieveUserRepoRunnable;

    public static GithubApiClient getInstance(){
        if (instance == null){
            instance = new GithubApiClient();
        }
        return instance;
    }

    private GithubApiClient() {
        mPublicRepoList = new MutableLiveData<>();
        mContributerList = new MutableLiveData<>();
        mUserRepoList = new MutableLiveData<>();
    }

    public LiveData<List<GithubPublicRepo>> getPublicRepoList(){
        return mPublicRepoList;
    }

    public LiveData<List<GithubPublicRepo>> getUserRepoList(){
        return mUserRepoList;
    }

    public LiveData<List<Owner>> getContributorList(){
        return mContributerList;
    }

    public void searchPublicRepo(){
        if (retrievePublicRepoListRunnable != null){
            retrievePublicRepoListRunnable = null;
        }
        retrievePublicRepoListRunnable = new RetrievePublicRepoRunnable();
        AppExecuters.getInstance().networkIO().submit(retrievePublicRepoListRunnable);
    }

    public void fetchUserRepo(String username){
        if (retrieveUserRepoRunnable != null){
            retrieveUserRepoRunnable = null;
        }
        retrieveUserRepoRunnable = new RetrieveUserRepoRunnable(username);
        AppExecuters.getInstance().networkIO().submit(retrieveUserRepoRunnable);
    }

    public void fetchContributors(String user, String repo){
        if (retrieveContributorRunnable != null){
            retrieveContributorRunnable = null;
        }
        retrieveContributorRunnable = new RetrieveContributorRunnable(user, repo);
        AppExecuters.getInstance().networkIO().submit(retrieveContributorRunnable);
    }

    private class RetrievePublicRepoRunnable implements Runnable {

        public RetrievePublicRepoRunnable() {
        }

        @Override
        public void run() {

            try {
                Response<List<GithubPublicRepo>> response = getPublicRepo().execute();
                if (response.isSuccessful()){
                    if (response.body() != null) {
                        List<GithubPublicRepo> list = new ArrayList<>();
                        for (int i = 0; i < 20; i++) {
                            list = response.body();
                        }
                        mPublicRepoList.postValue(list);
                    }
                }else {
                    Log.d(TAG, "run: error "+ response.errorBody().string());
                    mPublicRepoList.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mPublicRepoList.postValue(null);
            }

        }

        private Call<List<GithubPublicRepo>> getPublicRepo(){
            return ServiceGenerator.getGithubApi().searchPublicRepositories();
        }

    }

    private class RetrieveUserRepoRunnable implements Runnable {

        private String username;

        public RetrieveUserRepoRunnable(String username) {
            this.username = username;
        }

        @Override
        public void run() {

            try {
                Response<List<GithubPublicRepo>> response = getUserRepo(username).execute();
                if (response.isSuccessful()){
                    if (response.body() != null) {
                        List<GithubPublicRepo> list = new ArrayList<>(response.body());
                        mUserRepoList.postValue(list);
                    }
                }else {
                    Log.d(TAG, "run: error "+ response.errorBody().string());
                    mUserRepoList.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mUserRepoList.postValue(null);
            }

        }

        private Call<List<GithubPublicRepo>> getUserRepo(String username){
            return ServiceGenerator.getGithubApi().getUserRepositories(username);
        }
    }

    private class RetrieveContributorRunnable implements Runnable {

        private String user;
        private String repo;

        public RetrieveContributorRunnable(String user, String repo) {
            this.user = user;
            this.repo = repo;
        }

        @Override
        public void run() {

            try {
                Response<List<Owner>> response = fetchContributors(user, repo).execute();
                if (response.isSuccessful()){
                    if (response.body() != null) {
                        List<Owner> list = new ArrayList<>(response.body());
                        mContributerList.postValue(list);
                    }
                }else {
                    Log.d(TAG, "run: error "+ response.errorBody().string());
                    mContributerList.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                mContributerList.postValue(null);
            }
        }

        private Call<List<Owner>> fetchContributors(String user, String repo){
            return ServiceGenerator.getGithubApi().getContributors(user, repo);
        }
    }
}
