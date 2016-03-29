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

import java.util.ArrayList;

import popularmovies.app9ation.xyz.popularmovies.asyncTasks.FavoriteMovieDisplayTask;
import popularmovies.app9ation.xyz.popularmovies.asyncTasks.FetchMovieDataTask;
import popularmovies.app9ation.xyz.popularmovies.data.MovieContract;


/**
 * A placeholder fragment containing a simple view.
 */
public class PosterDisplayFragment extends Fragment {
    private MovieAdapter movieAdapter;
    private Movie movie;
    private String FETCH_PARAM ="popular";
    private  ArrayList<Movie> movieList = new ArrayList<Movie>();
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
        outState.putParcelableArrayList("MoviesList",movieList);
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onStart() {
        super.onStart();
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

        FetchMovieDataTask movieDataTask = new FetchMovieDataTask(getContext(), getActivity(), movieAdapter);
        movieList.clear();
        movieAdapter.notifyDataSetChanged();
        movieDataTask.execute(sortType);
    }

    public void displayFavorites(){

       FavoriteMovieDisplayTask favoriteMovieDisplayTask = new FavoriteMovieDisplayTask(getContext(),getActivity() , Movie_COLUMNS, movieAdapter);
        movieList.clear();
        movieAdapter.notifyDataSetChanged();
        favoriteMovieDisplayTask.execute();



    }

    @Override
    public void onResume() {
        super.onResume();
       // movieAdapter.notifyDataSetChanged();

    }

}