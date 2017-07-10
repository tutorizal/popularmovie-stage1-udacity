package com.tutorizal.app.popularmovie;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tutorizal.app.popularmovie.utilities.MovieItem;

/**
 * Created by aaijal on 7/10/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieAdapterViewHolder> {
    private MovieItem[] mMovieData;

    private final MovieAdapterOnClickHandler mClickHandler;

    public interface MovieAdapterOnClickHandler {
        void onClick(String movieId);
    }

    public MovieAdapter(MovieAdapterOnClickHandler clickHandler) {
        this.mClickHandler = clickHandler;
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // will be changed to ImageView using Picasso library next time
        public final TextView mMovieTextView;

        public MovieAdapterViewHolder(View itemView) {
            super(itemView);
            mMovieTextView = (TextView) itemView.findViewById(R.id.tv_item_movie_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            MovieItem movieTitle = mMovieData[adapterPosition];
            mClickHandler.onClick(String.valueOf(movieTitle.getId()));
        }
    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.movie_main_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new MovieAdapterViewHolder(view);
    }

    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {
        MovieItem movieTitle = mMovieData[position];
        holder.mMovieTextView.setText(movieTitle.getTitle()+"\n"+movieTitle.getPosterPath());
    }

    @Override
    public int getItemCount() {
        if (null == mMovieData) return 0;
        return mMovieData.length;
    }

    public void setMovieData(MovieItem[] moviesData) {
        mMovieData = moviesData;
        notifyDataSetChanged();
    }
}
