package com.fesdapps.popularmoviesone.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.fesdapps.popularmoviesone.Models.MovieModel;
import com.fesdapps.popularmoviesone.MovieDetail;
import com.fesdapps.popularmoviesone.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Fabian on 21/07/2016.
 */
public class MoviesAdapter extends ArrayAdapter<MovieModel> {
    private static final String BASE_URL_IMG = "http://image.tmdb.org/t/p/w185/";
    private Context mContext;

    public MoviesAdapter(Activity context, List<MovieModel> movies){
        super(context, 0, movies);
        this.mContext = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final MovieModel movie = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.griditemview, parent, false);
        }
        ImageView movieposter = (ImageView) convertView.findViewById(R.id.posterimg);
        String imgUrl = BASE_URL_IMG+movie.getPoster_path();
        Picasso.with(mContext).load(imgUrl).error(R.mipmap.ic_launcher)
                .placeholder(R.mipmap.ic_launcher).into(movieposter);
        movieposter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MovieDetail.class);
                intent.putExtra("title", movie.getOriginal_title());
                intent.putExtra("posterImg",BASE_URL_IMG+movie.getPoster_path());
                intent.putExtra("synopsis", movie.getOverview());
                intent.putExtra("user_rate", movie.getVote_average());
                intent.putExtra("realease_date", movie.getRelease_date());
                getContext().startActivity(intent);
            }
        });
        return convertView;
    }


}
