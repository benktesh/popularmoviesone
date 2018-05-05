package com.benktesh.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.benktesh.popularmovies.Model.MovieItem;
import com.example.benktesh.popularmovies.R;
import com.squareup.picasso.Picasso;

public class DetailedActivity extends AppCompatActivity {

    private static final String TAG = DetailedActivity.class.getSimpleName();
    public static final String EXTRA_INDEX = "extra_index";
    private static final int DEFAULT_INDEX = -1;

    TextView mOriginalTitle;
    TextView mOverview;
    ImageView mPoster;
    TextView mReleaseDate;
    TextView mVoteAverage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);



        Intent intent = getIntent();
        if (intent == null) {
            closeOnError("Intent is null");
        }


        int position = intent.getIntExtra(EXTRA_INDEX, DEFAULT_INDEX);

        Log.d(TAG, "Got the position data" + position);

        if (position == DEFAULT_INDEX) {
            // EXTRA_POSITION not found in intent
            closeOnError("Could not get the position data");
            return;
        }
        MovieItem movieItem = new MovieItem();
       // MovieItem movieItem = JsonUtils.parseMovieJson("json");

        movieItem = new MovieItem();
        movieItem.setOriginalTitle("This is test");
        movieItem.setOverview("Give me my mojo");
        movieItem.setPosterPath("What a surprise baby");
        movieItem.setReleaseDate("2018/05/14");
        movieItem.setVoteAverage(5);


        if (movieItem == null) {
            closeOnError("Movie is null");
            return;
        }

        populateUI(movieItem);
    }

    private void closeOnError(String msg) {
        finish();
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(MovieItem movieItem){
        mOriginalTitle = (TextView) findViewById(R.id.tv_original_title);
        mOriginalTitle.setText(movieItem.getOriginalTitle());

        mPoster = (ImageView) findViewById(R.id.iv_movie_poster);

        String posterPathURL = "http://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg";

        try {


            Picasso.with(this)
                    .load(posterPathURL)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(mPoster);
        }
        catch (Exception ex) {
            Log.d(TAG, ex.getMessage());
        }

        setTitle(movieItem.getOriginalTitle());
    }
}
