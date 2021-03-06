package com.zoka.moviesapp.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.zoka.moviesapp.ClickListener;
import com.zoka.moviesapp.MoviesFragment;
import com.zoka.moviesapp.R;
import com.zoka.moviesapp.models.MoviesModel;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mohamed AbdelraZek on 2/20/2017.
 */


public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    private Cursor mCursor;
    private ClickListener mClickListener;

    public MoviesAdapter(Context context, ClickListener clickListener) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        mClickListener = clickListener;

    }

    public void swap(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.movie_single_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        mCursor.moveToPosition(position);
        String path_url = mCursor.getString(mCursor.getColumnIndex(MoviesFragment.MOVIES_POSTER_PATH));
        Glide.with(context).load(path_url).into(holder.posterImage);
        holder.posterImage.setContentDescription("Movie Name is " + mCursor.getString(mCursor.getColumnIndex(MoviesFragment.COLUMN_TITLE)));
    }

    @Override
    public int getItemCount() {
        if (null == mCursor) return 0;
        return mCursor.getCount();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.poster_img_view)
        ImageView posterImage;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCursor.moveToPosition(getAdapterPosition());
                    MoviesModel moviesModel = new MoviesModel();
                    moviesModel.setMoviesId(mCursor.getString(mCursor.getColumnIndex(MoviesFragment.MOVIES_POSTER_ID)));
                    moviesModel.setMoviesPosterPath(mCursor.getString(mCursor.getColumnIndex(MoviesFragment.MOVIES_POSTER_PATH)));
                    moviesModel.setMovieTitle(mCursor.getString(mCursor.getColumnIndex(MoviesFragment.COLUMN_TITLE)));
                    mClickListener.onItemClicked(moviesModel);

                }
            });

        }
    }
}