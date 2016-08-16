package com.fesdapps.popularmoviesone.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Fabian on 16/08/2016.
 */
public class ReviewModel implements Parcelable {

    String author;
    String content;

    public ReviewModel(String author,String content){
        this.author = author;
        this.content = content;

    }

    protected ReviewModel(Parcel in) {
        author = in.readString();
        content = in.readString();
    }

    public static final Creator<ReviewModel> CREATOR = new Creator<ReviewModel>() {
        @Override
        public ReviewModel createFromParcel(Parcel in) {
            return new ReviewModel(in);
        }

        @Override
        public ReviewModel[] newArray(int size) {
            return new ReviewModel[size];
        }
    };

    public String getReviewContent() {
        return content;
    }

    public void setReviewContent(String content) {
        this.content = content;
    }

    public String getReviewAuthor() {
        return author;
    }

    public void setReviewAuthor(String author) {
        this.author = author;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(author);
        dest.writeString(content);
    }
}
