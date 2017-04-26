package com.zoka.moviesapp.widget;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.widget.AdapterView;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.squareup.picasso.Picasso;
import com.zoka.moviesapp.MoviesFragment;
import com.zoka.moviesapp.R;
import com.zoka.moviesapp.data.MoviesContract;
import com.zoka.moviesapp.utils.PreferenceUtilities;

import java.io.IOException;


public class MoviesWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new ListRemoteViewFactory();
    }

    public class ListRemoteViewFactory implements RemoteViewsFactory {

        private Cursor data = null;

        //Lifecycle start
        @Override
        public void onCreate() {
            //No action needed
        }

        @Override
        public void onDestroy() {
            if (data != null) {
                data.close();
                data = null;
            }

        }
        //Lifecycle end
        @Override
        public void onDataSetChanged() {
            if (data != null) data.close();

            final long identityToken = Binder.clearCallingIdentity();
            data = getContentResolver().query(MoviesContract.MoviesEntry.CONTENT_URI,
                    MoviesFragment.Movies_PROJECTION,
                    MoviesContract.MoviesEntry.COLUMN_SORT_TYPE + "= ?",
                    new String[]{PreferenceUtilities.getSortType(getBaseContext())},
                    null);
            Binder.restoreCallingIdentity(identityToken);
        }

        @Override
        public int getCount() {
            return data == null ? 0 : data.getCount();
        }

        @SuppressLint("PrivateResource")
        @Override
        public RemoteViews getViewAt(int position) {
            if (position == AdapterView.INVALID_POSITION || data == null
                    || !data.moveToPosition(position)) {
                return null;
            }

            final RemoteViews remoteViews = new RemoteViews(getPackageName(),
                    R.layout.movie_single_item);

            final String imageUrl = data.getString(data.getColumnIndex(MoviesFragment.MOVIES_POSTER_PATH));

            Bitmap b = null;
            try {
                b = Picasso.with(getBaseContext()).load(imageUrl).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            remoteViews.setImageViewBitmap(R.id.poster_img_view, b);
            return remoteViews;

        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int i) {
            return data.moveToPosition(i) ? data.getLong(0) : i;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }
}
