package com.zoka.moviesapp;

import android.net.Uri;

import java.net.MalformedURLException;
import java.net.URL;

public class Network {
    public static String BASE_URL = "http://api.themoviedb.org/3/movie";
    private static String API_KEY_PARAM = "api_key";
    private static String API_KEY = "5764dbdfaaaae09ae32bd0f0c85cf2a5";

    public static URL buildQueryParam(String sort_path) throws MalformedURLException {
        Uri builtUri = Uri.parse(BASE_URL).buildUpon().appendPath(sort_path)
                .query("").appendQueryParameter(API_KEY_PARAM, API_KEY).build();

        return new URL(builtUri.toString());
    }
}
