package popularmovies.app9ation.xyz.popularmovies;

import popularmovies.app9ation.xyz.popularmovies.model.MovieDetail;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Bhupendra Singh on 17/3/16.
 */
public class MovieService {

    public interface TMDBApi{

        /*@GET("{movieID}?")
        retrofit2.Call<MovieDetail> getMovieDetail(
            @Path("movieID") String movieID,
            @Query("api_key") String apiKey,
            @Query("append_to_response") String appendToResponse);*/

        @GET("movie/{id}?api_key=" + BuildConfig.TMDB_API_KEY)
        retrofit2.Call<MovieDetail> getMovieDetail(
                @Path("id") long movieID,
                @Query("append_to_response") String appendToResponse);


    }
}


