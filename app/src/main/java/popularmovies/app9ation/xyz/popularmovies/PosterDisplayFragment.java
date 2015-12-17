package popularmovies.app9ation.xyz.popularmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

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

/**
 * A placeholder fragment containing a simple view.
 */
public class PosterDisplayFragment extends Fragment {
    private MovieAdapter movieAdapter;

   /* Movie[] movies = {
            new Movie(R.drawable.antman),
            new Movie(R.drawable.intersteller),
            new Movie(R.drawable.jurrasic),
            new Movie(R.drawable.antman),
            new Movie(R.drawable.intersteller),
            new Movie(R.drawable.jurrasic),
            new Movie(R.drawable.antman),
            new Movie(R.drawable.intersteller),
            new Movie(R.drawable.jurrasic),
            new Movie(R.drawable.antman),
            new Movie(R.drawable.intersteller),
            new Movie(R.drawable.jurrasic),
            new Movie(R.drawable.antman),
            new Movie(R.drawable.intersteller),
            new Movie(R.drawable.jurrasic),
            new Movie(R.drawable.antman),
            new Movie(R.drawable.intersteller),
            new Movie(R.drawable.jurrasic),
            new Movie(R.drawable.antman),
            new Movie(R.drawable.intersteller),
            new Movie(R.drawable.jurrasic)

    };

    */

    public PosterDisplayFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);


        movieAdapter = new MovieAdapter(getActivity() , new ArrayList<Movie>());

        GridView gridView  = (GridView) rootView.findViewById(R.id.posters_grid);
        gridView.setAdapter(movieAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                

            }
        } );

        return  rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovies();
    }

    public void updateMovies(){
        FetchMovieDataTask movieDataTask = new FetchMovieDataTask();

        String SORT_PARAM = "popularity.desc";
        movieDataTask.execute(SORT_PARAM);
    }










    public class FetchMovieDataTask extends AsyncTask<String, Void, String[]>{

        private final String LOG_TAG = FetchMovieDataTask.class.getSimpleName();

        private String[] getMoviesPosterDataFromJson(String moviesJsonStr)
                throws JSONException {
            // These are the names of the JSON objects that need to be extracted.
            final String TMDB_RESULTS= "results";
            final String TMDB_POSTER_PATH= "poster_path";
            final String TMDB_OVERVIEW = "overview";
            final String TMDB_TITLE = "title";
            final String TMDB_BACKDROP_PATH = "backdrop_path";
            final String TMDB_POPULARITY = "popularity";
            final String TMDB_VOTE_AVG = "vote_average";
            JSONObject moviesJson = new JSONObject(moviesJsonStr);
            JSONArray moviesArray = moviesJson.getJSONArray(TMDB_RESULTS);
            // TMDB returns json movie objects

            String[] resultStrs = new String[moviesArray.length()];

          //  List<Map<String, String>> listOfMaps = new ArrayList<Map<String, String>>();

            for(int i = 0; i < moviesArray.length(); i++) {
                // For now, using the format "Day, description, hi/low"
                String poster_path;
                String overview;
                String title;
                String backdrop_path;
                String poularity;
                String vote_avg;
                // Get the JSON object representing the movie
                JSONObject movieObject = moviesArray.getJSONObject(i);


                poster_path = movieObject.getString(TMDB_POSTER_PATH);

                resultStrs[i] =poster_path;

            }
               /* for (String s : resultStrs) {
                    Log.v(LOG_TAG, "Forecast entry: " + s);
                }
                */
            return resultStrs;
        }


        @Override
        protected String[] doInBackground(String... params) {


            // These two need to be declared outside the try/catch
            // so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;


            // Will contain the raw JSON response as a string.
            String moviesJsonStr = null;

            String format = "json";


            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are available at TMDB's  API page, at


                final String FORECAST_BASE_URL = "http://api.themoviedb.org/3/discover/movie?";
                final String SORT_PARAM = "sort_by";
                final String APPID_PARAM = "api_key";

                Uri builtUri = Uri.parse(FORECAST_BASE_URL).buildUpon()
                        .appendQueryParameter(SORT_PARAM,params[0])
                        .appendQueryParameter(APPID_PARAM, BuildConfig.TMDB_API_KEY)
                        .build();




                URL url = new URL(builtUri.toString());
                Log.v(LOG_TAG, "Built URI : "+builtUri.toString());

                // Create the request to OpenWeatherMap, and open the connection
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
                  Log.d(LOG_TAG,"Movies Json String: " +moviesJsonStr);
            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
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
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try {
                return getMoviesPosterDataFromJson(moviesJsonStr);
            }
            catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }

            // This will only happen if there was an error getting or parsing the forecast.
            return null;

        }


        @Override
        protected void onPostExecute(String[] results) {
            super.onPostExecute(results);

            if(results !=null) {
                movieAdapter.clear();
                String BASE_POSTER_URL = "http://image.tmdb.org/t/p/w185";
                String poster_path = "";
                for (int i = 0; i < results.length; i++) {
                    poster_path = BASE_POSTER_URL + results[i];
                    movieAdapter.add(new Movie(poster_path));
                }

            }


        }
    }
}
