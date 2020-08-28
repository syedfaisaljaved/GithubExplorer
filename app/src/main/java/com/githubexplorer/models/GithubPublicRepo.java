package com.githubexplorer.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GithubPublicRepo implements Parcelable {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("full_name")
    @Expose
    private String full_name;

    @SerializedName("owner")
    @Expose
    private Owner owner;

    @SerializedName("description")
    @Expose
    private String description;

    public GithubPublicRepo(String name, String full_name, Owner owner, String description) {
        this.name = name;
        this.full_name = full_name;
        this.owner = owner;
        this.description = description;
    }

    public GithubPublicRepo() {
    }

    protected GithubPublicRepo(Parcel in) {
        name = in.readString();
        full_name = in.readString();
        owner = in.readParcelable(Owner.class.getClassLoader());
        description = in.readString();
    }

    public static final Creator<GithubPublicRepo> CREATOR = new Creator<GithubPublicRepo>() {
        @Override
        public GithubPublicRepo createFromParcel(Parcel in) {
            return new GithubPublicRepo(in);
        }

        @Override
        public GithubPublicRepo[] newArray(int size) {
            return new GithubPublicRepo[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(full_name);
        parcel.writeParcelable(owner, i);
        parcel.writeString(description);
    }
}