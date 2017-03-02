package com.zoka.moviesapp.utils;

/**
 * Created by Mohamed AbdelraZek on 2/20/2017.
 */

import android.content.ContentValues;

import com.zoka.moviesapp.models.ReviewModel;
import com.zoka.moviesapp.models.TrailerModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.zoka.moviesapp.data.MoviesContract.MoviesEntry.COLUMN_BACK_DROP_PATH;
import static com.zoka.moviesapp.data.MoviesContract.MoviesEntry.COLUMN_DATE;
import static com.zoka.moviesapp.data.MoviesContract.MoviesEntry.COLUMN_DESCRIPTION;
import static com.zoka.moviesapp.data.MoviesContract.MoviesEntry.COLUMN_ID;
import static com.zoka.moviesapp.data.MoviesContract.MoviesEntry.COLUMN_POSTER_PATH;
import static com.zoka.moviesapp.data.MoviesContract.MoviesEntry.COLUMN_RATE;
import static com.zoka.moviesapp.data.MoviesContract.MoviesEntry.COLUMN_SORT_TYPE;
import static com.zoka.moviesapp.data.MoviesContract.MoviesEntry.COLUMN_TITLE;

public class JsonUtils {
    public static ContentValues[] getMoviesData(String jsonString, String sortType)
            throws JSONException {

        ContentValues[] contentValues;
        JSONObject jsonObject = new JSONObject(jsonString);

        final String MDB_MOVIE_POSTER = "poster_path";
        final String MDB_MOVIE_TITLE = "title";
        final String MDB_MOVIE_BACKDROP_PHOTO = "backdrop_path";
        final String MDB_MOVIE_DATE = "release_date";
        final String MDB_MOVIE_DESC = "overview";
        final String MDB_MOVIE_RATE = "vote_average";
        final String MDB_ID = "id";
        final String results = "results";
        String Description;
        String Title;
        String BackDropPhoto;
        String PosterPathPhoto;
        String Date;
        String Rate;
        String id;
        

        JSONArray movieArray = jsonObject.getJSONArray(results);
        contentValues = new ContentValues[movieArray.length()];


        for (int i = 0; i < movieArray.length(); i++) {
            ContentValues values = new ContentValues();

            JSONObject MoviesObject = movieArray.getJSONObject(i);
            BackDropPhoto = FormatBackDropImage(MoviesObject.getString(MDB_MOVIE_BACKDROP_PHOTO));
            PosterPathPhoto = FormatPosterImage(MoviesObject.getString(MDB_MOVIE_POSTER));
            Title = MoviesObject.getString(MDB_MOVIE_TITLE);
            Date = MoviesObject.getString(MDB_MOVIE_DATE);
            Description = MoviesObject.getString(MDB_MOVIE_DESC);
            Rate = MoviesObject.getString(MDB_MOVIE_RATE);
            id = MoviesObject.getString(MDB_ID);
            values.put(COLUMN_SORT_TYPE, sortType);
            values.put(COLUMN_ID, id);
            values.put(COLUMN_BACK_DROP_PATH, BackDropPhoto);
            values.put(COLUMN_POSTER_PATH, PosterPathPhoto);
            values.put(COLUMN_TITLE, Title);
            values.put(COLUMN_DESCRIPTION, Description);
            values.put(COLUMN_RATE, Rate);
            values.put(COLUMN_DATE, Date);
            contentValues[i] = values;
            //===============================================================================================
        }
        return contentValues;
    }

    private static String FormatPosterImage(String imageUrl) {
        final String BASE_URL = "http://image.tmdb.org/t/p/";
        final String IMG_SIZE = "/w500";
        return BASE_URL + IMG_SIZE + imageUrl;
    }

    private static String FormatBackDropImage(String imageUrl) {
        final String BASE_URL = "http://image.tmdb.org/t/p/";
        final String IMG_SIZE = "w780/";
        return BASE_URL + IMG_SIZE + imageUrl;
    }

    public static ArrayList<ReviewModel> JsonReviewParser(String s)
            throws JSONException {
        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray jsonMoviesArray = jsonObject.getJSONArray("results");
            String id = jsonObject.optString("id");
            ArrayList<ReviewModel> reviewList = new ArrayList<>();
            for (int i = 0; i < jsonMoviesArray.length(); i++) {
                ReviewModel reviewModel = new ReviewModel();
                JSONObject jsonObject1 = jsonMoviesArray.getJSONObject(i);
                String author = jsonObject1.getString("author");

                String content = jsonObject1.getString("content");
                String url = jsonObject1.getString("url");
                reviewModel.setAuthorName(author);
                reviewModel.setUrl(url);
                reviewModel.setId(id);
                reviewModel.setContent(content);
                reviewList.add(reviewModel);

            }

            return reviewList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ArrayList<TrailerModel> JsonTrailerParser(String string)
            throws JSONException {
        try {

            ArrayList<TrailerModel> data = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(string);
            //Get the instance of JSONArray that contains JSONObjects
            JSONArray jsonMoviesArray = jsonObject.optJSONArray("results");
            String id = jsonObject.optString("id");
            String key = "";
            for (int i = 0; i < jsonMoviesArray.length(); i++) {
                TrailerModel trailerModel = new TrailerModel();
                JSONObject jsonObject1 = jsonMoviesArray.getJSONObject(i);
                String type = jsonObject1.optString("type");
                if (type.equalsIgnoreCase("trailer")) {
                    key = jsonObject1.optString("key");
                    trailerModel.setKey(key);
                    trailerModel.setId(id);
                    data.add(trailerModel);
                }
            }
            return data;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }


}