package popularmovies.app9ation.xyz.popularmovies;

/**
 * Created by Bhupendra Shekhawat on 14/12/15.
 */
public class Movie{
    String poster;
    String overview;
    String title;
    String backdrop_path;
    String popularity;
    String vote_avg;

    public Movie(String poster){
        this.poster = poster;

    }

    public Movie(String poster, String overview, String title, String backdrop_path, String popularity, String vote_avg){
        this.poster = poster;
        this.overview =overview;
        this.title=title;
        this.backdrop_path = backdrop_path;
        this.popularity = popularity;
        this.vote_avg = vote_avg;
    }
}
