package com.fesdapps.popularmoviesone.Models;

/**
 * Created by Fabian on 21/07/2016.
 */
public class MovieModel {
    String poster_path;
    String overview;
    String release_date;
    String original_title;
    String vote_average;

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


    public MovieModel(String poster_path,String overview,String release_date,String original_title,String vote_average){
        this.poster_path = poster_path;
        this.overview = overview;
        this.release_date =release_date;
        this.original_title =original_title;
        this.vote_average =vote_average;


    }

}
