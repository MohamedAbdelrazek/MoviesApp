package com.zoka.moviesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.zoka.moviesapp.Adapters.ReviewAdapter;
import com.zoka.moviesapp.Adapters.TrailersAdapter;
import com.zoka.moviesapp.Models.MoviesModel;
import com.zoka.moviesapp.Models.ReviewModel;
import com.zoka.moviesapp.Models.TrailerModel;
import com.zoka.moviesapp.utils.JsonUtils;
import com.zoka.moviesapp.utils.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mohamed AbdelraZek on 2/21/2017.
 */

public class DetailsFragment extends Fragment {
    private static final int LOADER_ID = 20;
    private static final int REVIEW_LOADER_ID = 30;
    private static final int TRAILER_LOADER_ID = 40;
    private ReviewAdapter mReviewAdapter;
    private TrailersAdapter mTrailersAdapter;
    @BindView(R.id.ratingBar)
    RatingBar mRateView;
    @BindView(R.id.back_drop_path)
    ImageView mBackDropImage;
    @BindView(R.id.movie_title)
    TextView mTitleView;
    @BindView(R.id.movie_desc)
    TextView mDescription;
    @BindView(R.id.release_data)
    TextView mReleaseData;
    @BindView(R.id.review_recycler)
    RecyclerView mReviewRecycler;
    @BindView(R.id.trailer_recycler)
    RecyclerView mTrailerRecycler;
    @BindView(R.id.review_title)
    TextView mReviewTitle;
    @BindView(R.id.trailer_title)
    TextView mTrailerTitle;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(LOADER_ID, null, moviesLoaderCallbacks);
        getLoaderManager().initLoader(REVIEW_LOADER_ID, null, reviewsLoaderCallbacks);
        getLoaderManager().initLoader(TRAILER_LOADER_ID, null, trailersLoaderCallbacks);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View zRootView = inflater.inflate(R.layout.fragment_details, container, false);
        ButterKnife.bind(this, zRootView);
        mReviewAdapter = new ReviewAdapter(getContext(), new ArrayList<ReviewModel>());
        mReviewRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        mReviewRecycler.setAdapter(mReviewAdapter);

        mTrailersAdapter = new TrailersAdapter(getActivity(), new ArrayList<TrailerModel>());
        mTrailerRecycler.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mTrailerRecycler.setAdapter(mTrailersAdapter);

        return zRootView;
    }

    private String getID() {
        Intent intent = getActivity().getIntent();
        MoviesModel moviesModel = (MoviesModel) intent.getParcelableExtra(Intent.EXTRA_TEXT);
        String id = moviesModel.getId();
        return id;
    }

    private LoaderManager.LoaderCallbacks<MoviesModel> moviesLoaderCallbacks = new LoaderManager.LoaderCallbacks<MoviesModel>() {
        @Override
        public Loader<MoviesModel> onCreateLoader(int id, Bundle args) {
            return new AsyncTaskLoader<MoviesModel>(getActivity()) {
                MoviesModel moviesModel = null;

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
                    MoviesModel moviesModel = intent.getParcelableExtra(Intent.EXTRA_TEXT);
                    return moviesModel;
                }
            };
        }

        @Override
        public void onLoadFinished(Loader<MoviesModel> loader, MoviesModel moviesModel) {
            mRateView.setRating(Float.parseFloat(moviesModel.getRate()) / 2);

            if (moviesModel.getBackDrop() != null) {
                Picasso.with(null).load(moviesModel.getBackDrop()).placeholder(R.drawable.place_holder).into(mBackDropImage);
            } else {
                Picasso.with(null).load(moviesModel.getPosterPath()).into(mBackDropImage);
            }
            mTitleView.setText(moviesModel.getTitle());
            mDescription.setText(moviesModel.getDesc());
            mReleaseData.setText(moviesModel.getDate());
        }

        @Override
        public void onLoaderReset(Loader<MoviesModel> loader) {
            mTitleView.setText("");
            mDescription.setText("");
            mReleaseData.setText("");
            mRateView.setRating(0);

        }


    };
    private LoaderManager.LoaderCallbacks<ArrayList<ReviewModel>> reviewsLoaderCallbacks = new LoaderManager.LoaderCallbacks<ArrayList<ReviewModel>>() {

        @Override
        public Loader<ArrayList<ReviewModel>> onCreateLoader(int id, Bundle args) {
            return new AsyncTaskLoader<ArrayList<ReviewModel>>(getActivity()) {
                ArrayList<ReviewModel> reviewModels = null;

                @Override
                protected void onStartLoading() {
                    if (reviewModels == null) {
                        forceLoad();
                    } else {
                        deliverResult(reviewModels);
                    }
                }

                @Override
                public void deliverResult(ArrayList<ReviewModel> data) {
                    reviewModels = data;
                    super.deliverResult(data);
                }

                @Override
                public ArrayList<ReviewModel> loadInBackground() {
                    String id = getID();
                    try {
                        URL url = NetworkUtils.buildQueryReviewParam(id);
                        String jsonStr = NetworkUtils.JsonResponse(url);
                        return JsonUtils.JsonReviewParser(jsonStr);

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            };
        }

        @Override
        public void onLoadFinished(Loader<ArrayList<ReviewModel>> loader, ArrayList<ReviewModel> data) {

            if (data.size() > 0) {
                mReviewTitle.setVisibility(View.VISIBLE);
                mReviewAdapter.swap(data);
            }
        }

        @Override
        public void onLoaderReset(Loader<ArrayList<ReviewModel>> loader) {

        }
    };
    private LoaderManager.LoaderCallbacks<ArrayList<TrailerModel>> trailersLoaderCallbacks = new LoaderManager.LoaderCallbacks<ArrayList<TrailerModel>>() {

        @Override
        public Loader<ArrayList<TrailerModel>> onCreateLoader(int id, Bundle args) {
            return new AsyncTaskLoader<ArrayList<TrailerModel>>(getActivity()) {
                ArrayList<TrailerModel> trailerModels = null;

                @Override
                protected void onStartLoading() {
                    if (trailerModels != null) {
                        deliverResult(trailerModels);
                    } else {
                        forceLoad();
                    }
                }

                @Override
                public void deliverResult(ArrayList<TrailerModel> data) {
                    trailerModels = data;
                    super.deliverResult(data);
                }

                @Override
                public ArrayList<TrailerModel> loadInBackground() {

                    String id = getID();
                    try {
                        URL url = NetworkUtils.buildQueryTrailerParam(id);
                        String jsonStr = NetworkUtils.JsonResponse(url);
                        return JsonUtils.JsonTrailerParser(jsonStr);

                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    return null;
                }
            };
        }

        @Override
        public void onLoadFinished(Loader<ArrayList<TrailerModel>> loader, ArrayList<TrailerModel> data) {

            if (data.size() > 0) {
                mTrailerTitle.setVisibility(View.VISIBLE);
                mTrailersAdapter.swap(data);

            }
        }

        @Override
        public void onLoaderReset(Loader<ArrayList<TrailerModel>> loader) {

        }
    };
}