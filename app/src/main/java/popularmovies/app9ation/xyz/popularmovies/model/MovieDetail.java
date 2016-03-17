package popularmovies.app9ation.xyz.popularmovies.model;

import java.util.ArrayList;

/**
 * Created by Bhupendra Singh on 17/3/16.
 */
public class MovieDetail {

    public String title;
    public Long movieID;
    public ArrayList<String> reviews;
    public ArrayList<String> trailers;


    public Long getMovieID() {
        return movieID;
    }

    public void setMovieID(Long movieID) {
        this.movieID = movieID;
    }

    public void setTrailers(ArrayList<String> trailers){
        this.trailers = trailers;
    }

    public void setReviews(ArrayList<String> reviews){
        this.reviews = reviews;
    }

    public ArrayList<String> getReviews() {
        return reviews;
    }

    public ArrayList<String> getTrailers() {
        return trailers;
    }
}
