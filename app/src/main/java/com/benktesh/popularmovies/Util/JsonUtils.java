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

    public static MovieItem parseMovieJson(String json) {
        try {


            MovieItem movie = new MovieItem();

            JSONObject object = new JSONObject(json);

            //name is complex object
            JSONObject nameObject = object.optJSONObject("name");
            //sandwich.setMainName(nameObject.optString("mainName", "Not Found"));
            //sandwich.setAlsoKnownAs(getStringsFromArray(nameObject.optString("alsoKnownAs", "[\"\"]")));

            //sandwich.setPlaceOfOrigin(object.optString("placeOfOrigin", "Unknown"));
            //sandwich.setDescription(object.optString("description", "Not Found"));
            //sandwich.setImage(object.getString("image"));
            //sandwich.setIngredients(getStringsFromArray(object.optString("ingredients", "[\"\"]")));

            return movie;
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
