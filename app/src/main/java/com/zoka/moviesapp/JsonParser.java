package com.zoka.moviesapp;

/**
 * Created by Mohamed AbdelraZek on 2/20/2017.
 */

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class JsonParser {
    public static ArrayList<MoviesModel> JsonData(JSONObject jsonObject)
            throws JSONException {


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
        final String IMG_SIZE = "/w342";
        return BASE_URL + IMG_SIZE + imageUrl;
    }
    //http://image.tmdb.org/t/p//w342

    private static String FormatBackDropImage(String imageUrl) {
        final String BASE_URL = "http://image.tmdb.org/t/p/";
        final String IMG_SIZE = "w780/";
        return BASE_URL + IMG_SIZE + imageUrl;
    }
    //http://image.tmdb.org/t/p/w780/


}



