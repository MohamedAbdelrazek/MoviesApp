package com.zoka.moviesapp;

import android.content.Intent;
import android.database.Cursor;
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
import com.zoka.moviesapp.adapters.ReviewAdapter;
import com.zoka.moviesapp.adapters.TrailersAdapter;
import com.zoka.moviesapp.data.MoviesContract;
import com.zoka.moviesapp.models.ReviewModel;
import com.zoka.moviesapp.models.TrailerModel;

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
    RatingBar mRate;
    @BindView(R.id.back_drop_path)
    ImageView mBackDropImage;
    @BindView(R.id.movie_title)
    TextView mMovieName;
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
    private static String mId;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(LOADER_ID, null, moviesLoaderCallbacks);
        //getLoaderManager().initLoader(REVIEW_LOADER_ID, null, reviewsLoaderCallbacks);
        //    getLoaderManager().initLoader(TRAILER_LOADER_ID, null, trailersLoaderCallbacks);
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
        Intent intent = getActivity().getIntent();
         mId = intent.getStringExtra(Intent.EXTRA_TEXT);
        return zRootView;
    }



    private LoaderManager.LoaderCallbacks<Cursor> moviesLoaderCallbacks = new LoaderManager.LoaderCallbacks<Cursor>() {

        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {
            return new AsyncTaskLoader<Cursor>(getContext()) {


                @Override
                protected void onStartLoading() {
                    forceLoad();
                }

                @Override
                public Cursor loadInBackground() {
                    String selection = MoviesContract.MoviesEntry.COLUMN_ID + "= ?";
                    String[] selectionArg = {mId};
                    return getContext().getContentResolver().query(MoviesContract.MoviesEntry.CONTENT_URI,
                            null,
                            selection,
                            selectionArg,
                            null);


                }
            };
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor mCursor) {
            if (mCursor != null) {
                mCursor.moveToFirst();
                mRate.setRating(Float.parseFloat(mCursor.getString(mCursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_RATE))) / 2);
                mReleaseData.setText(mCursor.getString(mCursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_DATE)));
                mDescription.setText(mCursor.getString(mCursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_DESCRIPTION)));
                mMovieName.setText(mCursor.getString(mCursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_TITLE)));
                String path = mCursor.getString(mCursor.getColumnIndex(MoviesContract.MoviesEntry.COLUMN_BACK_DROP_PATH));
                Picasso.with(getContext()).load(path).placeholder(R.drawable.place_holder_image_for_back_image).into(mBackDropImage);
            }

        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    };


}