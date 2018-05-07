package com.benktesh.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.benktesh.popularmovies.Model.MovieItem;
import com.example.benktesh.popularmovies.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Benktesh on 5/2/18.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {
    private static final String TAG = MovieAdapter.class.getSimpleName();
    private static int viewHolderCount;
    private List<MovieItem> mMovieItemList;
    private MovieItem movieItem;

    final private ListItemClickListener mOnClickListener;


    public interface ListItemClickListener {
        void OnListItemClick(int clickedItemIndex);
    }


    public MovieAdapter(List<MovieItem> movieItemList, ListItemClickListener listener) {

        if(movieItemList == null)
        {
            mMovieItemList = new ArrayList<MovieItem>();
        }
        mMovieItemList = movieItemList;
        viewHolderCount = 0;
        mOnClickListener = listener;
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

        TextView listMovieItemView;


        public MovieViewHolder(View itemView) {
            super(itemView);

            listMovieItemView = (TextView) itemView.findViewById(R.id.tv_item_number);
            itemView.setOnClickListener(this);
        }

        void bind(int listIndex) {

            movieItem = mMovieItemList.get(listIndex);

            listMovieItemView.setText(String.valueOf(movieItem.getOriginalTitle()));
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.OnListItemClick(clickedPosition);
            Log.d(TAG, "Done with OnClick");
        }
    }

}
