package com.fesdapps.popularmoviesone.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fesdapps.popularmoviesone.Models.MovieModel;
import com.fesdapps.popularmoviesone.R;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Fabian on 22/07/2016.
 */
public class MovieDetailFragment extends Fragment {
    private static final String BASE_URL_IMG = "http://image.tmdb.org/t/p/w185/";
    private static final String BASE_URL = "http://api.themoviedb.org/3/movie/";
    private static final String REVIEW_KEY = "/reviews?api_key=645197735faaceb67ab59d10899455a6";
    private static final String VIDEOS_KEY = "/videos?api_key=645197735faaceb67ab59d10899455a6";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.moviedetail_fragmentview, container, false);
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            MovieModel movie =intent.getParcelableExtra("movie");
            ((TextView) rootView.findViewById(R.id.original_title)).setText(movie.getOriginal_title());
            ImageView img =(ImageView) rootView.findViewById(R.id.poster_detal_img);
            Picasso.with(getContext()).load(BASE_URL_IMG+movie.getPoster_path()).error(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher).into(img);
            ((TextView) rootView.findViewById(R.id.synopsis)).setText(movie.getOverview());
            ((TextView) rootView.findViewById(R.id.user_rate)).setText("User Rating: "+ movie.getVote_average());
            ((TextView) rootView.findViewById(R.id.release_date)).setText("Realease Data: "+movie.getRelease_date());
            fetchdata(movie.getMovieId());
        }
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
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
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
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] errorResponse, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
            }

            @Override
            public void onRetry(int retryNo) {
                // called when request is retried
            }
        });

    }
}
