package com.fesdapps.popularmoviesone;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.fesdapps.popularmoviesone.Fragments.MovieDetailFragment;

/**
 * Created by Fabian on 22/07/2016.
 */
public class MovieDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moviedetail_activity);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new MovieDetailFragment())
                    .commit();
        }

    }






}
