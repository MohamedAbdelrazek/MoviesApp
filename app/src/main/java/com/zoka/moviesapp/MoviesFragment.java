package com.zoka.moviesapp;

import android.content.SharedPreferences;
import android.database.Cursor;
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
import android.widget.Toast;

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

public class MoviesFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>,SharedPreferences.OnSharedPreferenceChangeListener {
    private static final String SORT_TYPE_EXTRA = "sort";
    private static final int LOADER_ID = 22;
    MoviesAdapter adapter;
    @BindView(R.id.recycler_view_id)
    RecyclerView zRecycler;
    public static final String[] Movies_PROJECTION = {
            MoviesContract.MoviesEntry.COLUMN_POSTER_PATH,
            MoviesContract.MoviesEntry.COLUMN_ID
    };

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getLoaderManager().initLoader(LOADER_ID, null, this);
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
            public void OnItemClicked(String MovieId) {
                Toast.makeText(getContext(), ""+MovieId, Toast.LENGTH_SHORT).show();

            }
        });
        zRecycler.setLayoutManager(new GridLayoutManager(getActivity(), calculateNoOfColumns()));
        zRecycler.setHasFixedSize(true);
        zRecycler.setAdapter(adapter);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        prefs.registerOnSharedPreferenceChangeListener(this);

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
            Toast.makeText(getActivity(), "Favourite List no ready Yet!", Toast.LENGTH_SHORT).show();
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
            @Override
            protected void onStartLoading() {
                forceLoad();
            }

            @Override
            public Cursor loadInBackground() {
                String selection = MoviesContract.MoviesEntry.COLUMN_SORT_TYPE + "= ?";
                String[] selectionArg = {PreferenceUtilities.getSortType(getContext())};
                return getContext().getContentResolver().query(MoviesContract.MoviesEntry.CONTENT_URI,
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
