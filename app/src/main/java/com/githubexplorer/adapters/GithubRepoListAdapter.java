package com.githubexplorer.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.githubexplorer.R;
import com.githubexplorer.models.GithubPublicRepo;

import java.util.List;

public class GithubRepoListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<GithubPublicRepo> mPublicRepoList;
    private OnRepoClickListener onRepoClickListener;

    public GithubRepoListAdapter(OnRepoClickListener onRepoClickListener) {
        this.onRepoClickListener = onRepoClickListener;
    }

    public GithubRepoListAdapter() {
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_public_repo_list_item, parent, false);
        return new GithubPublicRepoListViewHolder(view, onRepoClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ((GithubPublicRepoListViewHolder)holder).repoName.setText(mPublicRepoList.get(position).getName());
        ((GithubPublicRepoListViewHolder)holder).repoFullname.setText(mPublicRepoList.get(position).getFull_name());

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_blank_image);

        Glide.with(holder.itemView.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(mPublicRepoList.get(position).getOwner().getAvatar_url())
                .apply(RequestOptions.circleCropTransform())
                .into(((GithubPublicRepoListViewHolder)holder).image);
    }

    @Override
    public int getItemCount() {
        if (mPublicRepoList != null){
            return mPublicRepoList.size();
        }
        return 0;
    }

    public void setPublicRepoList(List<GithubPublicRepo> repoList){
        mPublicRepoList = repoList;
        notifyDataSetChanged();
    }

    public GithubPublicRepo getSelectedRepo(int position){
        if (mPublicRepoList != null){
            if (mPublicRepoList.size() > 0){
                return mPublicRepoList.get(position);
            }
        }
        return null;
    }

    public class GithubPublicRepoListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView repoName, repoFullname;
        ImageView image;
        OnRepoClickListener onRepoClickListener;

        public GithubPublicRepoListViewHolder(@NonNull View itemView, OnRepoClickListener onRepoClickListener) {
            super(itemView);

            this.onRepoClickListener = onRepoClickListener;
            repoName = itemView.findViewById(R.id.repo_name);
            repoFullname = itemView.findViewById(R.id.repo_fullname);
            image = itemView.findViewById(R.id.image);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onRepoClickListener.onClick(getAdapterPosition());
        }
    }

    public interface OnRepoClickListener{

        void onClick(int position);
    }
}
