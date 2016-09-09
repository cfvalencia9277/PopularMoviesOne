package com.fesdapps.popularmoviesone.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Fabian on 16/08/2016.
 */
public class ReviewModel implements Parcelable {
    String content;
    String id;
    String author;
    String url;

    public static Creator<ReviewModel> getCREATOR() {
        return CREATOR;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ReviewModel(String content,String author,String id, String url){
        this.content = content;
        this.id = id;
        this.author = author;
        this.url = url;
    }

    protected ReviewModel(Parcel in) {
        content = in.readString();
        id = in.readString();
        author = in.readString();
        url = in.readString();
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
        dest.writeString(content);
        dest.writeString(id);
        dest.writeString(author);
        dest.writeString(url);
    }
}
