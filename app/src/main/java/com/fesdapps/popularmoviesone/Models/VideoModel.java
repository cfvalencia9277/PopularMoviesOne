package com.fesdapps.popularmoviesone.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Fabian on 16/08/2016.
 */
public class VideoModel implements Parcelable {

    String id;
    String key;
    String name;

    public VideoModel(String id,String key,String name){
        this.id = id;
        this.key = key;
        this.name = name;
    }

    protected VideoModel(Parcel in) {
        id = in.readString();
        key = in.readString();
        name = in.readString();
    }

    public static final Creator<VideoModel> CREATOR = new Creator<VideoModel>() {
        @Override
        public VideoModel createFromParcel(Parcel in) {
            return new VideoModel(in);
        }

        @Override
        public VideoModel[] newArray(int size) {
            return new VideoModel[size];
        }
    };

    public String getKeyVideo() {
        return key;
    }

    public void setKeyVideo(String key) {
        this.key = key;
    }

    public String getNameVideo() {
        return name;
    }

    public void setNameVideo(String name) {
        this.name = name;
    }

    public String getIdVideo() {
        return id;
    }

    public void setIdVideo(String id) {
        this.id = id;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(key);
        dest.writeString(name);
    }
}
