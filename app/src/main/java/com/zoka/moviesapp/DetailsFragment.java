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

/**
 * Created by Mohamed AbdelraZek on 2/21/2017.
 */

public class DetailsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);

        Intent intent = getActivity().getIntent();
        MoviesModel moviesModel = (MoviesModel) intent.getSerializableExtra("value");
        RatingBar RateView = (RatingBar) rootView.findViewById(R.id.ratingBar);
        ImageView BackDropImageView = (ImageView) rootView.findViewById(R.id.back_drop_path);
        TextView TitleView = (TextView) rootView.findViewById(R.id.movie_title);
        TextView Description = (TextView) rootView.findViewById(R.id.movie_desc);
        TextView ReleaseData = (TextView) rootView.findViewById(R.id.release_data);
        RateView.setRating(Float.parseFloat(moviesModel.getRate()) / 2);

        if (moviesModel.getBackDrop() != null) {
            Picasso.with(null).load(moviesModel.getBackDrop()).into(BackDropImageView);
        } else {
            Picasso.with(null).load(moviesModel.getPosterPath()).into(BackDropImageView);
        }
        TitleView.setText(moviesModel.getTitle());
        Description.setText(moviesModel.getDesc());
        ReleaseData.setText(moviesModel.getDate());
        return rootView;
    }
}
