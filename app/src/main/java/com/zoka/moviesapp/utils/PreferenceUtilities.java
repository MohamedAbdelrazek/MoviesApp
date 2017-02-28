package com.zoka.moviesapp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Mohamed AbdelraZek on 2/28/2017.
 */

public class PreferenceUtilities {
    private static final String SORT_TYPE = "sort-type";
    private static final String DEFAULT_SORT = "popular";
    public static String TOP_RATED = "top_rated";
    public static String POPULAR = "popular";


    public static void setSortType(Context context, String sortType) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(SORT_TYPE, sortType);
        editor.apply();
    }

    public static String getSortType(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        String sortType = prefs.getString(SORT_TYPE, DEFAULT_SORT);
        return sortType;
    }
}
