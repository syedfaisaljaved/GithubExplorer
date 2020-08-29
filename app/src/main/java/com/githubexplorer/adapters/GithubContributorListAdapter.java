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
import com.githubexplorer.models.Owner;

import java.util.List;

public class GithubContributorListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Owner> mContributorList;
    private OnRepoClickListener onRepoClickListener;

    public GithubContributorListAdapter(OnRepoClickListener onRepoClickListener) {
        this.onRepoClickListener = onRepoClickListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_contributor_list_item, parent, false);
        return new GithubContributorListViewHolder(view, onRepoClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((GithubContributorListViewHolder)holder).contributorName.setText(mContributorList.get(position).getLogin());

        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.ic_blank_image);

        Glide.with(holder.itemView.getContext())
                .setDefaultRequestOptions(requestOptions)
                .load(mContributorList.get(position).getAvatar_url())
                .apply(RequestOptions.circleCropTransform())
                .into(((GithubContributorListViewHolder)holder).image);
    }

    @Override
    public int getItemCount() {
        if (mContributorList != null){
            return mContributorList.size();
        }
        return 0;
    }

    public void setContributorList(List<Owner> contributorList){
        mContributorList = contributorList;
        notifyDataSetChanged();
    }

    public Owner getSelectedContributor(int position){
        if (mContributorList != null){
            if (mContributorList.size() > 0){
                return mContributorList.get(position);
            }
        }
        return null;
    }

    public class GithubContributorListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        TextView contributorName;
        ImageView image;
        OnRepoClickListener onRepoClickListener;

        public GithubContributorListViewHolder(@NonNull View itemView, OnRepoClickListener onRepoClickListener) {
            super(itemView);

            this.onRepoClickListener = onRepoClickListener;
            contributorName = itemView.findViewById(R.id.repo_name);
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
