package com.zoka.moviesapp.sync;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;

import com.zoka.moviesapp.data.MoviesContract;
import com.zoka.moviesapp.utils.JsonUtils;
import com.zoka.moviesapp.utils.NetworkUtils;
import com.zoka.moviesapp.utils.PreferenceUtilities;

import org.json.JSONException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Mohamed AbdelraZek on 3/1/2017.
 */

public class MoviesSyncTask {

    private static ContentResolver contentResolver;

    synchronized public static void syncMovies(final Context context) {

        contentResolver = context.getContentResolver();

        try {
            //delete all the data
            // int del = contentResolver.delete(MoviesContract.MoviesEntry.CONTENT_URI, null, null);
            //Log.i("ZOKA", "deleted rows =" + del);

            //Storing Popular Movies .
            StoreMoviesData(PreferenceUtilities.POPULAR);
            //Storing Top rated Movies .
            StoreMoviesData(PreferenceUtilities.TOP_RATED);



        } catch (Exception e) {
            e.printStackTrace();

        }

    }

    private static void StoreMoviesData(String sortType) {
        URL url = null;
        try {
            url = NetworkUtils.buildQueryParam(sortType);
            String jsonRes = NetworkUtils.JsonResponse(url);
            ContentValues[] data = JsonUtils.getMoviesData(jsonRes, sortType);
            if (data != null && data.length > 0) {
                int inst = contentResolver.bulkInsert(MoviesContract.MoviesEntry.CONTENT_URI, data);


            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}

