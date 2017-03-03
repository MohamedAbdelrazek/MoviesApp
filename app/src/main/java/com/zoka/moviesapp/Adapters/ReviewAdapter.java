package com.zoka.moviesapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zoka.moviesapp.R;
import com.zoka.moviesapp.models.ReviewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Mohamed AbdelraZek on 2/27/2017.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewHolder> {
    private LayoutInflater inflater;
    private Context context;
    private List<ReviewModel> data;

    public ReviewAdapter(Context context, List<ReviewModel> data) {
        this.context = context;
        this.data = data;
        inflater = LayoutInflater.from(context);
    }

    public void swap(ArrayList<ReviewModel> zData) {
        if (zData != null) {
            data.clear();
            data.addAll(zData);
            notifyDataSetChanged();
        }

    }

    @Override
    public ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.review_single_item, parent, false);
        ReviewHolder reviewHolder = new ReviewHolder(view);
        return reviewHolder;
    }

    @Override
    public void onBindViewHolder(ReviewHolder holder, int position) {

        ReviewModel currentReviewData = data.get(position);
        holder.authorName.setText(CapsInit(currentReviewData.getAuthorName()));
        holder.content.setText(currentReviewData.getContent());
        holder.url.setText(currentReviewData.getUrl());


    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ReviewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.txt_view_author_id)
        TextView authorName;
        @BindView(R.id.txt_view_content_id)
        TextView content;
        @BindView(R.id.txt_view_url_id)
        TextView url;

        public ReviewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);


        }
    }

    private static String CapsInit(String name) {
        String capInitName = name.substring(0, 1).toUpperCase() + name.substring(1);
        return capInitName;
    }

}

