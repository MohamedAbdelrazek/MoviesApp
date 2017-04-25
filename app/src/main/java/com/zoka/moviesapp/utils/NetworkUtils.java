package com.zoka.moviesapp.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import com.zoka.moviesapp.BuildConfig;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.Response;

public class NetworkUtils {
    public static final String YOUTUBE_BASE = "http://www.youtube.com/watch?v=";
    private static String BASE_URL = "http://api.themoviedb.org/3/movie";
    private static String API_KEY_PARAM = "api_key";
    private static String REVIEW_PATH = "reviews";
    private static String TRAILER_PATH = "videos";

    public static URL buildQueryParam(String sort_path) throws MalformedURLException {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon().appendPath(sort_path)
                .query("").appendQueryParameter(API_KEY_PARAM, BuildConfig.API_KEY).build();

        return new URL(builtUri.toString());
    }

    public static URL buildQueryReviewParam(String id) throws MalformedURLException {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon().appendPath(id).appendPath(REVIEW_PATH)
                .query("").appendQueryParameter(API_KEY_PARAM, BuildConfig.API_KEY).build();
        return new URL(builtUri.toString());
    }

    public static URL buildQueryTrailerParam(String id) throws MalformedURLException {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon().appendPath(id).appendPath(TRAILER_PATH)
                .query("").appendQueryParameter(API_KEY_PARAM, BuildConfig.API_KEY).build();
        return new URL(builtUri.toString());
    }

    public static String JsonResponse(URL url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        okhttp3.Request request = new okhttp3.Request.Builder()
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
