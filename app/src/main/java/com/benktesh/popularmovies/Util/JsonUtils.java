package com.benktesh.popularmovies.Util;

import android.support.annotation.NonNull;
import android.util.Log;

import com.benktesh.popularmovies.Model.MovieItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class JsonUtils {

    private static final String TAG = JsonUtils.class.getSimpleName();

    public static List<MovieItem> parseMovieJson(String json) {
        try {

            MovieItem movie;
            JSONObject object = new JSONObject(json);

            JSONArray resultsArray = new JSONArray(object.optString("results",
                    "[\"\"]"));

            System.out.println(resultsArray.toString());

            ArrayList<MovieItem> items = new ArrayList<>();
            for (int i = 0; i < resultsArray.length(); i++) {
                String current = resultsArray.optString(i, "");

                JSONObject movieJson = new JSONObject(current);

                String overview = movieJson.optString("overview", "Not Available");
                String original_title = movieJson.optString("original_title",
                        "Not Available");
                String poster_path = movieJson
                        .optString("poster_path", "Not Available");
                String release_date = movieJson.optString("release_date",
                        "Not Available");
                String vote_count = movieJson.optString("vote_count", "Not Available");
                movie = new MovieItem(original_title, overview, poster_path, release_date, Double.parseDouble(vote_count));
                items.add(movie);

            }

            return items;
        } catch (Exception ex) {
            Log.e(TAG + "parseMovieJson", "Could not parse json " + json);
            return null;
        }
    }


    /**
     * This method parses a json string to a list of string
     *
     * @param - String stringArray representation of json array
     * @return List<String> list of string
     * @throws JSONException
     */
    @NonNull
    private static List<String> getStringsFromArray(String stringArray) {

        List<String> list = new ArrayList<String>();
        try {


            JSONArray jsonarray = null;

            jsonarray = new JSONArray(stringArray);
            for (int i = 0; i < jsonarray.length(); i++) {
                list.add(jsonarray.optString(i, "Not Found"));
            }
            return list;
        } catch (Exception ex)

        {
            Log.e(TAG + "getStringFromArray" + stringArray, ex.toString());

        }
        return list;
    }
}
