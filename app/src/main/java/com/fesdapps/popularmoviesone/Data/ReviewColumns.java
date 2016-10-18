package com.fesdapps.popularmoviesone.Data;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;
import net.simonvt.schematic.annotation.Unique;

/**
 * Created by Fabian on 9/9/16.
 */
public interface ReviewColumns {

    @DataType(DataType.Type.INTEGER)@PrimaryKey @AutoIncrement public static final String _ID = "_id";

    @DataType(DataType.Type.TEXT)@NotNull @Unique public static final String ID = "Id";
    @DataType(DataType.Type.TEXT)@NotNull public static final String CONTENT = "Content";
    @DataType(DataType.Type.TEXT)@NotNull public static final String MOVIE_ID = "Movie_Id";
    @DataType(DataType.Type.TEXT)@NotNull public static final String AUTHOR = "Author";
    @DataType(DataType.Type.TEXT)@NotNull public static final String URL = "Url";
}
