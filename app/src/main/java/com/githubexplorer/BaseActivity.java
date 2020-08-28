package com.githubexplorer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

public abstract class BaseActivity extends AppCompatActivity {

    private ProgressBar mProgressbar;
    private FrameLayout mFrameLayout;

    @Override
    public void setContentView(int layoutResID) {

        RelativeLayout relativeLayout = (RelativeLayout) getLayoutInflater().inflate(R.layout.activity_base,null);
        mProgressbar = relativeLayout.findViewById(R.id.progress_bar);
        mFrameLayout = relativeLayout.findViewById(R.id.activity_content);

        getLayoutInflater().inflate(layoutResID,mFrameLayout,true);
        super.setContentView(relativeLayout);
    }

    public void showProgressBar(boolean visibility){

        mProgressbar.setVisibility(visibility ? View.VISIBLE : View.INVISIBLE);
        mFrameLayout.setVisibility(visibility ? View.INVISIBLE : View.VISIBLE);

    }
}
