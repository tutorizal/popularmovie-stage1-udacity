package com.tutorizal.app.popularmovie;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by aaijal on 7/10/2017.
 */

public class StringMovieAdapter extends RecyclerView.Adapter<StringMovieAdapter.MovieAdapterViewHolder> {
    private String[] mMovieData;
    private ImageView mMovieThumbnail;
    private final String MOVIE_THUMBNAIL_URL = "http://image.tmdb.org/t/p/w185/";

//    to be used inside Picasso
    private final Context context;

    private final MovieAdapterOnClickHandler mClickHandler;

    public interface MovieAdapterOnClickHandler {
        void onClick(String movieId);
    }

    public StringMovieAdapter(MovieAdapterOnClickHandler clickHandler, Context ctx) {
        this.mClickHandler = clickHandler;
        this.context = ctx;
    }

    public class MovieAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // will be changed to ImageView using Picasso library next time
//        public final TextView mMovieTextView;

        public MovieAdapterViewHolder(View itemView) {
            super(itemView);
            mMovieThumbnail = (ImageView) itemView.findViewById(R.id.iv_item_movie_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            String movie = mMovieData[adapterPosition];
//            String movieId = movie.split("#")[0];
//            String moviePosterPath = movie.split("#")[2];
            mClickHandler.onClick(movie);
        }
    }

    @Override
    public MovieAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.movie_thumbnail_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new MovieAdapterViewHolder(view);
    }

    public void onBindViewHolder(MovieAdapterViewHolder holder, int position) {
        String movie = mMovieData[position];
        String moviePosterPath = movie.split("#")[2];
        String movieUrl = MOVIE_THUMBNAIL_URL+moviePosterPath;
        Picasso.with(context).load(movieUrl).into(mMovieThumbnail);
//        holder.mMovieThumbnail.setImageResource(movie);
    }

    @Override
    public int getItemCount() {
        if (null == mMovieData) return 0;
        return mMovieData.length;
    }

    public void setMovieData(String[] moviesData) {
        mMovieData = moviesData;
        notifyDataSetChanged();
    }
}
