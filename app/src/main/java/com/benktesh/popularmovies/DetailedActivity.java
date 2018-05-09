package com.benktesh.popularmovies;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.benktesh.popularmovies.Model.MovieItem;
import com.benktesh.popularmovies.Util.NetworkUtilities;
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
        Bundle data = getIntent().getExtras();
        if(data == null)
        {
            closeOnError("Movie data is not available.");
            return;
        }
        MovieItem movieItem = data.getParcelable("movieItem");
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

    private void populateUI(MovieItem movieItem) {
        mOriginalTitle = (TextView) findViewById(R.id.tv_original_title);
        mOverview = (TextView) findViewById(R.id.tv_synopsis);
        mReleaseDate = (TextView) findViewById(R.id.tv_release_date);
        mVoteAverage = (TextView) findViewById(R.id.tv_user_rating);
        mPoster = (ImageView) findViewById(R.id.iv_movie_poster);

        mOriginalTitle.setText(movieItem.getOriginalTitle());
        mOverview.setText(movieItem.getOverview());
        mReleaseDate.setText(movieItem.releaseDate);
        mVoteAverage.setText(String.valueOf(movieItem.getVoteAverage()));



        String posterPathURL = NetworkUtilities.buildPosterUrl(movieItem.getPosterPath());
        try {

            Picasso.with(this)
                    .load(posterPathURL)
                    .placeholder(R.mipmap.ic_launcher)
                    .error(R.mipmap.ic_launcher)
                    .into(mPoster);
        } catch (Exception ex) {
            Log.d(TAG, ex.getMessage());
        }

        setTitle(movieItem.getOriginalTitle());
    }
}
