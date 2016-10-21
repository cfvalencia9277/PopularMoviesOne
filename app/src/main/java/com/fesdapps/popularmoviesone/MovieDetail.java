package com.fesdapps.popularmoviesone;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.fesdapps.popularmoviesone.Fragments.MovieDetailFragment;

/**
 * Created by Fabian on 22/07/2016.
 */
public class MovieDetail extends AppCompatActivity {

    String movieId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moviedetail_activity);
        Intent intent = this.getIntent();
        if (savedInstanceState == null) {
            Bundle bundle = new Bundle();
            movieId = intent.getStringExtra("movie");
            bundle.putBoolean("first",false);
            bundle.putString("movieId",movieId);
            MovieDetailFragment mdf = new MovieDetailFragment();
            mdf.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_detail, mdf)
                    .commit();
        }

    }







}
