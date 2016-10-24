package com.fesdapps.popularmoviesone;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import com.facebook.stetho.Stetho;
import com.fesdapps.popularmoviesone.fragments.Fragment_Movies;
import com.fesdapps.popularmoviesone.fragments.MovieDetailFragment;


public class MainActivity extends AppCompatActivity {
    private boolean mTwoPane;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        setContentView(R.layout.activity_main);
        if(findViewById(R.id.container_detail) != null){
            mTwoPane = true;
            if(savedInstanceState == null){
                Bundle bundle = new Bundle();
                bundle.putBoolean("first",true);
                MovieDetailFragment mdf = new MovieDetailFragment();
                mdf.setArguments(bundle);
                getSupportFragmentManager().beginTransaction()
                        .add(R.id.container_detail, mdf)
                        .commit();
            }
        } else {
            mTwoPane = false;
        }
        if(savedInstanceState == null){
            Bundle bundle = new Bundle();
            bundle.putBoolean("twoPane",mTwoPane);
            Fragment_Movies mf = new Fragment_Movies();
            mf.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container_movies, mf)
                    .commit();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
}
