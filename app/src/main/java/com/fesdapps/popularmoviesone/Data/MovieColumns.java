package com.fesdapps.popularmoviesone.Data;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;
import net.simonvt.schematic.annotation.Unique;

/**
 * Created by Fabian on 9/9/16.
 */
public interface MovieColumns {

    @DataType(DataType.Type.INTEGER)@PrimaryKey @AutoIncrement public static final String _ID = "_id";

    @DataType(DataType.Type.TEXT)@NotNull @Unique public static final String ID = "ID";
    @DataType(DataType.Type.TEXT)@NotNull public static final String POSTER_PATH = "Poster_Path";
    @DataType(DataType.Type.TEXT)@NotNull public static final String OVERVIEW = "Overview";
    @DataType(DataType.Type.TEXT)@NotNull public static final String RELEASE_DATE = "Release_Date";
    @DataType(DataType.Type.TEXT)@NotNull public static final String ORIGINAL_TITLE = "Original_Title";
    @DataType(DataType.Type.TEXT)@NotNull public static final String VOTE_AVERAGE = "Vote_Average";
    @DataType(DataType.Type.TEXT)@NotNull public static final Boolean IS_FAVORITE = false;

}
