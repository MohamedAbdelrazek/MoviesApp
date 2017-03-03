package com.zoka.moviesapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
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

import com.facebook.stetho.Stetho;
import com.zoka.moviesapp.adapters.MoviesAdapter;
import com.zoka.moviesapp.data.MoviesContract;
import com.zoka.moviesapp.sync.MoviesSyncUtils;
import com.zoka.moviesapp.utils.PreferenceUtilities;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mohamed AbdelraZek on 2/20/2017.
 */

public class MoviesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, SharedPreferences.OnSharedPreferenceChangeListener {
    private static final int LOADER_ID = 22;
    private MoviesAdapter adapter;
    @BindView(R.id.recycler_view_id)
    RecyclerView mRecycler;
    public static final String[] Movies_PROJECTION = {
            MoviesContract.MoviesEntry.COLUMN_POSTER_PATH,
            MoviesContract.MoviesEntry.COLUMN_ID
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        MoviesSyncUtils.initialize(getContext());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        ButterKnife.bind(this, view);
        //for showing data base structure>
        Stetho.initializeWithDefaults(getContext());
        adapter = new MoviesAdapter(getContext(), new ClickListener() {
            @Override
            public void OnItemClicked(String movieId) {
                startActivity(new Intent(getContext(), DetailsActivity.class).putExtra(Intent.EXTRA_TEXT, movieId));

            }
        });
        mRecycler.setLayoutManager(new GridLayoutManager(getActivity(), calculateNoOfColumns()));
        mRecycler.setHasFixedSize(true);
        mRecycler.setAdapter(adapter);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        prefs.registerOnSharedPreferenceChangeListener(this);
        getLoaderManager().initLoader(LOADER_ID, null, this);
        return view;
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
        int noOfColumns = (int) (dpWidth / 300);
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


        return new AsyncTaskLoader<Cursor>(getContext()) {
            String selection;
            String[] selectionArg;

            @Override
            protected void onStartLoading() {
                forceLoad();
            }

            @Override
            public Cursor loadInBackground() {
                Uri uri;
                String sortType = PreferenceUtilities.getSortType(getContext());

                //   if (sortType.equals(PreferenceUtilities.FAVOURITE)) {

                //  } else {
                selection = MoviesContract.MoviesEntry.COLUMN_SORT_TYPE + "= ?";
                selectionArg = new String[]{sortType};
                uri = MoviesContract.MoviesEntry.CONTENT_URI;

                //}

                return getContext().getContentResolver().query(uri,
                        Movies_PROJECTION,
                        selection,
                        selectionArg,
                        null);


            }
        };
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
