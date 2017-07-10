package com.tutorizal.app.popularmovie;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tutorizal.app.popularmovie.utilities.MovieDatabaseJsonUtils;
import com.tutorizal.app.popularmovie.utilities.MovieItem;
import com.tutorizal.app.popularmovie.utilities.NetworkUtils;

import java.net.URL;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler{
    private static final String TAG = MainActivity.class.getSimpleName();
    private String option;

    private RecyclerView mRecyclerView ;
    private MovieAdapter mMovieAdapter;

    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_movies_grid);

        mErrorMessageDisplay = (TextView) findViewById(R.id.main_error_display_tv);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.main_loading_indicator_pb);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layoutManager);

        mRecyclerView.setHasFixedSize(true);

        mMovieAdapter = new MovieAdapter(this);

        mRecyclerView.setAdapter(mMovieAdapter);

        loadMovieData();
    }

    private void loadMovieData() {
        showMovieDataView();

        if(option == null) {
            new FetchMovieItemTask().execute("popular");
        } else {
            new FetchMovieItemTask().execute(option);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sort_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemClicked = item.getItemId();
        Context ctx = MainActivity.this;
        if( itemClicked == R.id.menu_sort_by_popularity) {
            option = "popular";
        } else {
            option = "top_rated";
        }
        Toast.makeText(ctx, option, Toast.LENGTH_SHORT).show();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(String movieId) {
        Toast.makeText(this, "Movie ID: "+movieId, Toast.LENGTH_SHORT).show();
        /*Context ctx = this;
        Class destinationClass = MovieDetailActivity.class;
        Intent intentToStartMovieDetail = new Intent(ctx, destinationClass);
        intentToStartMovieDetail.putExtra("MOVIE_ID", movieId);
        startActivity(intentToStartMovieDetail);*/
    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    private void showMovieDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private class FetchMovieItemTask extends AsyncTask<String, Void, MovieItem[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }


        @Override
        protected MovieItem[] doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }

            String param = params[0];
            URL movieRequestUrl = NetworkUtils.buildUrl(param);

            try {
                String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(movieRequestUrl);
                MovieItem[] simpleJsonMovieData = MovieDatabaseJsonUtils.getMoviesItemFromJson(MainActivity.this, jsonMovieResponse);
                return simpleJsonMovieData;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(MovieItem[] movieItems) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            if(movieItems != null) {
                showMovieDataView();
                mMovieAdapter.setMovieData(movieItems);
            } else {
                showErrorMessage();
            }
        }
    }
}
