package com.benktesh.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
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
    private static String SORT_TOP_RATED = "top_rated";
    private static String currentSort = SORT_POPULAR;
    private static String MOVIE_LIST_KEY = "MOVIE_LIST_KEY";
    private static String CURRENT_SORT_KEY = "CURRENT_SORT_KEY";
    ArrayList<MovieItem> movieItems;

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

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        //if saved instance is not null and contains key for movie list, we will restore that.
        if (savedInstanceState != null && savedInstanceState.containsKey(MOVIE_LIST_KEY)) {
            movieItems = savedInstanceState.getParcelableArrayList(MOVIE_LIST_KEY);
            currentSort = savedInstanceState.getString(CURRENT_SORT_KEY, SORT_POPULAR);
            Log.d(TAG, "Got List from Saved Instance");
        }
        LoadView();
    }

    /**
     * This method loads the main view. It first checks if the movieItems is empty or null and in that case
     * calls GetMovieData to get a fresh copy of data. Otherwise, it just loads the view from existing list of
     * movie items.
     */
    private void LoadView() {
        if (movieItems == null || movieItems.isEmpty()) {
            GetMovieData();
        }
        mMovieAdapter = new MovieAdapter(movieItems, this, this);
        mMovieItemList.setAdapter(mMovieAdapter);
    }

    /**
     * This method calls the network and popualtes the movieitems based on currentSort variable.
     */
    private void GetMovieData() {
        final String responseFromHttpUrl;
        URL url = NetworkUtilities.buildDataUrl(getText(R.string.api_key).toString(), currentSort);

        try {
            responseFromHttpUrl = NetworkUtilities.getResponseFromHttpUrl(url, this);
            if (responseFromHttpUrl == null) {
                Toast.makeText(this, "There is no internet and movies can't load at this time.", Toast.LENGTH_LONG).show();
            } else {
                movieItems = JsonUtils.parseMovieJson(responseFromHttpUrl);
                Log.d(TAG, responseFromHttpUrl);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "Error getting response from network");
            Toast.makeText(this, "Error getting response from network.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Depending on the item selected, this method sets the current sort and clears the movie item list.
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        String key = (String) getResources().getText(R.string.api_key);

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_sort_most_popular && currentSort != SORT_POPULAR) {
            movieItems.clear();
            currentSort = SORT_POPULAR;
            LoadView();
            return true;
        }
        if (id == R.id.action_sort_top_rated && currentSort != SORT_TOP_RATED) {
            movieItems.clear();
            currentSort = SORT_TOP_RATED;
            LoadView();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void OnListItemClick(int clickedItemIndex, MovieItem movieItem) {
       // Toast.makeText(this, " " + clickedItemIndex, Toast.LENGTH_SHORT).show();
        Intent myIntent = new Intent(this, DetailedActivity.class);
        myIntent.putExtra(DetailedActivity.EXTRA_INDEX, 1);
        myIntent.putExtra("movieItem", movieItem);
        startActivity(myIntent);


    }

    /**
     * Upon restore the movie items and current sort will be applied.
     * @param savedInstanceState
     */
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        movieItems = savedInstanceState.getParcelableArrayList(MOVIE_LIST_KEY);
        currentSort = savedInstanceState.getString(CURRENT_SORT_KEY);
        Log.d(TAG, "Restored movies from bundle");
    }

    /**
     * We will store the movieItems and Current Sort into the bundle
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(MOVIE_LIST_KEY, movieItems);
        outState.putString(CURRENT_SORT_KEY, currentSort);
        super.onSaveInstanceState(outState);
        Log.d(TAG, "Saving the bundle");
    }
}
