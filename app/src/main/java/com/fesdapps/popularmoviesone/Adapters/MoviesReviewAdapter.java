package com.fesdapps.popularmoviesone.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.fesdapps.popularmoviesone.models.ReviewModel;
import com.fesdapps.popularmoviesone.R;

import java.util.List;

/**
 * Created by Fabian on 9/9/16.
 */
public class MoviesReviewAdapter  extends RecyclerView.Adapter<MoviesReviewAdapter.CustomViewHolder>  {
    private List<ReviewModel> feedItemList;
    private Context mContext;

    public MoviesReviewAdapter(Context context, List<ReviewModel> feedItemList) {
        this.feedItemList = feedItemList;
        this.mContext = context;
    }
    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.reviewview, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        ReviewModel feedItem = feedItemList.get(position);
        holder.author.setText(feedItem.getReviewAuthor());
        holder.review.setText(feedItem.getReviewContent());
    }

    @Override
    public int getItemCount() {
        return (null != feedItemList ? feedItemList.size() : 0);
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView author;
        protected TextView review;

        public CustomViewHolder(View view) {
            super(view);
            this.author = (TextView) view.findViewById(R.id.author_tv);
            this.review = (TextView) view.findViewById(R.id.review_tv);

        }
    }
}
