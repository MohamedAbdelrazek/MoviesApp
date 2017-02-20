package com.zoka.moviesapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Mohamed AbdelraZek on 2/20/2017.
 */

public class MoviesFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.fragment_movies, container, false);

        return rootview;
    }

    private void geJsonResponse(URL queryURl) throws IOException {
        Log.i("ZOKA", "" + queryURl.toString());
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (com.android.volley.Request.Method.GET, queryURl.toString(), null, new com.android.volley.Response.Listener<JSONObject>() {


                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("ZOKA", "response" + response.toString());


                    }
                }, new com.android.volley.Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "" + error.toString(), Toast.LENGTH_SHORT).show();

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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.top_rated_id) {
            Toast.makeText(getActivity(), "Top", Toast.LENGTH_SHORT).show();
            try {
                URL url = Network.buildQueryParam(getString(R.string.top_rated));
                geJsonResponse(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return true;
        } else if (id == R.id.popular_id) {
            Toast.makeText(getActivity(), "popular", Toast.LENGTH_SHORT).show();
            try {
                URL url = Network.buildQueryParam(getString(R.string.popular));
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
