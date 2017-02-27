package com.zoka.moviesapp.utils;

/**
 * Created by Mohamed AbdelraZek on 2/20/2017.
 */

import android.util.Log;

import com.zoka.moviesapp.Models.MoviesModel;
import com.zoka.moviesapp.Models.ReviewModel;
import com.zoka.moviesapp.Models.TrailerModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonUtils {
    public static ArrayList<MoviesModel> getMoviesData(String jsonString)
            throws JSONException {
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


        ArrayList<MoviesModel> MoviesList = new ArrayList<>();

        JSONArray movieArray = jsonObject.getJSONArray(results);

        for (int i = 0; i < movieArray.length(); i++) {

            MoviesModel movieModel = new MoviesModel();
            JSONObject jsonObject1 = movieArray.getJSONObject(i);
            BackDropPhoto = FormatBackDropImage(jsonObject1.getString(MDB_MOVIE_BACKDROP_PHOTO));
            PosterPathPhoto = FormatPosterImage(jsonObject1.getString(MDB_MOVIE_POSTER));
            Title = jsonObject1.getString(MDB_MOVIE_TITLE);
            Date = jsonObject1.getString(MDB_MOVIE_DATE);
            Description = jsonObject1.getString(MDB_MOVIE_DESC);
            Rate = jsonObject1.getString(MDB_MOVIE_RATE);
            id = jsonObject1.getString(MDB_ID);

            //===============================================================================================
            movieModel.setTitle(Title);
            movieModel.setDate(Date);
            movieModel.setDesc(Description);
            movieModel.setRate(Rate);
            movieModel.setId(id);
            movieModel.setPosterPath(PosterPathPhoto);
            movieModel.setBackDrop(BackDropPhoto);
            MoviesList.add(movieModel);
        }
        return MoviesList;
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

            ArrayList<ReviewModel> reviewList = new ArrayList<>();
            for (int i = 0; i < jsonMoviesArray.length(); i++) {
                ReviewModel reviewSchema = new ReviewModel();
                JSONObject jsonObject1 = jsonMoviesArray.getJSONObject(i);
                String author = jsonObject1.getString("author");

                String content = jsonObject1.getString("content");
                String url = jsonObject1.getString("url");
                reviewSchema.setAuthorName(author);
                reviewSchema.setUrl(url);
                reviewSchema.setContent(content);
                reviewList.add(reviewSchema);

            }
            for (ReviewModel r:reviewList) {
                Log.i("ZOKA",r.getAuthorName());
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
            //  if (string != null) {
            ArrayList<TrailerModel> data = new ArrayList<>();
            JSONObject jsonObject = new JSONObject(string);
            //Get the instance of JSONArray that contains JSONObjects
            JSONArray jsonMoviesArray = jsonObject.optJSONArray("results");
            String key = "";
            for (int i = 0; i < jsonMoviesArray.length(); i++) {
                TrailerModel trailerModel = new TrailerModel();
                JSONObject jsonObject1 = jsonMoviesArray.getJSONObject(i);
                String type = jsonObject1.optString("type");
                if (type.equalsIgnoreCase("trailer"))
                    key = jsonObject1.optString("key");
                trailerModel.setKey(key);
                data.add(trailerModel);

            }
            for (TrailerModel t:data) {
                Log.i("ZOKA",""+t.getKey());

            }
            return data;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }

    }

}