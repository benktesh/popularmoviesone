package com.benktesh.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.benktesh.popularmovies.Model.MovieItem;
import com.benktesh.popularmovies.Util.NetworkUtilities;
import com.example.benktesh.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Benktesh on 5/2/18.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private static final String TAG = MovieAdapter.class.getSimpleName();
    private List<MovieItem> mMovieItemList;
    private MovieItem movieItem;
    private Context mContext;

    final private ListItemClickListener mOnClickListener;

    public interface ListItemClickListener {
        void OnListItemClick(int clickedItemIndex, MovieItem movieItem);
    }

    public MovieAdapter(List<MovieItem> movieItemList, ListItemClickListener listener, Context context) {

        if (movieItemList == null) {
            mMovieItemList = new ArrayList<MovieItem>();
        }
        mMovieItemList = movieItemList;
        mOnClickListener = listener;
        mContext = context;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        MovieViewHolder viewHolder = new MovieViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mMovieItemList.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView listMovieItemView;

        public MovieViewHolder(View itemView) {
            super(itemView);

            listMovieItemView = (ImageView) itemView.findViewById(R.id.iv_item_poster);
            itemView.setOnClickListener(this);
        }

        void bind(int listIndex) {
            movieItem = mMovieItemList.get(listIndex);
            listMovieItemView = (ImageView) itemView.findViewById(R.id.iv_item_poster);
            String posterPathURL = NetworkUtilities.buildPosterUrl(movieItem.getPosterPath());
            Log.v(TAG, "Poster URL: " + posterPathURL);
            try {
                Picasso.with(mContext)
                        .load(posterPathURL)
                        .placeholder(R.mipmap.ic_launcher)
                        .error(R.mipmap.ic_launcher)
                        .into(listMovieItemView);
            } catch (Exception ex) {
                Log.e(TAG, ex.getMessage());
            }
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.OnListItemClick(clickedPosition, mMovieItemList.get(clickedPosition));
            Log.v(TAG, "Done with OnClick");
        }
    }
}
