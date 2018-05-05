package com.benktesh.popularmovies;

import android.content.Intent;
import android.os.Bundle;
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

import com.example.benktesh.popularmovies.R;
import com.benktesh.popularmovies.Util.NetworkUtilities;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements MovieAdapter.ListItemClickListener{

    private static final int NUM_LIST_ITEMS = 100;
    private MovieAdapter mMovieAdapter;
    private RecyclerView mMovieItemList;

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

        mMovieAdapter = new MovieAdapter(NUM_LIST_ITEMS, this);


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

            URL url = NetworkUtilities.buildPosterUrl("BNZadXqJSdt05SHLqgT0HuC5Gm.jpg");


            url = NetworkUtilities.buildDataUrl(key, "popular");

            Log.d("", "onOptionsItemSelected: " + url);
            Toast.makeText(this, "most popular", Toast.LENGTH_SHORT).show();

            return true;
        }
        if(id == R.id.action_sort_top_rated){
            Toast.makeText(this, "top rated", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void OnListItemClick(int clickedItemIndex) {
        Toast.makeText(this, " " + clickedItemIndex, Toast.LENGTH_SHORT).show();

        Intent myIntent = new Intent(this, DetailedActivity.class);
        myIntent.putExtra(DetailedActivity.EXTRA_INDEX, 1);
        startActivity(myIntent);


    }
}
