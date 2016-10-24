package com.fesdapps.popularmoviesone.data;

import net.simonvt.schematic.annotation.AutoIncrement;
import net.simonvt.schematic.annotation.DataType;
import net.simonvt.schematic.annotation.NotNull;
import net.simonvt.schematic.annotation.PrimaryKey;
import net.simonvt.schematic.annotation.Unique;

/**
 * Created by Fabian on 9/9/16.
 */
public interface TrailerColumns {
    @DataType(DataType.Type.INTEGER)@PrimaryKey
    @AutoIncrement
    public static final String _ID = "_id";

    @DataType(DataType.Type.TEXT)@NotNull @Unique public static final String ID = "Id";
    @DataType(DataType.Type.TEXT)@NotNull public static final String MOVIE_ID = "Movie_Id";
    @DataType(DataType.Type.TEXT)@NotNull public static final String SITE = "Site";
    @DataType(DataType.Type.TEXT)@NotNull public static final String ISO_639_1 = "Iso_639_1";
    @DataType(DataType.Type.TEXT)@NotNull public static final String NAME = "Name";
    @DataType(DataType.Type.TEXT)@NotNull public static final String TYPE = "Type";
    @DataType(DataType.Type.TEXT)@NotNull public static final String KEY = "Key";
    @DataType(DataType.Type.TEXT)@NotNull public static final String ISO_3166_1 = "Iso_3166_1";
    @DataType(DataType.Type.TEXT)@NotNull public static final String SIZE = "Size";
}
