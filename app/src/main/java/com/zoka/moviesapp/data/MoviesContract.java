package com.zoka.moviesapp.data;

import android.provider.BaseColumns;

/**
 * Created by Mohamed AbdelraZek on 3/1/2017.
 */

public class MoviesContract {


    public class MoviesEntry implements BaseColumns {
        public static final String TABLE_NAME = "movies";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DESCRIPTION = "desc";
        public static final String COLUMN_POSTER_PATH = "poster_path";
        public static final String COLUMN_BACK_DROP_PATH = "back_drop";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_RATE = "rate";


    }

    public class ReviewsEntry implements BaseColumns {
        public static final String TABLE_NAME = "reviews";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_CONTENT = "content";
        public static final String COLUMN_AUTHOR_NAME= "authorName";



    }

    public class TrailersEntry implements BaseColumns {
        private String key;
        private String id;
        public static final String TABLE_NAME = "trailers";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_KEY = "key";


    }
}
