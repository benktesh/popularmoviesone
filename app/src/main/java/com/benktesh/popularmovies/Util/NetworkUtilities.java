package com.benktesh.popularmovies.Util;

import android.net.Uri;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Benktesh on 5/1/18.
 * Some of the resource for this file were created from Udacity provided content for the students in Google Challenge Scholar's Exercise 2.
 */

public class NetworkUtilities {

    final static String BASE_URL = "http://image.tmdb.org/t/p/";

    final static String PARAM_QUERY = "q";

    //widht of the poster
    final static String WIDTH = "w185";

    /**
     * Builds the URL to fetch poster image.
     *
     * @param poster The file for poster.
     * @return The URL to use to query the GitHub.
     */
    public static URL buildPosterUrl(String poster) {

        String finalPath = BASE_URL  + WIDTH + "//n" + poster;
        Uri builtUri = Uri.parse(finalPath);

        return getUrl(builtUri);

    }

    @Nullable
    private static URL getUrl(Uri builtUri) {
        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

    public static URL buildDataUrl(String apiKey, String sort)
    {
        String finalPath = "http://api.themoviedb.org/3/movie/" + sort + "?api_key=" + apiKey;
        Uri builtUri = Uri.parse(finalPath);
        return getUrl(builtUri);
    }

    /**
     * This method returns the entire result from the HTTP response.
     *
     * @param url The URL to fetch the HTTP response from.
     * @return The contents of the HTTP response.
     * @throws IOException Related to network and stream reading
     */
    public static String getResponseFromHttpUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                scanner.close();
                return null;
            }
        } finally {
            urlConnection.disconnect();

        }
    }

}
