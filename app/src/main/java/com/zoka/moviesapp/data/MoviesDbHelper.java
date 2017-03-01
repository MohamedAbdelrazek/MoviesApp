package com.zoka.moviesapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Mohamed AbdelraZek on 3/1/2017.
 */

public class MoviesDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 3;

    private static final String DATABASE_NAME = "weather.db";

    public MoviesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIE_TABLE = "CREATE TABLE " + MoviesContract.MoviesEntry.TABLE_NAME + " (" +
                MoviesContract.MoviesEntry.COLUMN_ID + " TEXT PRIMARY KEY  NOT NULL, " +
                MoviesContract.MoviesEntry.COLUMN_DATE + " TEXT, " +
                MoviesContract.MoviesEntry.COLUMN_BACK_DROP_PATH + " TEXT, " +
                MoviesContract.MoviesEntry.COLUMN_SORT_TYPE + " TEXT NOT NULL , " +
                MoviesContract.MoviesEntry.COLUMN_POSTER_PATH + " TEXT, " +
                MoviesContract.MoviesEntry.COLUMN_RATE + " TEXT, " +
                MoviesContract.MoviesEntry.COLUMN_DESCRIPTION + " TEXT, " +
                MoviesContract.MoviesEntry.COLUMN_TITLE + " TEXT);";

        final String SQL_CREATE_REVIEWS_TABLE = "CREATE TABLE " + MoviesContract.ReviewsEntry.TABLE_NAME + " (" +
                MoviesContract.ReviewsEntry.COLUMN_ID + " TEXT  NOT NULL, " +
                MoviesContract.ReviewsEntry.COLUMN_URL + " TEXT, " +
                MoviesContract.ReviewsEntry.COLUMN_AUTHOR_NAME + " TEXT UNIQUE NOT NULL, " +
                MoviesContract.ReviewsEntry.COLUMN_CONTENT + " TEXT);";

        final String SQL_CREATE_TRAILERS_TABLE = "CREATE TABLE " + MoviesContract.TrailersEntry.TABLE_NAME + " (" +
                MoviesContract.TrailersEntry.COLUMN_ID + " TEXT  NOT NULL, " +
                MoviesContract.TrailersEntry.COLUMN_KEY + " TEXT UNIQUE NOT NULL);";

        db.execSQL(SQL_CREATE_MOVIE_TABLE);
        db.execSQL(SQL_CREATE_REVIEWS_TABLE);
        db.execSQL(SQL_CREATE_TRAILERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MoviesContract.MoviesEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MoviesContract.ReviewsEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MoviesContract.TrailersEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}