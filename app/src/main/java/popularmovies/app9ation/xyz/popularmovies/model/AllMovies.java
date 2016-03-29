package popularmovies.app9ation.xyz.popularmovies.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import popularmovies.app9ation.xyz.popularmovies.Movie;

/**
 * Created by Bhupendra Singh on 29/3/16.
 */


public class AllMovies {
    @SerializedName("results")
    ArrayList<Movie> movieList;

    public ArrayList<Movie> getMovieList() {
        return movieList;
    }


}