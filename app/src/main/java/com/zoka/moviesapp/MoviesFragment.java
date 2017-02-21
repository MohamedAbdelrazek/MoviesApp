package com.zoka.moviesapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.zoka.moviesapp.utils.NetworkUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mohamed AbdelraZek on 2/20/2017.
 */

public class MoviesFragment extends Fragment {
    MoviesAdapter adapter;
    @BindView(R.id.recycler_view_id)
    RecyclerView zRecycler;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        ButterKnife.bind(this, view);

        adapter = new MoviesAdapter(getActivity(), new ArrayList<MoviesModel>());
        zRecycler.setLayoutManager(new GridLayoutManager(getActivity(), calculateNoOfColumns()));
        zRecycler.setAdapter(adapter);
        adapter.setRecyclerListener(new MoveClicksFromRecyclerView() {
            @Override
            public void OnItemClicked(View v, MoviesModel moviesModel) {
                Intent intent = new Intent(getContext(), DetailsActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("value", moviesModel);
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
    public void onStart() {
        super.onStart();
        URL url = null;
        try {
            url = NetworkUtils.buildQueryParam(NetworkUtils.TOP_RATED);
            geJsonResponse(url);
        } catch (MalformedURLException e) {

        } catch (IOException e) {

        }

    }

    private void geJsonResponse(URL queryURl) throws IOException {
        Log.i("ZOKA", "" + queryURl.toString());
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (com.android.volley.Request.Method.GET, queryURl.toString(), null, new com.android.volley.Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            ArrayList<MoviesModel> data = JsonParser.JsonData(response);
                            adapter.swap(data);
                        } catch (JSONException e) {

                        }


                    }
                }, new com.android.volley.Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {


                    }
                });

        Volley.newRequestQueue(getActivity()).add(jsObjRequest);


    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.top_rated_id) {
            try {
                URL url = NetworkUtils.buildQueryParam(NetworkUtils.TOP_RATED);
                geJsonResponse(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return true;
        } else if (id == R.id.popular_id) {
            try {
                URL url = NetworkUtils.buildQueryParam(NetworkUtils.POPULAR);
                geJsonResponse(url);


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
