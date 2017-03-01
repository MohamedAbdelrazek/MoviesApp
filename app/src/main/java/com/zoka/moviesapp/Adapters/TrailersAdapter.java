package com.zoka.moviesapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.zoka.moviesapp.R;
import com.zoka.moviesapp.models.TrailerModel;
import com.zoka.moviesapp.utils.NetworkUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mohamed AbdelraZek on 2/27/2017.
 */

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailerHolder> {
    private LayoutInflater inflater;
    private Context context;
    private List<TrailerModel> data;

    public TrailersAdapter(Context context, List<TrailerModel> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    public void swap(ArrayList<TrailerModel> zData) {
        if (zData != null) {
            data.clear();
            data.addAll(zData);
            notifyDataSetChanged();
        }

    }


    @Override
    public TrailerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.trailer_single_item, parent, false);
        TrailerHolder trailerHolder = new TrailerHolder(view);
        return trailerHolder;

    }

    @Override
    public void onBindViewHolder(TrailerHolder holder, int position) {
        TrailerModel currentTrailerData = data.get(position);
        Picasso.with(context)
                .load(ConstructThumbnailUrl(currentTrailerData.getKey()))
                .into(holder.trailerImage);


    }

    private static String ConstructThumbnailUrl(String key) {
        return "http://img.youtube.com/vi/" + key + "/0.jpg";
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class TrailerHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_thumb)
        ImageView trailerImage;

        public TrailerHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent videoClient = new Intent(Intent.ACTION_VIEW);
                    videoClient.setData(Uri.parse(NetworkUtils.YOUTUBE_BASE + data.get(getAdapterPosition()).getKey()));
                    context.startActivity(videoClient);

                }
            });


        }
    }

}
