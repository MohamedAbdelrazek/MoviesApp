package com.zoka.moviesapp;

import android.view.View;

import com.zoka.moviesapp.models.MoviesModel;

/**
 * Created by Mohamed AbdelraZek on 2/21/2017.
 */

public interface ClickListener {
    public void OnItemClicked(View v , MoviesModel position);
}
