package com.zoka.moviesapp.sync;


import android.app.IntentService;
import android.content.Intent;

/**
 * Created by Mohamed AbdelraZek on 3/1/2017.
 */

public class MoviesSyncIntentService extends IntentService {

    public MoviesSyncIntentService() {
        super("MoviesSyncIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        MoviesSyncTask.syncMovies(this);

    }
}
