package com.zoka.moviesapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mohamed AbdelraZek on 3/3/2017.
 */

public class FavouriteMoviesModel implements Parcelable {
    private String moviesId;
    private String moviesPosterPath;

    public FavouriteMoviesModel()
    {

    }
    protected FavouriteMoviesModel(Parcel in) {
        moviesId = in.readString();
        moviesPosterPath = in.readString();
    }

    public static final Creator<FavouriteMoviesModel> CREATOR = new Creator<FavouriteMoviesModel>() {
        @Override
        public FavouriteMoviesModel createFromParcel(Parcel in) {
            return new FavouriteMoviesModel(in);
        }

        @Override
        public FavouriteMoviesModel[] newArray(int size) {
            return new FavouriteMoviesModel[size];
        }
    };

    public String getMoviesId() {
        return moviesId;
    }

    public void setMoviesId(String moviesId) {
        this.moviesId = moviesId;
    }

    public String getMoviesPosterPath() {
        return moviesPosterPath;
    }

    public void setMoviesPosterPath(String moviesPosterPath) {
        this.moviesPosterPath = moviesPosterPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(moviesId);
        dest.writeString(moviesPosterPath);

    }
}
