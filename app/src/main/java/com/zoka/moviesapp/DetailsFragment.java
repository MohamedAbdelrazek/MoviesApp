package com.zoka.moviesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mohamed AbdelraZek on 2/21/2017.
 */

public class DetailsFragment extends Fragment {
    @BindView(R.id.ratingBar)
    RatingBar zRateView;
    @BindView(R.id.back_drop_path)
    ImageView BackDropImageView;
    @BindView(R.id.movie_title)
    TextView zTitleView;
    @BindView(R.id.movie_desc)
    TextView zDescription;
    @BindView(R.id.release_data)
    TextView zReleaseData;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View zRootView = inflater.inflate(R.layout.fragment_details, container, false);
        ButterKnife.bind(this, zRootView);
        Intent intent = getActivity().getIntent();
        MoviesModel moviesModel = (MoviesModel) intent.getSerializableExtra("value");
        zRateView.setRating(Float.parseFloat(moviesModel.getRate()) / 2);
        if (moviesModel.getBackDrop() != null) {
            Picasso.with(null).load(moviesModel.getBackDrop()).placeholder(R.drawable.place_holder).into(BackDropImageView);
        } else {
            Picasso.with(null).load(moviesModel.getPosterPath()).into(BackDropImageView);
        }
        zTitleView.setText(moviesModel.getTitle());
        zDescription.setText(moviesModel.getDesc());
        zReleaseData.setText(moviesModel.getDate());
        return zRootView;
    }
}
