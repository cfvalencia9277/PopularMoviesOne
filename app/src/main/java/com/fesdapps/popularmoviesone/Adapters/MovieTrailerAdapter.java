package com.fesdapps.popularmoviesone.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fesdapps.popularmoviesone.models.MovieTrailerModel;
import com.fesdapps.popularmoviesone.R;

import java.util.List;

/**
 * Created by Fabian on 9/5/16.
 */
public class MovieTrailerAdapter extends RecyclerView.Adapter<MovieTrailerAdapter.CustomViewHolder>  {
    private List<MovieTrailerModel> feedItemList;
    private Context mContext;

    public MovieTrailerAdapter(Context context, List<MovieTrailerModel> feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = context;
    }
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailerview, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        final MovieTrailerModel feedItem = feedItemList.get(position);
        holder.textView.setText(feedItem.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //https://www.youtube.com/watch?v=
                // add the key of the trailer
                Intent trailerintent = new Intent();
                trailerintent.setAction(Intent.ACTION_VIEW);
                trailerintent.setData(Uri.parse("http://www.youtube.com/watch?v="+feedItem.getKey()));
                if (trailerintent.resolveActivity(mContext.getPackageManager()) != null) {
                    mContext.startActivity(trailerintent);
                }
                //mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v="+feedItem.getKey())));
            }
        });
    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView textView;

        public CustomViewHolder(View view) {
            super(view);
            this.textView = (TextView) view.findViewById(R.id.trailername);
        }
    }

}
