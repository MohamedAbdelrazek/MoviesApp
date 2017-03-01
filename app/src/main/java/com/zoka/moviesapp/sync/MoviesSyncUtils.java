package com.zoka.moviesapp.sync;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.zoka.moviesapp.data.MoviesContract;

/**
 * Created by Mohamed AbdelraZek on 3/1/2017.
 */

public class MoviesSyncUtils {
    private static boolean sInitialized;

    synchronized public static void initialize(@NonNull final Context context) {


        if (sInitialized) return;

        sInitialized = true;
        new AsyncTask<Void, Void, Void>() {
            @Override
            public Void doInBackground(Void... voids) {
                Log.i("ZOKA", "in back Ground !");
                Uri forecastQueryUri = MoviesContract.MoviesEntry.CONTENT_URI;

                String[] projectionColumns = {MoviesContract.MoviesEntry.COLUMN_ID};


                /* Here, we perform the query to check to see if we have any weather data */
                Cursor cursor = context.getContentResolver().query(
                        forecastQueryUri,
                        projectionColumns,
                        null,
                        null,
                        null);

              Log.i("ZOKA","cursor count "+  cursor.getCount());
                if (null == cursor || cursor.getCount() == 0) {
                    startImmediateSync(context);
                }

                /* Make sure to close the Cursor to avoid memory leaks! */
                cursor.close();
                return null;
            }
        }.execute();
    }


    public static void startImmediateSync(@NonNull final Context context) {
        Log.i("ZOKA","startImmediateSync Method calling !! ");
        Intent intentToSyncImmediately = new Intent(context, MoviesSyncIntentService.class);
        context.startService(intentToSyncImmediately);
    }
}
