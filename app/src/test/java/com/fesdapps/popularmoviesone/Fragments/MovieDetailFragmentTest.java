package com.fesdapps.popularmoviesone.fragments;

import com.fesdapps.popularmoviesone.R;

import org.junit.After;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Fabian on 16/08/2016.
 */
public class MovieDetailFragmentTest {

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void testOnCreateView() throws Exception {
            assertNotNull(R.layout.moviedetail_fragmentview);
    }

    @Test
    public void testFetchdata() throws Exception {

    }
}