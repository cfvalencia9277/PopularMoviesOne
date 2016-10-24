package com.fesdapps.popularmoviesone.models;

import android.os.Parcel;
import android.os.Parcelable;



/**
 * Created by Fabian on 21/07/2016.
 */
public class MovieModel  implements Parcelable{



    private String id;
    String poster_path;
    String overview;
    String release_date;
    String original_title;
    String vote_average;


    protected MovieModel(Parcel in) {
        id = in.readString();
        poster_path = in.readString();
        overview = in.readString();
        release_date = in.readString();
        original_title = in.readString();
        vote_average = in.readString();
    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel in) {
            return new MovieModel(in);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };

    public String getMovieId() {
        return id;
    }

    public void setMovieId(String id) {
        this.id = id;
    }
    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }


    public MovieModel(String id,String poster_path,String overview,String release_date,String original_title,String vote_average){
        this.id = id;
        this.poster_path = poster_path;
        this.overview = overview;
        this.release_date =release_date;
        this.original_title =original_title;
        this.vote_average =vote_average;


    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(poster_path);
        dest.writeString(overview);
        dest.writeString(release_date);
        dest.writeString(original_title);
        dest.writeString(vote_average);
    }
}
