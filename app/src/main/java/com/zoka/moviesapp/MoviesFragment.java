package com.zoka.moviesapp;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.facebook.stetho.Stetho;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.zoka.moviesapp.adapters.MoviesAdapter;
import com.zoka.moviesapp.data.MoviesContract;
import com.zoka.moviesapp.models.MoviesModel;
import com.zoka.moviesapp.sync.MoviesSyncUtils;
import com.zoka.moviesapp.utils.PreferenceUtilities;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mohamed AbdelraZek on 2/20/2017.
 */

public class MoviesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, SharedPreferences.OnSharedPreferenceChangeListener {
    public final static String MOVIES_POSTER_PATH = "poster_path";
    public final static String MOVIES_POSTER_ID = "id";
    public static final String COLUMN_TITLE = "title";
    private static final int LOADER_ID = 22;
    private static final String[] Movies_PROJECTION = {
            MOVIES_POSTER_PATH, MOVIES_POSTER_ID, COLUMN_TITLE
    };
    private static final String[] Movies_Fav_PROJECTION = {
            MOVIES_POSTER_PATH, MOVIES_POSTER_ID, COLUMN_TITLE

    };
    private static MoviesListener mMoviesListener;
    @BindView(R.id.recycler_view_id)
    RecyclerView mRecycler;
    FrameLayout mFrameLayout;
    private MoviesAdapter adapter;
    private Tracker mTracker;

    public static void setMoviesListener(MoviesListener moviesListener) {
        mMoviesListener = moviesListener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        MoviesSyncUtils.initialize(getContext());
        AnalyticsApplication application = (AnalyticsApplication) getActivity().getApplication();
        mTracker = application.getDefaultTracker();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(LOADER_ID, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        ButterKnife.bind(this, view);
        Stetho.initializeWithDefaults(getContext());
        adapter = new MoviesAdapter(getContext(), new ClickListener() {

            @Override
            public void onItemClicked(MoviesModel moviesModel) {
                mMoviesListener.setMovies(moviesModel);


            }
        });
        mFrameLayout = (FrameLayout) view.findViewById(R.id.fragment_movies);
        mRecycler.setLayoutManager(new GridLayoutManager(getActivity(), calculateNoOfColumns()));
        mRecycler.setHasFixedSize(true);
        mRecycler.setAdapter(adapter);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        prefs.registerOnSharedPreferenceChangeListener(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mTracker.setScreenName("Movies Fragment screen !");
        mTracker.send(new HitBuilders.ScreenViewBuilder().build());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        prefs.unregisterOnSharedPreferenceChangeListener(this);
    }

    private int calculateNoOfColumns() {
        DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();
        float dpWidth = displayMetrics.widthPixels;
        int noOfColumns;
        if (mFrameLayout == null) {
            noOfColumns = (int) (dpWidth / 300);

        } else {
            noOfColumns = (int) (dpWidth / 600);
        }
        return noOfColumns;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.top_rated_id) {
            PreferenceUtilities.setSortType(getContext(), PreferenceUtilities.TOP_RATED);
            return true;
        } else if (id == R.id.popular_id) {
            PreferenceUtilities.setSortType(getContext(), PreferenceUtilities.POPULAR);
            return true;
        } else if (id == R.id.favourite_id) {
            PreferenceUtilities.setSortType(getContext(), PreferenceUtilities.FAVOURITE);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        getLoaderManager().restartLoader(LOADER_ID, null, this);

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String selection;
        String[] selectionArg;
        String sortType = PreferenceUtilities.getSortType(getContext());

        if (sortType.equals(PreferenceUtilities.FAVOURITE)) {
            return new CursorLoader(getContext(), MoviesContract.FavouriteMoviesEntry.CONTENT_URI, Movies_Fav_PROJECTION, null, null, null);
        } else {
            selection = MoviesContract.MoviesEntry.COLUMN_SORT_TYPE + "= ?";
            selectionArg = new String[]{sortType};
            return new CursorLoader(getContext(), MoviesContract.MoviesEntry.CONTENT_URI, Movies_PROJECTION, selection, selectionArg, null);
        }

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        adapter.swap(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swap(null);

    }
}
