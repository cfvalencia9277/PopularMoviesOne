package com.fesdapps.popularmoviesone.Fragments;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fesdapps.popularmoviesone.Adapters.MovieTrailerAdapter;
import com.fesdapps.popularmoviesone.Adapters.MoviesReviewAdapter;
import com.fesdapps.popularmoviesone.Data.MovieColumns;
import com.fesdapps.popularmoviesone.Data.MoviesDatabase;
import com.fesdapps.popularmoviesone.Data.MoviesProvider;
import com.fesdapps.popularmoviesone.Data.ReviewColumns;
import com.fesdapps.popularmoviesone.Data.TrailerColumns;
import com.fesdapps.popularmoviesone.Models.MovieModel;
import com.fesdapps.popularmoviesone.Models.MovieTrailerModel;
import com.fesdapps.popularmoviesone.Models.ReviewModel;
import com.fesdapps.popularmoviesone.R;
import com.google.gson.Gson;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Fabian on 22/07/2016.
 */
public class MovieDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String BASE_URL_IMG = "http://image.tmdb.org/t/p/w185/";
    private static final String BASE_URL = "http://api.themoviedb.org/3/movie/";
    private static final String REVIEW_KEY = "/reviews?api_key=645197735faaceb67ab59d10899455a6";
    private static final String VIDEOS_KEY = "/videos?api_key=645197735faaceb67ab59d10899455a6";
    Button fav_btn;
    RecyclerView videoList;
    RecyclerView reviewList;
    private List<MovieTrailerModel> feedsListTrailers;
    private List<ReviewModel> feedsListReviews;
    MovieTrailerAdapter trailerAdapter;
    MoviesReviewAdapter reviewAdapter;
    MovieModel movie;

    private static final int FAVORITE_LOADER = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(FAVORITE_LOADER, null, this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.moviedetail_fragmentview, container, false);
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            movie =intent.getParcelableExtra("movie");
            ((TextView) rootView.findViewById(R.id.original_title)).setText(movie.getOriginal_title());
            ImageView img =(ImageView) rootView.findViewById(R.id.poster_detal_img);
            Picasso.with(getContext()).load(BASE_URL_IMG+movie.getPoster_path()).error(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher).into(img);
            ((TextView) rootView.findViewById(R.id.synopsis)).setText(movie.getOverview());
            ((TextView) rootView.findViewById(R.id.user_rate)).setText("User Rating: "+ movie.getVote_average());
            ((TextView) rootView.findViewById(R.id.release_date)).setText("Realease Data: "+movie.getRelease_date());
            fetchdata(movie.getMovieId());
        }
        fav_btn = (Button) rootView.findViewById(R.id.fav_btn);
        fav_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.e("CLICKED","FAV");
                insertData(movie);
            }
        });
        videoList = (RecyclerView) rootView.findViewById(R.id.movieTrailer);
        videoList.setLayoutManager(new LinearLayoutManager(getActivity()));
        reviewList = (RecyclerView) rootView.findViewById(R.id.movieReview);
        reviewList.setLayoutManager(new LinearLayoutManager(getActivity()));
        return rootView;
    }
    public void fetchdata(String movieId){
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(BASE_URL+movieId+VIDEOS_KEY, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // called when response HTTP status is "200 OK"
                String str = null;
                try {
                    str = new String(response, "UTF-8");
                    JSONObject serversent = new JSONObject(str);
                    JSONArray result = serversent.getJSONArray("results");
                    if(result != null){
                        feedsListTrailers = new ArrayList<>();
                        for(int i=0; i< result.length();i++){
                            Gson gson = new Gson();
                            JSONObject trailerObject = (JSONObject) result.get(i);
                            MovieTrailerModel movietrailer = gson.fromJson(trailerObject.toString(),MovieTrailerModel.class);
                            addTrailertodb(movietrailer);
                            feedsListTrailers.add(movietrailer);
                        }
                    }
                   // Log.e("FEEDLIST",feedsListTrailers.toString());
                    trailerAdapter = new MovieTrailerAdapter(getContext(),feedsListTrailers);
                    videoList.setAdapter(trailerAdapter);
                   // Log.e("Result Videos",result.toString());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Toast.makeText(getActivity(), "Failed to fetch Trailers", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });
        client.get(BASE_URL+movieId+REVIEW_KEY, new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // called when response HTTP status is "200 OK"
                String str = null;
                try {
                   // Log.e("ENTER","TRY");
                    str = new String(response, "UTF-8");
                    JSONObject serversent = new JSONObject(str);
                    JSONArray result = serversent.getJSONArray("results");
                    if(result != null){
                        //Log.e("RESULT","NOT NULL");
                        feedsListReviews = new ArrayList<>();
                        for(int i=0; i< result.length();i++){
                           // Log.e("ENTER","FOR LOOP");
                            Gson gson = new Gson();
                            JSONObject reviewObject = (JSONObject) result.get(i);
                            ReviewModel moviereview = gson.fromJson(reviewObject.toString(),ReviewModel.class);
                            addReviewtodb(moviereview);
                            //Log.e("CONTENT",moviereview.getReviewContent());
                            feedsListReviews.add(moviereview);
                        }
                    }
                    //Log.e("FEEDLIST",feedsListReviews.toString());
                    reviewAdapter = new MoviesReviewAdapter(getContext(),feedsListReviews);
                    reviewList.setAdapter(reviewAdapter);
                    //Log.e("Result review",result.toString());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Toast.makeText(getActivity(), "Failed to fetch reviews!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });

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

             Log.e("MOVIE","NEW FAV");
             ArrayList<ContentProviderOperation> batchOperations = new ArrayList<>(1);
             ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(
                     MoviesProvider.Movies.CONTENT_URI);
             builder.withValue(MovieColumns.POSTER_PATH, movieInsert.getPoster_path());
             builder.withValue(MovieColumns.ORIGINAL_TITLE, movieInsert.getOriginal_title());
             builder.withValue(MovieColumns.OVERVIEW, movieInsert.getOverview());
             builder.withValue(MovieColumns.RELEASE_DATE, movieInsert.getRelease_date());
             builder.withValue(MovieColumns.VOTE_AVERAGE, movieInsert.getVote_average());
             builder.withValue(String.valueOf(MovieColumns.IS_FAVORITE), false);
             builder.withValue(MovieColumns.TYPE, "popular");
             builder.withValue(MovieColumns.ID, movieInsert.getMovieId());
             batchOperations.add(builder.build());
             try{
                 getActivity().getContentResolver().applyBatch(MoviesProvider.AUTHORITY, batchOperations);
             }

             catch (SQLiteConstraintException e){
             */
        try {
            //Log.e("ERROR", "Existed ");
            ContentValues values = new ContentValues();
            values.put(String.valueOf(MovieColumns.IS_FAVORITE), true);
            String[] mArray = {movieInsert.getMovieId()};
            getActivity().getContentResolver().update(MoviesProvider.Movies.CONTENT_URI, values, MovieColumns.ID + "=?", mArray);
        }
             //}

              catch (Exception e) {
                  e.printStackTrace();
                  Log.e("EXCEPTION", "GENERAL");
             }

       //  }
    }

    public void addReviewtodb(ReviewModel item){
        ArrayList<ContentProviderOperation> batchOperations = new ArrayList<>(1);
        ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(
                MoviesProvider.Reviews.CONTENT_URI);
        builder.withValue(ReviewColumns.MOVIE_ID,movie.getMovieId());
        builder.withValue(ReviewColumns.ID,item.getId());
        builder.withValue(ReviewColumns.AUTHOR,item.getReviewAuthor());
        builder.withValue(ReviewColumns.CONTENT,item.getReviewContent());
        builder.withValue(ReviewColumns.URL,item.getUrl());
        batchOperations.add(builder.build());
        try{
            getActivity().getContentResolver().applyBatch(MoviesProvider.AUTHORITY, batchOperations);
        }
        catch (SQLiteConstraintException e){
            Log.e("EXIST", "EXIST");
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
    public void addTrailertodb(MovieTrailerModel item){
        ArrayList<ContentProviderOperation> batchOperations = new ArrayList<>(1);
        ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(
                MoviesProvider.Trailers.CONTENT_URI);
        builder.withValue(TrailerColumns.MOVIE_ID,movie.getMovieId());
        builder.withValue(TrailerColumns.ID,item.getId());
        builder.withValue(TrailerColumns.SITE,item.getSite());
        builder.withValue(TrailerColumns.ISO_639_1,item.getIso_639_1());
        builder.withValue(TrailerColumns.NAME,item.getName());
        builder.withValue(TrailerColumns.TYPE,item.getType());
        builder.withValue(TrailerColumns.KEY,item.getKey());
        builder.withValue(TrailerColumns.ISO_3166_1,item.getIso_3166_1());
        builder.withValue(TrailerColumns.SIZE,item.getSize());
        batchOperations.add(builder.build());
        try{
            getActivity().getContentResolver().applyBatch(MoviesProvider.AUTHORITY, batchOperations);
        }
        catch (SQLiteConstraintException e){
            Log.e("EXIST", "EXIST");
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
        // here user loader manager and return from the database ????
        // ask how to get data from db using loader - cursor ?
        return new CursorLoader(getContext(),MoviesProvider.Movies.CONTENT_URI,null,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // after creating cursor adapter send data to cursor
        data.moveToFirst();
        Log.v("Cursor Object", DatabaseUtils.dumpCursorToString(data));

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // swapCursor to null on the cursoradapter to clear everything out
    }
}
