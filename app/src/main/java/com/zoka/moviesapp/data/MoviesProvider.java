package com.zoka.moviesapp.data;

import android.annotation.TargetApi;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Mohamed AbdelraZek on 3/1/2017.
 */

public class MoviesProvider extends ContentProvider {
    private MoviesDbHelper mOpenHelper;
    private static final UriMatcher sUriMatcher = buildUriMatcher();

    static final int CODE_MOVIES = 100;

    static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MoviesContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, MoviesContract.PATH_MOVIES, CODE_MOVIES);
        return matcher;
    }


    @Override
    public boolean onCreate() {
        mOpenHelper = new MoviesDbHelper(getContext());
        return true;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {
        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        switch (sUriMatcher.match(uri)) {

            case CODE_MOVIES:
                db.beginTransaction();
                int rowsInserted = 0;
                try {
                    for (ContentValues value : values) {
                        long _id = db.insert(MoviesContract.MoviesEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            rowsInserted++;
                        }
                    }
                    db.setTransactionSuccessful();
                } finally {
                    db.endTransaction();
                }

                if (rowsInserted > 0) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }

                return rowsInserted;

            default:
                return super.bulkInsert(uri, values);
        }
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor zCursor;
        SQLiteDatabase database = mOpenHelper.getReadableDatabase();
        int match = sUriMatcher.match(uri);
        switch (match) {
            case CODE_MOVIES:
                zCursor = database.query(MoviesContract.MoviesEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            default:
                throw new IllegalStateException("cant Query this URI ! ");

        }
        zCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return zCursor;
    }
    @Override
    public int delete(@NonNull Uri uri, String selection, String[] selectionArgs) {

        int numRowsDeleted;

        switch (sUriMatcher.match(uri)) {

            case CODE_MOVIES:
                numRowsDeleted = mOpenHelper.getWritableDatabase().delete(
                        MoviesContract.MoviesEntry.TABLE_NAME,
                        selection,
                        selectionArgs);

                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        /* If we actually deleted any rows, notify that a change has occurred to this URI */
        if (numRowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return numRowsDeleted;
    }


    @Nullable
    @Override
    public String getType(Uri uri) {
        throw new RuntimeException("We are not implementing getType in Movies App.");
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }



    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    @TargetApi(11)
    public void shutdown() {
        mOpenHelper.close();
        super.shutdown();
    }
}
