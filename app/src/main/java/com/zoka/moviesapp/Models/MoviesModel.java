package com.zoka.moviesapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Mohamed AbdelraZek on 2/20/2017.
 */

public class MoviesModel implements Parcelable {
    private String Title;
    private String Desc;
    private String PosterPath;
    private String Date;
    private String BackDrop;
    private String Rate;
    private String id;
    public MoviesModel(){}

    protected MoviesModel(Parcel in) {
        Title = in.readString();
        Desc = in.readString();
        PosterPath = in.readString();
        Date = in.readString();
        BackDrop = in.readString();
        Rate = in.readString();
        id = in.readString();
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

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getDesc() {
        return Desc;
    }

    public void setDesc(String desc) {
        Desc = desc;
    }

    public String getPosterPath() {
        return PosterPath;
    }

    public void setPosterPath(String posterPath) {
        PosterPath = posterPath;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getBackDrop() {
        return BackDrop;
    }

    public void setBackDrop(String backDrop) {
        BackDrop = backDrop;
    }

    public String getRate() {
        return Rate;
    }

    public void setRate(String rate) {
        Rate = rate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(Title);
        dest.writeString(Desc);
        dest.writeString(PosterPath);
        dest.writeString(Date);
        dest.writeString(BackDrop);
        dest.writeString(Rate);
        dest.writeString(id);


    }
}
