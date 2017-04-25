package com.zoka.moviesapp.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Mohamed AbdelraZek on 3/1/2017.
 */

public class MoviesContract {
    public static final String CONTENT_AUTHORITY = "com.zoka.moviesapp";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_MOVIES = "movies";
    public static final String PATH_FAVOURITE_MOVIES = "favourite_movies";


    public static class MoviesEntry implements BaseColumns {

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_MOVIES)
                .build();
        public static final String TABLE_NAME = "movies";
        public static final String COLUMN_SORT_TYPE = "sort";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "desc";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_BACK_DROP_PATH = "back_drop";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_RATE = "rate";
    }

    public static class FavouriteMoviesEntry implements BaseColumns {
        public static final String TABLE_NAME = "favourite_movies";
        public static final String COLUMN_FAVOURITE_MOVIE_ID = "id";
        public static final String COLUMN_TITLE = "title";
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_FAVOURITE_MOVIES)
                .build();

        public static final String COLUMN_POSTER_PATH = "poster_path";
    }
}
