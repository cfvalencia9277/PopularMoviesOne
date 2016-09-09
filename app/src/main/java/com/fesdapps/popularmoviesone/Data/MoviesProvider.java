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

    public static final String AUTHORITY = "com.fesdapps.popularmoviesone.Data.MoviesProvider";

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
    public static Uri withID(long id){
        return buildUri(Path.MOVIES, String.valueOf(id));
    }

}
