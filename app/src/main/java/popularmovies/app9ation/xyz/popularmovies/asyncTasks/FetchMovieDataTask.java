package popularmovies.app9ation.xyz.popularmovies.asyncTasks;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import popularmovies.app9ation.xyz.popularmovies.BuildConfig;
import popularmovies.app9ation.xyz.popularmovies.Movie;
import popularmovies.app9ation.xyz.popularmovies.MovieAdapter;
import popularmovies.app9ation.xyz.popularmovies.R;
import popularmovies.app9ation.xyz.popularmovies.util.Util;

/**
 * Created by Bhupendra Singh on 29/3/16.
 */


public class FetchMovieDataTask extends AsyncTask<String, Void, ArrayList<Movie>> {

    private  ArrayList<Movie> mMovieList = new ArrayList<Movie>();
    private MovieAdapter movieAdapter;
    private Context mContext;
    private Activity mActivity;

    public FetchMovieDataTask(Context context , Activity activity, MovieAdapter movieAdapter) {

        this.mContext = context;
        this.mActivity = activity;

        this.movieAdapter = movieAdapter;

    }


    private ArrayList<Movie> getMoviesPosterDataFromJson(String moviesJsonStr)
            throws JSONException {
        // These are the names of the JSON objects that need to be extracted.
        final String TMDB_RESULTS= "results";
        final String TMDB_ID ="id";
        final String TMDB_POSTER_PATH= "poster_path";
        final String TMDB_OVERVIEW = "overview";
        final String TMDB_TITLE = "title";
        final String TMDB_BACKDROP_PATH = "backdrop_path";
        final String TMDB_POPULARITY = "popularity";
        final String TMDB_VOTE_AVG = "vote_average";
        final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/";
        final String POSTER_SIZE = "w185";
        final String BACKDROP_SIZE = "w500";
        final String RELEASE_DATE ="release_date";


        JSONObject moviesJson = new JSONObject(moviesJsonStr);
        JSONArray moviesArray = moviesJson.getJSONArray(TMDB_RESULTS);
        // TMDB returns json movie objects


        for(int i = 0; i < moviesArray.length(); i++) {

            //Create a Movie  object
            Movie movie ;

            Long id;
            String poster_path;
            String overview;
            String title;
            String backdrop_path;
            String popularity;
            String vote_avg;
            String display_yearMonth;
            String release_date;

            // Get the JSON object representing the movie
            JSONObject movieObject = moviesArray.getJSONObject(i);

            id = movieObject.getLong(TMDB_ID);
            poster_path = BASE_IMAGE_URL + POSTER_SIZE +movieObject.getString(TMDB_POSTER_PATH) ;
            overview = movieObject.getString(TMDB_OVERVIEW);
            title = movieObject.getString(TMDB_TITLE);
            backdrop_path = BASE_IMAGE_URL + BACKDROP_SIZE +movieObject.getString(TMDB_BACKDROP_PATH);
            popularity = movieObject.getString(TMDB_POPULARITY);
            vote_avg = movieObject.getString(TMDB_VOTE_AVG);

            release_date =movieObject.getString(RELEASE_DATE);
            display_yearMonth = Util.getMonthYear(release_date);
            // Get the movie Year from movie release_date string

            // create a movie object from above parameters
            movie = new Movie(id,poster_path,overview,title,backdrop_path,popularity,vote_avg, display_yearMonth);

            mMovieList.add(movie);


        }

        return mMovieList;
    }


    @Override
    protected ArrayList<Movie> doInBackground(String... params) {

        // These two need to be declared outside the try/catch
        // so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;



        // Will contain the raw JSON response as a string.
        String moviesJsonStr = null;



        try {
            // Construct the URL for the TMDB query
            // Possible parameters are available at TMDB's  API page, at

            final String FETCH_TYPE = params[0];
            final String FORECAST_BASE_URL = "http://api.themoviedb.org/3/movie/"+  FETCH_TYPE  +"?";

            final String APPID_PARAM = "api_key";

            Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                    .appendQueryParameter(APPID_PARAM, BuildConfig.TMDB_API_KEY)
                    .build();

            URL url = new URL(builtUri.toString());

            // Create the request to TMDB, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");

            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.

                return null;
            }

            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                // But it does make debugging a *lot* easier if you print out the completed
                // buffer for debugging.
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }
            moviesJsonStr = buffer.toString();
        } catch (IOException e) {

            // show user with an error message using toast in onPostExecute

            // If the code didn't successfully get the weather data, there's no point in attempting
            // to parse it.
            return  null;
        } finally{
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {

                }
            }
        }

        try {
            return getMoviesPosterDataFromJson(moviesJsonStr);
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

        // This will only happen if there was an error getting or parsing the forecast.
        return null;

    }


    @Override
    protected void onPostExecute(ArrayList<Movie> movieList) {
        super.onPostExecute(movieList);


        if(movieList !=null) {

            movieAdapter.clear();

            Movie curMovie;
            for (int i = 0; i < movieList.size(); i++) {
                curMovie = movieList.get(i);
                movieAdapter.add(curMovie);
            }

        }
        else{
            // Let the user know that some problem has occurred via a toast
            Toast.makeText(mContext,mContext.getString(R.string.no_movie_data_error) ,Toast.LENGTH_SHORT).show();
        }


    }
}