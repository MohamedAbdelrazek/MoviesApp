package com.zoka.moviesapp.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.zoka.moviesapp.ClickListener;
import com.zoka.moviesapp.Models.MoviesModel;
import com.zoka.moviesapp.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mohamed AbdelraZek on 2/20/2017.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MyViewHolder> {
    private LayoutInflater inflater;
    private Context context;
    private List<MoviesModel> data;
    private ClickListener recyclerListener;

    public MoviesAdapter(Context context, List<MoviesModel> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    public void swap(ArrayList<MoviesModel> zData) {
        if (zData != null) {
            data.clear();
            data.addAll(zData);
            notifyDataSetChanged();
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.movie_single_item, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        MoviesModel currentMoviesData = data.get(position);
        Picasso.with(context).load(currentMoviesData.getPosterPath()).into(holder.posterImage);
    }

    public void setRecyclerListener(ClickListener recyclerListener) {
        this.recyclerListener = recyclerListener;


    }

    @Override
    public int getItemCount() {
        return data.size();
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
                    if (recyclerListener != null) {
                        recyclerListener.OnItemClicked(v, data.get(getAdapterPosition()));
                    }
                }
            });


        }
    }
}
