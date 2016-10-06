package com.fesdapps.popularmoviesone.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.fesdapps.popularmoviesone.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Fabian on 06/10/2016.
 */

public class RVAdapter  extends RecyclerViewCursorAdapter<RVAdapter.CustomViewHolder>{

    private static final String TAG = RVAdapter.class.getSimpleName();
    private final Context mContext;

    public RVAdapter(Context context, Uri locationsetting, String sortoder) {
        super(null);
        this.mContext =  context;
        Cursor cursor = mContext.getContentResolver().query(locationsetting, null, null, null, sortoder);
        swapCursor(cursor);
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.griditemview,parent,false);
        return new CustomViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(CustomViewHolder holder, Cursor cursor) {
        int pathIndex = cursor.getColumnIndex("Poster_Path");
        String imgPath = cursor.getString(pathIndex);
        Picasso.with(mContext).load(imgPath).error(R.mipmap.ic_launcher).placeholder(R.mipmap.ic_launcher).into(holder.posterImg);
        holder.posterImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e(TAG,"Clicked on movie poster");
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
