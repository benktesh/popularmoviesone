package com.benktesh.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.StrictMode;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.benktesh.popularmovies.Model.MovieItem;
import com.benktesh.popularmovies.Util.JsonUtils;
import com.example.benktesh.popularmovies.R;
import com.benktesh.popularmovies.Util.NetworkUtilities;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MovieAdapter.ListItemClickListener {

    private static final int NUM_LIST_ITEMS = 100;
    private MovieAdapter mMovieAdapter;
    private RecyclerView mMovieItemList;
    private static String SORT_POPULAR = "popular";
    private static String SORT_TOP_RATED = "top rated";

    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //get reference to recyclerview
        mMovieItemList = (RecyclerView) findViewById(R.id.rv_movies);

        GridLayoutManager layoutManager = new GridLayoutManager(this, 2);
        mMovieItemList.setLayoutManager(layoutManager);

        mMovieItemList.setHasFixedSize(true);

        /*
            Call api and get list of movies.
            Make this separte function so that we can refresh this list (such as in the even tof sort order change
            The function should handle network errors.
            Make adapter take the list instead.


        */

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        List<MovieItem> movieItems = new ArrayList<MovieItem>();
        final String responseFromHttpUrl;

        URL url = NetworkUtilities.buildDataUrl(getText(R.string.api_key).toString(), SORT_POPULAR);

        try {
            responseFromHttpUrl = NetworkUtilities.getResponseFromHttpUrl(url);
            movieItems = JsonUtils.parseMovieJson(responseFromHttpUrl);
            Log.d(TAG, responseFromHttpUrl);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "Error getting response from network");
            Toast.makeText(this, "Error getting response from network.", Toast.LENGTH_SHORT).show();


        }


        mMovieAdapter = new MovieAdapter(movieItems, this, this);


        mMovieItemList.setAdapter(mMovieAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        String key = (String) getResources().getText(R.string.api_key);

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sort_most_popular) {

            //URL url = NetworkUtilities.buildPosterUrl("BNZadXqJSdt05SHLqgT0HuC5Gm.jpg");


           // url = NetworkUtilities.buildDataUrl(key, SORT_POPULAR);

            //Log.d("", "onOptionsItemSelected: " + url);
            Toast.makeText(this, "most popular", Toast.LENGTH_SHORT).show();

            return true;
        }
        if (id == R.id.action_sort_top_rated) {
            Toast.makeText(this, SORT_TOP_RATED, Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void OnListItemClick(int clickedItemIndex, MovieItem movieItem) {
        Toast.makeText(this, " " + clickedItemIndex, Toast.LENGTH_SHORT).show();

        Intent myIntent = new Intent(this, DetailedActivity.class);
        myIntent.putExtra(DetailedActivity.EXTRA_INDEX, 1);
        myIntent.putExtra("movieItem", movieItem);
        startActivity(myIntent);


    }
}
