package com.fesdapps.popularmoviesone.Fragments;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.stetho.Stetho;
import com.fesdapps.popularmoviesone.Adapters.MoviesAdapter;
import com.fesdapps.popularmoviesone.Data.MovieColumns;
import com.fesdapps.popularmoviesone.Data.MoviesProvider;
import com.fesdapps.popularmoviesone.Models.MovieModel;
import com.fesdapps.popularmoviesone.R;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fabian on 21/07/2016.
 */
public class Fragment_Movies extends Fragment {
    View rootView;
    GridView gridView;
    ProgressBar progressBar;
    MoviesAdapter adapter;

    String sortType;


    public Fragment_Movies(){}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Stetho.initializeWithDefaults(getContext());

    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.moviefragment, menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            updateOrder();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.mainactivity_fragmentview,container,false);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progress);
        gridView = (GridView) rootView.findViewById(R.id.maingridview);
        return rootView;
    }
    public void updateOrder(){
        new AsyncHttpTaskMovies().execute();

    }
    @Override
    public void onStart() {
        super.onStart();
        new AsyncHttpTaskMovies().execute();
    }

    public class AsyncHttpTaskMovies extends AsyncTask<List<MovieModel>, Void, List<MovieModel>> {

        public boolean isTopRated;
        ArrayList<MovieModel> dataServer = new ArrayList<MovieModel>();


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressBar.setVisibility(View.VISIBLE);
            gridView.setVisibility(View.GONE);
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
            sortType = prefs.getString(getString(R.string.pref_sort),getString(R.string.pref_label_popular));
            if(sortType.equalsIgnoreCase("rate")){
                isTopRated=true;
            }else {isTopRated=false;}
        }

        @Override
        protected List<MovieModel> doInBackground(List<MovieModel>... params) {
            HttpURLConnection urlConnection = null;
            {
                final String MOVIE_BASE_URL_POPULAR = "http://api.themoviedb.org/3/movie/popular";
                final String MOVIE_BASE_URL_TOP_RATED = "http://api.themoviedb.org/3/movie/top_rated";
                final String USER_KEY = "?api_key=645197735faaceb67ab59d10899455a6";
                String strurl = "";
                if (isTopRated) {
                    strurl = MOVIE_BASE_URL_TOP_RATED + USER_KEY;
                } else {
                    strurl = MOVIE_BASE_URL_POPULAR + USER_KEY;
                }
                URL url = null;
                try {
                    url = new URL(strurl);
                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();

                    StringBuilder sb = new StringBuilder();
                    int HttpResult = urlConnection.getResponseCode();
                    if (HttpResult == HttpURLConnection.HTTP_OK) {
                        BufferedReader br = new BufferedReader(
                                new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
                        String line = null;
                        while ((line = br.readLine()) != null) {
                            sb.append(line + "\n");
                        }
                        br.close();
                        JSONObject serversent = new JSONObject(sb.toString());
                        JSONArray result = serversent.getJSONArray("results");
                        for(int i=0;i<result.length();i++){
                            Gson gson = new Gson();
                            JSONObject movieObject = (JSONObject) result.get(i);
                            MovieModel movie = gson.fromJson(movieObject.toString(),MovieModel.class);
                            insertData(movie);
                            dataServer.add(movie);
                        }
                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (ProtocolException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            return null;
        }
        @Override
        protected void onPostExecute(List<MovieModel> plantsfeed) {
            progressBar.setVisibility(View.GONE);
            gridView.setVisibility(View.VISIBLE);
            if (dataServer.size()>0) {
                adapter = new MoviesAdapter(getActivity(), dataServer);
                gridView.setAdapter(adapter);

            } else {
                Toast.makeText(getActivity(), "Failed to fetch data!", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void insertData(MovieModel movieInsert){

        //  if(MoviesProvider.MoviewithIDSERVER(movieInsert.getMovieId()) != null){
        //     Log.e("MOVIE","ENTERED ");
        //  }else{
        /*
        propper way to read especific data from db
            Cursor c = getActivity().getContentResolver().query(MoviesProvider.Movies.CONTENT_URI,
                null, MovieColumns.ID + "= ?",
                new String[] { movieInsert.getMovieId() }, null);
        Log.e("C CURSOR",DatabaseUtils.dumpCursorToString(c));
        */
        Log.e("MOVIE","Added Movie");
        ArrayList<ContentProviderOperation> batchOperations = new ArrayList<>(1);
        ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(MoviesProvider.Movies.CONTENT_URI);
        builder.withValue(MovieColumns.POSTER_PATH, movieInsert.getPoster_path());
        builder.withValue(MovieColumns.ORIGINAL_TITLE, movieInsert.getOriginal_title());
        builder.withValue(MovieColumns.OVERVIEW, movieInsert.getOverview());
        builder.withValue(MovieColumns.RELEASE_DATE, movieInsert.getRelease_date());
        builder.withValue(MovieColumns.VOTE_AVERAGE, movieInsert.getVote_average());
        builder.withValue(MovieColumns.ID, movieInsert.getMovieId());
        builder.withValue(String.valueOf(MovieColumns.IS_FAVORITE), false);
        builder.withValue(MovieColumns.TYPE,sortType);
        batchOperations.add(builder.build());
        try{
            getActivity().getContentResolver().applyBatch(MoviesProvider.AUTHORITY, batchOperations);
        }
        catch (SQLiteConstraintException e){
            Log.e("EXIST", "EXIST MAIN");
        }
        catch (SQLiteException e){
            Log.e("SQLite", "Error ");
        }
        catch(RemoteException | OperationApplicationException e){
            Log.e("DATA", "Error applying batch insert");
        }
        catch (Exception e){
            Log.e("EXCEPTION", "GENERAL");
        }

        //  }
    }







}


