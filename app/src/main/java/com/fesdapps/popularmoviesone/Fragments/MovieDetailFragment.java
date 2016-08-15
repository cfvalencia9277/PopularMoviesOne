package com.fesdapps.popularmoviesone.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import cz.msebera.android.httpclient.Header;

/**
 * Created by Fabian on 22/07/2016.
 */
public class MovieDetailFragment extends Fragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://www.google.com", new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                // called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] response) {
                // called when response HTTP status is "200 OK"
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.moviedetail_fragmentview, container, false);
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            MovieModel movie =intent.getParcelableExtra("movie");
            ((TextView) rootView.findViewById(R.id.original_title)).setText(movie.getOriginal_title());
            ImageView img =(ImageView) rootView.findViewById(R.id.poster_detal_img);
            Picasso.with(getContext()).load(movie.getPoster_path()).error(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher).into(img);
            ((TextView) rootView.findViewById(R.id.synopsis)).setText(movie.getOverview());
            ((TextView) rootView.findViewById(R.id.user_rate)).setText("User Rating: "+ movie.getVote_average());
            ((TextView) rootView.findViewById(R.id.release_date)).setText("Realease Data: "+movie.getRelease_date());

        }
        return rootView;
    }
}
