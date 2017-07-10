package com.tutorizal.app.popularmovie;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tutorizal.app.popularmovie.utilities.MovieDatabaseJsonUtils;
import com.tutorizal.app.popularmovie.utilities.NetworkUtils;

import java.net.URL;

public class StringMainActivity extends AppCompatActivity implements StringMovieAdapter.MovieAdapterOnClickHandler{
    private static final String TAG = StringMainActivity.class.getSimpleName();

    public final static String MOV_ID = "MOV_ID";
    public final static String MOV_TITLE = "MOV_TITLE";
    public final static String MOV_POSTER_PATH = "MOV_POSTER_PATH";
    public final static String MOV_AVERAGE = "MOV_AVERAGE";
    public final static String MOV_OVERVIEW = "MOV_OVERVIEW";
    public final static String MOV_RELEASE_DATE = "MOV_RELEASE_DATE";

    private String movId, movAverage, movTitle, movPosterPath, movOverview, movReleaseDate;
    private String option;

    private RecyclerView mRecyclerView ;
    private StringMovieAdapter mMovieAdapter;

    private TextView mErrorMessageDisplay;
    private ProgressBar mLoadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_movies_grid);

        mErrorMessageDisplay = (TextView) findViewById(R.id.main_error_display_tv);

        mLoadingIndicator = (ProgressBar) findViewById(R.id.main_loading_indicator_pb);

//        GridLayoutManager layoutManager = new GridLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            mRecyclerView.setLayoutManager(new GridLayoutManager(this, 4));
        }
        mRecyclerView.setHasFixedSize(true);

        mMovieAdapter = new StringMovieAdapter(this, getApplicationContext());

        mRecyclerView.setAdapter(mMovieAdapter);

        loadMovieData();
    }

    private void loadMovieData() {
        showMovieDataView();

        if(option == null) {
//            default will fetch by popularity
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
        Context ctx = StringMainActivity.this;
        if( itemClicked == R.id.menu_sort_by_popularity) {
            option = "popular";
        } else {
            option = "top_rated";
        }
        Toast.makeText(ctx, option, Toast.LENGTH_SHORT).show();
        loadMovieData();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(String movies) {
        movId = movies.split("#")[0];
        movAverage= movies.split("#")[1];
        movPosterPath = movies.split("#")[2];
        movTitle = movies.split("#")[3];
        movOverview = movies.split("#")[4];
        movReleaseDate = movies.split("#")[5];
        Toast.makeText(this, "Movie ID: "+movId, Toast.LENGTH_SHORT).show();
        Context ctx = this;
        Class destinationClass = MovieDetailActivity.class;
        Intent intentToStartMovieDetail = new Intent(ctx, destinationClass);
        intentToStartMovieDetail.putExtra(MOV_ID, movId);
        intentToStartMovieDetail.putExtra(MOV_AVERAGE, movAverage);
        intentToStartMovieDetail.putExtra(MOV_POSTER_PATH, movPosterPath);
        intentToStartMovieDetail.putExtra(MOV_TITLE, movTitle);
        intentToStartMovieDetail.putExtra(MOV_OVERVIEW, movOverview);
        intentToStartMovieDetail.putExtra(MOV_RELEASE_DATE, movReleaseDate);
        startActivity(intentToStartMovieDetail);
    }

    private void showErrorMessage() {
        mRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageDisplay.setVisibility(View.VISIBLE);
    }

    private void showMovieDataView() {
        mErrorMessageDisplay.setVisibility(View.INVISIBLE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }

    private class FetchMovieItemTask extends AsyncTask<String, Void, String[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingIndicator.setVisibility(View.VISIBLE);
        }


        @Override
        protected String[] doInBackground(String... params) {
            if (params.length == 0) {
                return null;
            }

            String param = params[0];
            URL movieRequestUrl = NetworkUtils.buildUrl(param);

            try {
                String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(movieRequestUrl);
                String[] simpleJsonMovieData = MovieDatabaseJsonUtils.getMoviesStringsFromJson(StringMainActivity.this, jsonMovieResponse);
                return simpleJsonMovieData;
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String[] movieItems) {
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
