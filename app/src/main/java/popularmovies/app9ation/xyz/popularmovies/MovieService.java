package popularmovies.app9ation.xyz.popularmovies;

import popularmovies.app9ation.xyz.popularmovies.model.AllMovies;
import popularmovies.app9ation.xyz.popularmovies.model.AllReviews;
import popularmovies.app9ation.xyz.popularmovies.model.AllTrailers;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Bhupendra Singh on 17/3/16.
 */
public class MovieService {

    public interface TMDBApi{

        // To get all the movies and store them as MovieModel objects

       @GET("movie/{sort}?api_key=" + BuildConfig.TMDB_API_KEY)
        Call<AllMovies> getMovies(
                @Path("sort") String sortCriteria
        );


        // To get the trailers and store them as MovieTrailer objects

        @GET("movie/{id}/videos?api_key=" + BuildConfig.TMDB_API_KEY)
        Call<AllTrailers> getTrailers(
                @Path("id") long movieID
              );

        // To get the trailers and store them as MovieTrailer objects

        @GET("movie/{id}/reviews?api_key=" + BuildConfig.TMDB_API_KEY)
        Call<AllReviews> getReviews(
                @Path("id") long movieID
               );




    }

}


