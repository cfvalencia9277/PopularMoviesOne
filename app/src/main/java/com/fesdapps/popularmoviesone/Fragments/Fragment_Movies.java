package com.fesdapps.popularmoviesone.Fragments;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteException;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import com.fesdapps.popularmoviesone.Adapters.RVAdapter;
import com.fesdapps.popularmoviesone.Data.MovieColumns;
import com.fesdapps.popularmoviesone.Data.MoviesProvider;
import com.fesdapps.popularmoviesone.Models.MovieModel;
import com.fesdapps.popularmoviesone.R;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Fabian on 21/07/2016.
 */
public class Fragment_Movies extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>  {
    View rootView;
    ProgressBar progressBar;
    String sortType;
    RVAdapter rvaAdapte;
    RecyclerView recyclerView;
    SharedPreferences prefs;

    private static final int MOVIE_LOADER = 101;
    boolean mTwoPane;

    public Fragment_Movies(){}
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        Stetho.initializeWithDefaults(getContext());
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        sortType = prefs.getString(getString(R.string.pref_sort),getString(R.string.pref_label_popular));
        Bundle args = new Bundle();
        args.putString("sort",sortType);
        getLoaderManager().initLoader(MOVIE_LOADER, args, this);
        if (getArguments().containsKey("twoPane")) {
            mTwoPane = getArguments().getBoolean("twoPane");
        }
        rvaAdapte = new RVAdapter(getContext(),mTwoPane,getActivity().getSupportFragmentManager());
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
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerview);
        RecyclerView.LayoutManager layoutManager2 = new GridLayoutManager(getActivity(),2);
        recyclerView.setLayoutManager(layoutManager2);
        recyclerView.setAdapter(rvaAdapte);
        return rootView;
    }
    public void updateOrder(){
        progressBar.setVisibility(View.VISIBLE);
        fetchdata();
        restartLoader();
    }
    @Override
    public void onResume() {
        super.onResume();
        restartLoader();
    }
    public void restartLoader(){
        sortType = prefs.getString(getString(R.string.pref_sort),getString(R.string.pref_label_popular));
        Bundle args = new Bundle();
        args.putString("sort",sortType);
        getLoaderManager().restartLoader(MOVIE_LOADER, args, this);
    }

    public void fetchdata(){
        AsyncHttpClient client = new AsyncHttpClient();
        final String MOVIE_BASE_URL_POPULAR = "http://api.themoviedb.org/3/movie/popular?api_key=645197735faaceb67ab59d10899455a6";
        final String MOVIE_BASE_URL_TOP_RATED = "http://api.themoviedb.org/3/movie/top_rated?api_key=645197735faaceb67ab59d10899455a6";
        client.get(MOVIE_BASE_URL_POPULAR, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                progressBar.setVisibility(View.GONE);
                try {
                    String str = new String(responseBody, "UTF-8");
                    JSONObject serversent = new JSONObject(str);
                    JSONArray result = serversent.getJSONArray("results");
                    for(int i=0;i<result.length();i++){
                        Gson gson = new Gson();
                        JSONObject movieObject = (JSONObject) result.get(i);
                        MovieModel movie = gson.fromJson(movieObject.toString(),MovieModel.class);
                        insertData(movie,"popular");
                    }
                }catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
        client.get(MOVIE_BASE_URL_TOP_RATED, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                progressBar.setVisibility(View.GONE);
                try {
                    String str = new String(responseBody, "UTF-8");
                    JSONObject serversent = new JSONObject(str);
                    JSONArray result = serversent.getJSONArray("results");
                    for(int i=0;i<result.length();i++){
                        Gson gson = new Gson();
                        JSONObject movieObject = (JSONObject) result.get(i);
                        MovieModel movie = gson.fromJson(movieObject.toString(),MovieModel.class);
                        insertData(movie,"rate");
                    }
                }catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }


    public void insertData(MovieModel movieInsert,String sortType){
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
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String sort = args.getString("sort","popular");// bring sort type
        CursorLoader cl = null;
            if(sort.equals("popular")) {
                cl = new CursorLoader(getContext(), MoviesProvider.Movies.CONTENT_URI, null,
                        MovieColumns.TYPE + "= ?", new String[]{"popular"}, null);
            }
            if(sort.equals("rate")) {
                cl = new CursorLoader(getContext(), MoviesProvider.Movies.CONTENT_URI, null,
                        MovieColumns.TYPE + "= ?", new String[]{"rate"}, null);
            }
            if(sort.equals("favorite")) {
                cl = new CursorLoader(getContext(),MoviesProvider.Movies.CONTENT_URI,null,
                        MovieColumns.IS_FAVORITE + "= ?",new String[] { "1" },null);
            }
        return cl;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        progressBar.setVisibility(View.GONE);
        rvaAdapte.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        rvaAdapte.swapCursor(null);
    }

}


