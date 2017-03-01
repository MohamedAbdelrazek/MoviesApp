package com.zoka.moviesapp.sync;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.zoka.moviesapp.data.MoviesContract;
import com.zoka.moviesapp.utils.JsonTest;
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
            StoreMoviesData(PreferenceUtilities.POPULAR);
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
            ContentValues[] data = JsonTest.getMoviesData(jsonRes, sortType);
            if (data != null && data.length > 0) {
                int inst = contentResolver.bulkInsert(MoviesContract.MoviesEntry.CONTENT_URI, data);
                Log.i("ZOKA", "inserted rows =" + inst);


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

