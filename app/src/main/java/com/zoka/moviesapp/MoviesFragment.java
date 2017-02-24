package com.zoka.moviesapp;

import android.content.Intent;
import android.os.Bundle;
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

import com.zoka.moviesapp.utils.ConstantUtils;
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
 * Created by Mohamed AbdelraZek on 2/20/2017.
 */

public class MoviesFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<MoviesModel>> {
    private static final String SORT_TYPE_EXTRA = "sort";
    private static final int LOADER_ID = 22;
    MoviesAdapter adapter;
    @BindView(R.id.recycler_view_id)
    RecyclerView zRecycler;
    private String mSortType = ConstantUtils.POPULAR;

    public String getSortType() {
        return mSortType;
    }

    public void setSortType(String sortType) {
        mSortType = sortType;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        getLoaderManager().initLoader(LOADER_ID, null, this);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        ButterKnife.bind(this, view);

        adapter = new MoviesAdapter(getActivity(), new ArrayList<MoviesModel>());
        zRecycler.setLayoutManager(new GridLayoutManager(getActivity(), calculateNoOfColumns()));
        zRecycler.setAdapter(adapter);
        adapter.setRecyclerListener(new ClickListener() {
            @Override
            public void OnItemClicked(View v, MoviesModel moviesModel) {
                Intent intent = new Intent(getContext(), DetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable(Intent.EXTRA_TEXT, moviesModel);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        return view;
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
            setSortType(ConstantUtils.TOP_RATED);
            getLoaderManager().restartLoader(LOADER_ID,null,this);
            return true;
        } else if (id == R.id.popular_id) {
            setSortType(ConstantUtils.POPULAR);
            getLoaderManager().restartLoader(LOADER_ID,null,this);
            return true;
        }
        else if (id == R.id.favourite_id) {
            Toast.makeText(getActivity(), "Favourite List no ready Yet!", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<ArrayList<MoviesModel>> onCreateLoader(int id, final Bundle args) {
        return new AsyncTaskLoader<ArrayList<MoviesModel>>(getActivity()) {
            @Override
            protected void onStartLoading() {
                forceLoad();
            }

            @Override
            public ArrayList<MoviesModel> loadInBackground() {

                try {
                    URL url = NetworkUtils.buildQueryParam(getSortType());

                    String jsonRes = NetworkUtils.JsonResponse(url);
                    return JsonUtils.getMoviesData(jsonRes);

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
    public void onLoadFinished(Loader<ArrayList<MoviesModel>> loader, ArrayList<MoviesModel> data) {
        adapter.swap(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<MoviesModel>> loader) {

    }
}
