package com.tutorizal.app.popularmovie.utilities;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by aaijal on 7/8/2017.
 */

public final class MovieDatabaseJsonUtils {
    private final static String MDB_LIST = "results";

    private final static String MDB_ID = "id";

    private final static String MDB_TITLE = "title";
    private final static String MDB_POSTER_PATH = "poster_path";
    private final static String MDB_AVERAGE = "vote_average";
    private final static String MDB_OVERVIEW = "overview";
    private final static String MDB_RELEASE_DATE = "release_date";

    /** Return String array of movies */
    public static String[] getMoviesStringsFromJson(Context context, String movieJsonStr)
            throws JSONException {

        String[] parsedMovieData = null;

        JSONObject movieJson = new JSONObject(movieJsonStr);

        JSONArray movieArray = movieJson.getJSONArray(MDB_LIST);

        parsedMovieData = new String[movieArray.length()];

        for (int i = 0; i < movieArray.length(); i++) {
            /* These are the values that will be collected */
            int movId;
            double movAverage;
            String movPosterPath;
            String movTitle;
            String movOverview;
            String movReleaseDate;

            /* Get the JSON object representing movie */
            JSONObject movie = movieArray.getJSONObject(i);
            movId = movie.getInt(MDB_ID);
            movAverage = movie.getDouble(MDB_AVERAGE);
            movPosterPath = movie.getString(MDB_POSTER_PATH);
            movTitle = movie.getString(MDB_TITLE);
            movOverview = movie.getString(MDB_OVERVIEW);
            movReleaseDate = movie.getString(MDB_RELEASE_DATE);

            parsedMovieData[i] = String.valueOf(movId)+ "#" + String.valueOf(movAverage)+ "#" + movPosterPath+ "#" + movTitle+ "#" + movOverview+ "#" + movReleaseDate;
        }

        return parsedMovieData;
    }

    /** Return MovieItem array */
    public static MovieItem[] getMoviesItemFromJson(Context context, String movieJsonStr)
            throws JSONException {

        MovieItem[] parsedMovieData = null;

        JSONObject movieJson = new JSONObject(movieJsonStr);

        JSONArray movieArray = movieJson.getJSONArray(MDB_LIST);

        parsedMovieData = new MovieItem[movieArray.length()];

        for (int i = 0; i < movieArray.length(); i++) {
            /* These are the values that will be collected */
            int movId;
            double movAverage;
            String movPosterPath;
            String movTitle;
            String movOverview;
            String movReleaseDate;

            /* Get the JSON object representing movie */
            JSONObject movie = movieArray.getJSONObject(i);
            movId = movie.getInt(MDB_ID);
            movAverage = movie.getDouble(MDB_AVERAGE);
            movPosterPath = movie.getString(MDB_POSTER_PATH);
            movTitle = movie.getString(MDB_TITLE);
            movOverview = movie.getString(MDB_OVERVIEW);
            movReleaseDate = movie.getString(MDB_RELEASE_DATE);

            parsedMovieData[i].setId(movId);
            parsedMovieData[i].setAverageRate(movAverage);
            parsedMovieData[i].setPosterPath(movPosterPath);
            parsedMovieData[i].setTitle(movTitle);
            parsedMovieData[i].setOverview(movOverview);
            parsedMovieData[i].setReleaseDate(movReleaseDate);
        }

        return parsedMovieData;
    }
}
