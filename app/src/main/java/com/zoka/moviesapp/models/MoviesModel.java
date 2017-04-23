package com.zoka.moviesapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mohamed AbdelraZek on 3/3/2017.
 */

public class MoviesModel implements Parcelable {
    private String moviesId;
    private String moviesPosterPath;
    private String movieTitle;

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public MoviesModel()
    {

    }
    protected MoviesModel(Parcel in) {
        moviesId = in.readString();
        moviesPosterPath = in.readString();
        movieTitle=in.readString();
    }

    public static final Creator<MoviesModel> CREATOR = new Creator<MoviesModel>() {
        @Override
        public MoviesModel createFromParcel(Parcel in) {
            return new MoviesModel(in);
        }

        @Override
        public MoviesModel[] newArray(int size) {
            return new MoviesModel[size];
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
        dest.writeString(movieTitle);

    }
}
