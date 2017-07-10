package com.tutorizal.app.popularmovie;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.net.URL;

public class MovieDetailActivity extends AppCompatActivity {

    private final String MOVIE_THUMBNAIL_URL = "http://image.tmdb.org/t/p/w185/";

//    private MovieItem movieItem;
//    use String instead, haven't solved using Custom class
    private String movId, movAverage, movTitle, movPosterPath, movOverview, movReleaseDate;

    private TextView mTitle, mPlot, mRating, mReleaseDate;
    private ImageView mThumbnail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        initView();

        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if(intentThatStartedThisActivity.hasExtra(StringMainActivity.MOV_ID)) {
                movTitle = intentThatStartedThisActivity.getExtras().getString(StringMainActivity.MOV_TITLE);
                movOverview = intentThatStartedThisActivity.getExtras().getString(StringMainActivity.MOV_OVERVIEW);
                movAverage = intentThatStartedThisActivity.getExtras().getString(StringMainActivity.MOV_AVERAGE);
                movReleaseDate = intentThatStartedThisActivity.getExtras().getString(StringMainActivity.MOV_RELEASE_DATE);
                movPosterPath = intentThatStartedThisActivity.getExtras().getString(StringMainActivity.MOV_POSTER_PATH);
                mTitle.setText(movTitle);
                mPlot.setText(movOverview);
                mRating.setText(movAverage);
                mReleaseDate.setText("Release Date: "+movReleaseDate);
                Picasso.with(getApplicationContext()).load(MOVIE_THUMBNAIL_URL+movPosterPath).into(mThumbnail);
            }
        }
    }

    private void initView() {
        mTitle = (TextView) findViewById(R.id.detail_title);
        mPlot = (TextView) findViewById(R.id.detail_plot);
        mRating  = (TextView) findViewById(R.id.detail_rating);
        mReleaseDate = (TextView) findViewById(R.id.detail_release_date);
        mThumbnail = (ImageView) findViewById(R.id.detail_image);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sort_list, menu);
        return false;
    }


}
