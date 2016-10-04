package com.fesdapps.popularmoviesone.Data;

import android.net.Uri;

import net.simonvt.schematic.annotation.ContentProvider;
import net.simonvt.schematic.annotation.ContentUri;
import net.simonvt.schematic.annotation.InexactContentUri;
import net.simonvt.schematic.annotation.TableEndpoint;

/**
 * Created by Fabian on 9/9/16.
 */
@ContentProvider(authority = MoviesProvider.AUTHORITY,database = MoviesDatabase.class)
public class MoviesProvider {

    public static final String AUTHORITY = "com.fesdapps.popularmoviesone";

    static final Uri BASE_CONTENT_URI = Uri.parse("content://"+AUTHORITY);

    interface Path{
        String MOVIES = "Movies";
        String TRAILERS = "Trailers";
        String REVIEWS = "Reviews";
    }

    private static Uri buildUri(String ... paths){
        Uri.Builder builder = BASE_CONTENT_URI.buildUpon();
        for(String path: paths){
            builder.appendPath(path);
        }
        return builder.build();
    }

    @TableEndpoint(table = MoviesDatabase.MOVIES)public static class Movies{
        @ContentUri(
                path = Path.MOVIES,
                type = "vnd.android.cursor,dir/Movies",
                defaultSort = MovieColumns.RELEASE_DATE + " ASC")
        public static final Uri CONTENT_URI = buildUri(Path.MOVIES);
    }
    @InexactContentUri(
            name = "MOVIE_ID",
            path = MoviesProvider.Path.MOVIES+"/#",
            type = "vnd.android.cursor,dir/Movies",
            whereColumn = MovieColumns._ID,
            pathSegment = 1)
    public static Uri MoviewithID(long id){
        return buildUri(Path.MOVIES, String.valueOf(id));
    }
    @InexactContentUri(
            name = "MOVIE_IDSERVER",
            path = MoviesProvider.Path.MOVIES+"/#",
            type = "vnd.android.cursor.item/Movies",
            whereColumn = MovieColumns.ID,
            pathSegment = 1)
    public static Uri MoviewithIDSERVER(String id){
        return buildUri(Path.MOVIES, id);
    }
    @TableEndpoint(table = MoviesDatabase.TRAILERS)public static class Trailers{
        @ContentUri(
                path = Path.TRAILERS,
                type = "vnd.android.cursor,dir/Trailers",
                defaultSort = TrailerColumns.NAME+ " ASC")
        public static final Uri CONTENT_URI = buildUri(Path.TRAILERS);
    }
    @InexactContentUri(
            name = "TRAILER_ID",
            path = Path.TRAILERS+"/#",
            type = "vnd.android.cursor,dir/Trailers",
            whereColumn = TrailerColumns._ID,
            pathSegment = 1)
    public static Uri TrailerwithID(long id){
        return buildUri(Path.TRAILERS, String.valueOf(id));
    }
    @TableEndpoint(table = MoviesDatabase.REVIEWS)public static class Reviews{
        @ContentUri(
                path = Path.REVIEWS,
                type = "vnd.android.cursor,dir/Reviews",
                defaultSort = ReviewColumns.AUTHOR+ " ASC")
        public static final Uri CONTENT_URI = buildUri(Path.REVIEWS);
    }
    @InexactContentUri(
            name = "REVIEW_ID",
            path = Path.REVIEWS+"/#",
            type = "vnd.android.cursor,dir/Reviews",
            whereColumn = ReviewColumns._ID,
            pathSegment = 1)
    public static Uri ReviewwithID(long id){
        return buildUri(Path.REVIEWS, String.valueOf(id));
    }

}
