package popularmovies.app9ation.xyz.popularmovies;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
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

import popularmovies.app9ation.xyz.popularmovies.data.MovieContract;
import popularmovies.app9ation.xyz.popularmovies.util.Util;


/**
 * A placeholder fragment containing a simple view.
 */
public class PosterDisplayFragment extends Fragment {
    private MovieAdapter movieAdapter;
    private Movie movie;
    private String FETCH_PARAM ="popular";
    //private String VOTE_COUNT_MIN ="200";
    private  ArrayList<Movie> movieList = new ArrayList<Movie>();
    private static final String LOG_TAG = PosterDisplayFragment.class.getSimpleName();
    private boolean isSavedInstance =false;

    private static final String[] Movie_COLUMNS = {
            //Array of all the column names in Movie table
            MovieContract.MovieEntry.TABLE_NAME + "." + MovieContract.MovieEntry._ID,
            MovieContract.MovieEntry.COLUMN_MOVIE_ID,
            MovieContract.MovieEntry.COLUMN_TITLE,
            MovieContract.MovieEntry.COLUMN_POSTER,
            MovieContract.MovieEntry.COLUMN_BACKDROP,
            MovieContract.MovieEntry.COLUMN_OVERVIEW,
            MovieContract.MovieEntry.COLUMN_RATING,
            MovieContract.MovieEntry.COLUMN_DATE,

    };

    // These indices are tied to MOVIE_COLUMNS.  If MOVIE_COLUMNS changes, these
    // must change.
    static final int COL_ID = 0;
    static final int COL_MOVIE_ID =1;
    static final int COL_TITLE = 2;
    static final int COL_POSTER = 3;
    static final int COL_BACKDROP = 4;
    static final int COL_OVERVIEW = 5;
    static final int COL_RATING = 6;
    static final int COL_DATE = 7;



    public PosterDisplayFragment() {
    }


    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callback {
        void onItemSelected(Movie movie);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState != null && savedInstanceState.containsKey("MoviesList")){
            Log.d(LOG_TAG,"Using savedInstanceBundle ");
            isSavedInstance= true;
            movieList =savedInstanceState.getParcelableArrayList("MoviesList");
        }
        setHasOptionsMenu(true);
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
                movie = movieAdapter.getItem(position);

                ((Callback) getActivity()).onItemSelected(movie);


            }
        } );

        return  rootView;
    }

    public void onCreateOptionsMenu(Menu menu , MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu,inflater);
        inflater.inflate(R.menu.menu_fragment_main, menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.action_sort_popularity){

            Log.d(LOG_TAG , "Sort Acc to Popularity");
            FETCH_PARAM = "popular";
            updateMovies(FETCH_PARAM);

        }

        if(id== R.id.action_sort_rating){

            Log.d(LOG_TAG , "Sort Acc to Rating");
            FETCH_PARAM = "top_rated";
            updateMovies(FETCH_PARAM);
        }
        if(id== R.id.action_sort_favorite){
            // Query the local db to display favorite movies
            Log.d(LOG_TAG , "Display only Favorite Movies");
            displayFavorites();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("MoviesList",movieList);
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.d(LOG_TAG,"inside onStart");
        if(!isSavedInstance && movieList.isEmpty()) {
            updateMovies(FETCH_PARAM);
        }
        else if(movieList !=null) {

            movieAdapter.clear();

            Movie curMovie;
            for (int i = 0; i < movieList.size(); i++) {
                curMovie = movieList.get(i);
                movieAdapter.add(curMovie);
            }

        }
    }

    public void updateMovies(String sortType){

        Log.d(LOG_TAG, "UpdateMovies called with SortType : " +sortType);
        FetchMovieDataTask movieDataTask = new FetchMovieDataTask();
        movieList.clear();
        movieAdapter.notifyDataSetChanged();
        movieDataTask.execute(sortType);
    }

    public void displayFavorites(){

       FavoriteMovieDisplayTask favoriteMovieDisplayTask = new FavoriteMovieDisplayTask(getActivity());
        movieList.clear();
        movieAdapter.notifyDataSetChanged();
        favoriteMovieDisplayTask.execute();



    }



    public class FetchMovieDataTask extends AsyncTask<String, Void, ArrayList<Movie>>{

        private final String LOG_TAG = FetchMovieDataTask.class.getSimpleName();



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

                movieList.add(movie);


            }

            return movieList;
        }


        @Override
        protected ArrayList<Movie> doInBackground(String... params) {

            Log.d(LOG_TAG, "Inside FetchMovieDataTask's doInBackground method ");


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
                Log.d(LOG_TAG, "Built URI : "+builtUri.toString());

                Log.d(LOG_TAG,"Querying the TMDB Api" );
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
                //  Log.d(LOG_TAG,"Movies Json String: " +moviesJsonStr+"\n");
            } catch (IOException e) {

                Log.e(LOG_TAG, "Error ", e);
                // show user with an error message using toast

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
        protected void onPostExecute(ArrayList<Movie> movieList) {
            super.onPostExecute(movieList);

            Log.d(LOG_TAG, "Inside FetchMovieDataTask's onPostExecute() method ");

            if(movieList !=null) {

                Log.d(LOG_TAG, "Inside FetchMovieDataTask's onPostExecute() method : movieList != null");

                movieAdapter.clear();

                Movie curMovie;
                for (int i = 0; i < movieList.size(); i++) {
                    curMovie = movieList.get(i);
                    movieAdapter.add(curMovie);
                }

            }
            else{
                // Let the user know that some problem has occurred via a toast
                Toast.makeText(getContext(),getActivity().getString(R.string.no_movie_data_error) ,Toast.LENGTH_SHORT).show();
            }


        }
    }



    // AsyncTask to display Favorite movies from database
    public class FavoriteMovieDisplayTask extends  AsyncTask<Void ,Void,ArrayList<Movie>>{

        private Context mContext;

        public FavoriteMovieDisplayTask(Context context) {
            mContext = context;

        }

        @Override
        protected ArrayList<Movie> doInBackground(Void... params) {

            Cursor cursor = mContext.getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI, Movie_COLUMNS, null,null,null);

            if(cursor != null) {
                while (cursor.moveToNext()) {
                    Movie movie = new Movie(cursor);
                    movieList.add(movie);
                }
            }
            cursor.close();

            return movieList;

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
                Toast.makeText(getContext(),getActivity().getString(R.string.no_movie_data_error) ,Toast.LENGTH_SHORT).show();
            }


        }

    }

}