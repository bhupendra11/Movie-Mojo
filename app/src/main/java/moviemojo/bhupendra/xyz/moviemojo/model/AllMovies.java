package moviemojo.bhupendra.xyz.moviemojo.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

import moviemojo.bhupendra.xyz.moviemojo.Movie;

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