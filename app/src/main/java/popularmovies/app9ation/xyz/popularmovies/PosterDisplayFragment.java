package popularmovies.app9ation.xyz.popularmovies;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import java.util.ArrayList;

import popularmovies.app9ation.xyz.popularmovies.asyncTasks.FavoriteMovieDisplayTask;
import popularmovies.app9ation.xyz.popularmovies.data.MovieContract;
import popularmovies.app9ation.xyz.popularmovies.model.AllMovies;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * A placeholder fragment containing a simple view.
 */
public class PosterDisplayFragment extends Fragment {
    private MovieAdapter movieAdapter;
    private Movie movie;
    private String FETCH_PARAM ="popular";
    //private  ArrayList<Movie> movieList = new ArrayList<Movie>();
    private boolean isSavedInstance =false;

    private Call<AllMovies> callMovies;
    private MovieService.TMDBApi tmdbApi;
    private static final String API_BASE_URL = "http://api.themoviedb.org/3/";
    private AllMovies allMovies;
    private ArrayList<Movie> movieItems = new ArrayList<Movie>();

    public static final String MOVIE_LIST = "MoviesList";

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

        if(savedInstanceState != null && savedInstanceState.containsKey(MOVIE_LIST)){
            isSavedInstance= true;
            movieItems =savedInstanceState.getParcelableArrayList(MOVIE_LIST);
        }
        setHasOptionsMenu(true);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        tmdbApi = retrofit.create(MovieService.TMDBApi.class);

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

            FETCH_PARAM = "popular";
            updateMovies(FETCH_PARAM);

        }

        if(id== R.id.action_sort_rating){

            FETCH_PARAM = "top_rated";
            updateMovies(FETCH_PARAM);
        }
        if(id== R.id.action_sort_favorite){
            // Query the local db to display favorite movies
            displayFavorites();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(MOVIE_LIST,movieItems);
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onStart() {
        super.onStart();
        if(!isSavedInstance && movieItems.isEmpty()) {
            updateMovies(FETCH_PARAM);
        }
        else if(movieItems !=null) {

            movieAdapter.clear();

            Movie curMovie;
            for (int i = 0; i < movieItems.size(); i++) {
                curMovie = movieItems.get(i);
                movieAdapter.add(curMovie);
            }

        }
    }

    public void updateMovies(String sortType){

        /*FetchMovieDataTask movieDataTask = new FetchMovieDataTask(getContext(), getActivity(), movieAdapter);
        movieList.clear();
        movieAdapter.notifyDataSetChanged();
        movieDataTask.execute(sortType);*/

        getMovies(sortType);



    }

    public void displayFavorites(){

       FavoriteMovieDisplayTask favoriteMovieDisplayTask = new FavoriteMovieDisplayTask(getContext(),getActivity() , Movie_COLUMNS, movieAdapter);
        movieItems.clear();
        movieAdapter.notifyDataSetChanged();
        favoriteMovieDisplayTask.execute();



    }

    @Override
    public void onResume() {
        super.onResume();
       // movieAdapter.notifyDataSetChanged();

    }


    public void getMovies(String sort) {


        // For fetching all movies
        callMovies = tmdbApi.getMovies(sort);

        callMovies.enqueue(new retrofit2.Callback<AllMovies>() {
            @Override
            public void onResponse(Call<AllMovies> call, Response<AllMovies> response) {
                allMovies = response.body();
                movieItems = allMovies.getMovieList();


                if(movieItems !=null) {

                    movieAdapter.clear();

                    Movie  curMovie;
                    for (int i = 0; i < movieItems.size(); i++) {
                        curMovie = movieItems.get(i);
                        movieAdapter.add(curMovie);
                    }

                }
                else{
                    // Let the user know that some problem has occurred via a toast
                    Toast.makeText(getContext(),getActivity().getString(R.string.no_movie_data_error) ,Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<AllMovies> call, Throwable t) {

            }

        });


    }



}