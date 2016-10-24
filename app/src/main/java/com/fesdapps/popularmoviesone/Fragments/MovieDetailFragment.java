package com.fesdapps.popularmoviesone.fragments;

import android.content.ContentProviderOperation;
import android.content.ContentValues;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import com.fesdapps.popularmoviesone.adapters.MovieTrailerAdapter;
import com.fesdapps.popularmoviesone.adapters.MoviesReviewAdapter;
import com.fesdapps.popularmoviesone.data.MovieColumns;
import com.fesdapps.popularmoviesone.data.MoviesProvider;
import com.fesdapps.popularmoviesone.data.ReviewColumns;
import com.fesdapps.popularmoviesone.data.TrailerColumns;
import com.fesdapps.popularmoviesone.models.MovieModel;
import com.fesdapps.popularmoviesone.models.MovieTrailerModel;
import com.fesdapps.popularmoviesone.models.ReviewModel;
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
public class MovieDetailFragment extends Fragment  {
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
    String movieId;
    TextView originalTitle;
    TextView overview;
    TextView releadeDate;

    ImageView img;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView;
        if (getArguments().getBoolean("first")) {
            Log.e("ARGS",String.valueOf(getArguments().getBoolean("first")));
            rootView = inflater.inflate(R.layout.no_movie, container, false);
        }
        else{
            rootView = inflater.inflate(R.layout.moviedetail_fragmentview, container, false);
            movieId = getArguments().getString("movieId");
            Cursor c = getActivity().getContentResolver().query(MoviesProvider.Movies.CONTENT_URI,
                    null, MovieColumns.ID + "= ?",
                    new String[] { movieId }, null);
            movie = createMovieModel(c);
            img=(ImageView) rootView.findViewById(R.id.poster_detal_img);
            originalTitle = ((TextView) rootView.findViewById(R.id.original_title));
            overview = ((TextView) rootView.findViewById(R.id.synopsis));
            releadeDate = ((TextView) rootView.findViewById(R.id.release_date));
            fav_btn = (Button) rootView.findViewById(R.id.fav_btn);
            fav_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        insertData();
                    }
                });
            videoList = (RecyclerView) rootView.findViewById(R.id.movieTrailer);
            videoList.setLayoutManager(new LinearLayoutManager(getActivity()));
            reviewList = (RecyclerView) rootView.findViewById(R.id.movieReview);
            reviewList.setLayoutManager(new LinearLayoutManager(getActivity()));
            setViewValues();
            fetchdata(movieId);
        }
        return rootView;
    }
    public void setViewValues(){
        originalTitle.setText(movie.getOriginal_title());
        Picasso.with(getContext()).load(BASE_URL_IMG+movie.getPoster_path())
                .error(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher).into(img);
        overview.setText(movie.getOverview());
        releadeDate.setText("Realease Data: "+movie.getRelease_date());
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
                    trailerAdapter = new MovieTrailerAdapter(getContext(),feedsListTrailers);
                    videoList.setAdapter(trailerAdapter);
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
                    str = new String(response, "UTF-8");
                    JSONObject serversent = new JSONObject(str);
                    JSONArray result = serversent.getJSONArray("results");
                    if(result != null){
                        feedsListReviews = new ArrayList<>();
                        for(int i=0; i< result.length();i++){
                            Gson gson = new Gson();
                            JSONObject reviewObject = (JSONObject) result.get(i);
                            ReviewModel moviereview = gson.fromJson(reviewObject.toString(),ReviewModel.class);
                            addReviewtodb(moviereview);
                            feedsListReviews.add(moviereview);
                        }
                    }
                    reviewAdapter = new MoviesReviewAdapter(getContext(),feedsListReviews);
                    reviewList.setAdapter(reviewAdapter);
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
    public void insertData(){
        try {
            ContentValues values = new ContentValues();
            values.put(String.valueOf(MovieColumns.IS_FAVORITE), true);
            String[] mArray = {movieId};
            getActivity().getContentResolver().update(MoviesProvider.Movies.CONTENT_URI,
                    values, MovieColumns.ID + "=?", mArray);
        }
        catch (Exception e) {
            e.printStackTrace();
            Log.e("EXCEPTION", "GENERAL");
        }
    }
    public void addReviewtodb(ReviewModel item){
        ArrayList<ContentProviderOperation> batchOperations = new ArrayList<>(1);
        ContentProviderOperation.Builder builder = ContentProviderOperation.newInsert(
                MoviesProvider.Reviews.CONTENT_URI);
        builder.withValue(ReviewColumns.MOVIE_ID,movieId);
        builder.withValue(ReviewColumns.ID,item.getId());
        builder.withValue(ReviewColumns.AUTHOR,item.getReviewAuthor());
        builder.withValue(ReviewColumns.CONTENT,item.getReviewContent());
        builder.withValue(ReviewColumns.URL,item.getUrl());
        batchOperations.add(builder.build());
        try{
            getActivity().getContentResolver()
                    .applyBatch(MoviesProvider.AUTHORITY, batchOperations);
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
        builder.withValue(TrailerColumns.MOVIE_ID,movieId);
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
            getActivity().getContentResolver()
                    .applyBatch(MoviesProvider.AUTHORITY, batchOperations);
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
    MovieModel createMovieModel(Cursor cursor){
        int idIndex = cursor.getColumnIndexOrThrow("ID");
        int posterindex = cursor.getColumnIndexOrThrow("Poster_Path");
        int overviewIndex = cursor.getColumnIndexOrThrow("Overview");
        int releadeIndex = cursor.getColumnIndexOrThrow("Release_Date");
        int originalIndex = cursor.getColumnIndexOrThrow("Original_Title");
        int voteIndex = cursor.getColumnIndexOrThrow("Vote_Average");
        cursor.moveToFirst();
        String id = cursor.getString(idIndex);
        String poster_path= cursor.getString(posterindex);
        String overview= cursor.getString(overviewIndex);
        String release_date= cursor.getString(releadeIndex);
        String original_title= cursor.getString(originalIndex);
        String vote_average= cursor.getString(voteIndex);
        return new MovieModel(id,poster_path,overview,release_date,original_title,vote_average);
    }
}
