package com.zoka.moviesapp.utils;

import android.net.Uri;

import com.zoka.moviesapp.BuildConfig;

import java.net.MalformedURLException;
import java.net.URL;

public class NetworkUtils {
    public static String BASE_URL = "http://api.themoviedb.org/3/movie";
    private static String API_KEY_PARAM = "api_key";
    public static String TOP_RATED = "top_rated";
    public static String POPULAR = "popular";

    public static URL buildQueryParam(String sort_path) throws MalformedURLException {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon().appendPath(sort_path)
                .query("").appendQueryParameter(API_KEY_PARAM, BuildConfig.API_KEY).build();

        return new URL(builtUri.toString());
    }
}
