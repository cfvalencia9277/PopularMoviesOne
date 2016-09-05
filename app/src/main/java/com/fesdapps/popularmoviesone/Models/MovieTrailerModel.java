package com.fesdapps.popularmoviesone.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Fabian on 9/5/16.
 */
public class MovieTrailerModel implements Parcelable{

    String site;
    String id;
    String iso_639_1;
    String name;
    String type;
    String key;
    String iso_3166_1;
    String size;

    public MovieTrailerModel(String site,String id,String iso_639_1,String name,String type,String key,String iso_3166_1,String size) {
        this.site = site;
        this.id = id;
        this.iso_639_1 = iso_639_1;
        this.name = name;
        this.type = type;
        this.key = key;
        this.iso_3166_1 = iso_3166_1;
        this.size = size;
    }

    protected MovieTrailerModel(Parcel in) {
        site = in.readString();
        id = in.readString();
        iso_639_1 = in.readString();
        name = in.readString();
        type = in.readString();
        key = in.readString();
        iso_3166_1 = in.readString();
        size = in.readString();
    }

    public static final Creator<MovieTrailerModel> CREATOR = new Creator<MovieTrailerModel>() {
        @Override
        public MovieTrailerModel createFromParcel(Parcel in) {
            return new MovieTrailerModel(in);
        }

        @Override
        public MovieTrailerModel[] newArray(int size) {
            return new MovieTrailerModel[size];
        }
    };

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getIso_3166_1() {
        return iso_3166_1;
    }

    public void setIso_3166_1(String iso_3166_1) {
        this.iso_3166_1 = iso_3166_1;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIso_639_1() {
        return iso_639_1;
    }

    public void setIso_639_1(String iso_639_1) {
        this.iso_639_1 = iso_639_1;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(site);
        dest.writeString(id);
        dest.writeString(iso_639_1);
        dest.writeString(name);
        dest.writeString(type);
        dest.writeString(key);
        dest.writeString(iso_3166_1);
        dest.writeString(size);
    }
}
