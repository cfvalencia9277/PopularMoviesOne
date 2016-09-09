package com.fesdapps.popularmoviesone.Data;

import net.simonvt.schematic.annotation.Database;
import net.simonvt.schematic.annotation.Table;

/**
 * Created by Fabian on 9/9/16.
 */
@Database(version = MoviesDatabase.VERSION)
public class MoviesDatabase {
    private MoviesDatabase(){}

    public  static final int VERSION = 2;

    @Table(MovieColumns.class) public static final String MOVIES = "Movies";
    @Table(ReviewColumns.class) public static final String REVIEWS = "Reviews";
    @Table(TrailerColumns.class) public static final String TRAILERS = "Trailers";
}
