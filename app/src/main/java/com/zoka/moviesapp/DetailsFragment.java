package com.zoka.moviesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
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

public class DetailsFragment extends Fragment implements LoaderManager.LoaderCallbacks<MoviesModel> {
    private static final int LOADER_ID = 22;
    @BindView(R.id.ratingBar)
    RatingBar zRateView;
    @BindView(R.id.back_drop_path)
    ImageView zBackDropImageView;
    @BindView(R.id.movie_title)
    TextView zTitleView;
    @BindView(R.id.movie_desc)
    TextView zDescription;
    @BindView(R.id.release_data)
    TextView zReleaseData;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View zRootView = inflater.inflate(R.layout.fragment_details, container, false);
        ButterKnife.bind(this, zRootView);


        return zRootView;
    }


    @Override
    public Loader<MoviesModel> onCreateLoader(int id, Bundle args) {
        return new AsyncTaskLoader<MoviesModel>(getActivity()) {
            MoviesModel moviesModel;

            @Override
            protected void onStartLoading() {
                if (moviesModel == null) {
                    forceLoad();
                } else {
                    deliverResult(moviesModel);
                }
            }

            @Override
            public void deliverResult(MoviesModel data) {
                moviesModel = data;
                super.deliverResult(data);
            }

            @Override
            public MoviesModel loadInBackground() {
                Intent intent = getActivity().getIntent();
                MoviesModel moviesModel = (MoviesModel) intent.getSerializableExtra(Intent.EXTRA_TEXT);
                return moviesModel;
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<MoviesModel> loader, MoviesModel moviesModel) {
        zRateView.setRating(Float.parseFloat(moviesModel.getRate()) / 2);

        if (moviesModel.getBackDrop() != null) {
            Picasso.with(null).load(moviesModel.getBackDrop()).placeholder(R.drawable.place_holder).into(zBackDropImageView);
        } else {
            Picasso.with(null).load(moviesModel.getPosterPath()).into(zBackDropImageView);
        }
        zTitleView.setText(moviesModel.getTitle());
        zDescription.setText(moviesModel.getDesc());
        zReleaseData.setText(moviesModel.getDate());
    }

    @Override
    public void onLoaderReset(Loader<MoviesModel> loader) {
        zTitleView.setText("");
        zDescription.setText("");
        zReleaseData.setText("");
        zRateView.setRating(0);

    }
}
