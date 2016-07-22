package com.fesdapps.popularmoviesone.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fesdapps.popularmoviesone.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Fabian on 22/07/2016.
 */
public class MovieDetailFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.moviedetail_fragmentview, container, false);
        Intent intent = getActivity().getIntent();
        if (intent != null) {
            if(intent.hasExtra("title")){
                String movieData;
                movieData = intent.getStringExtra("title");
                ((TextView) rootView.findViewById(R.id.original_title)).setText(movieData);
            }
            if(intent.hasExtra("posterImg")){
                String movieData;
                movieData = intent.getStringExtra("posterImg");
                ImageView img =(ImageView) rootView.findViewById(R.id.poster_detal_img);
                Picasso.with(getContext()).load(movieData).error(R.mipmap.ic_launcher)
                        .placeholder(R.mipmap.ic_launcher).into(img);
            }
            if(intent.hasExtra("synopsis")){
                String movieData;
                movieData = intent.getStringExtra("synopsis");
                ((TextView) rootView.findViewById(R.id.synopsis)).setText(movieData);
            }
            if(intent.hasExtra("user_rate")){
                String movieData;
                movieData = intent.getStringExtra("user_rate");
                ((TextView) rootView.findViewById(R.id.user_rate)).setText("User Rating: "+movieData);
            }
            if(intent.hasExtra("realease_date")){
                String movieData;
                movieData = intent.getStringExtra("realease_date");
                ((TextView) rootView.findViewById(R.id.release_date)).setText("Realease Data: "+movieData);
            }

        }
        return rootView;
    }
}
