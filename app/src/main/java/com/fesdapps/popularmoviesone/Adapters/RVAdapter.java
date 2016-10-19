package com.fesdapps.popularmoviesone.Adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fesdapps.popularmoviesone.Data.MovieColumns;
import com.fesdapps.popularmoviesone.MovieDetail;
import com.fesdapps.popularmoviesone.R;
import com.squareup.picasso.Picasso;

import static java.security.AccessController.getContext;

/**
 * Created by Fabian on 06/10/2016.
 */

public class RVAdapter  extends RecyclerViewCursorAdapter<RVAdapter.CustomViewHolder>{

    private static final String TAG = RVAdapter.class.getSimpleName();
    private final Context mContext;
    private static final String BASE_URL_IMG = "http://image.tmdb.org/t/p/w185/";

    public RVAdapter(Context context) {
        super(null);
        this.mContext =  context;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.griditemview,parent,false);
        return new CustomViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(CustomViewHolder holder, Cursor cursor) {
        int pathIndex = cursor.getColumnIndex(MovieColumns.POSTER_PATH);
        String imgPath = cursor.getString(pathIndex);
        int idIndex = cursor.getColumnIndex(MovieColumns.ID);
        final String movieId = cursor.getString(idIndex);
        Picasso.with(mContext).load(BASE_URL_IMG+imgPath).error(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher).into(holder.posterImg);
        holder.posterImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG,"Clicked on movie:  "+movieId);
                Intent intent = new Intent(mContext, MovieDetail.class);
                intent.putExtra("movie",movieId);
                mContext.startActivity(intent);
            }
        });
    }

    public static class CustomViewHolder extends RecyclerView.ViewHolder
    {
        ImageView posterImg;
        CustomViewHolder(View itemView)
        {
            super(itemView);
            posterImg = (ImageView) itemView.findViewById(R.id.posterimg);
        }
    }
}
