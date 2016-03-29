package popularmovies.app9ation.xyz.popularmovies.asyncTasks;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.widget.Toast;

import java.util.ArrayList;

import popularmovies.app9ation.xyz.popularmovies.Movie;
import popularmovies.app9ation.xyz.popularmovies.MovieAdapter;
import popularmovies.app9ation.xyz.popularmovies.R;
import popularmovies.app9ation.xyz.popularmovies.data.MovieContract;

/**
 * Created by Bhupendra Singh on 29/3/16.
 */



// AsyncTask to display Favorite movies from database
public class FavoriteMovieDisplayTask extends AsyncTask<Void ,Void,ArrayList<Movie>> {

    private Context mContext;
    private Activity mActivity;

    private  ArrayList<Movie> mMovieList = new ArrayList<Movie>();
    private MovieAdapter movieAdapter;
    private  String[] Movie_COLUMNS;

    public FavoriteMovieDisplayTask(Context context, Activity activity,String[] Movie_COLUMNS, MovieAdapter movieAdapter ) {
        this.mContext = context;
        this.mActivity = activity;
        this.Movie_COLUMNS = Movie_COLUMNS;

        this.movieAdapter = movieAdapter;

    }

    @Override
    protected ArrayList<Movie> doInBackground(Void... params) {



        Cursor cursor = mContext.getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI, Movie_COLUMNS, null,null,null);



        if(cursor != null) {
            while (cursor.moveToNext()) {
                Movie movie = new Movie(cursor);
                mMovieList.add(movie);
            }
        }
        cursor.close();

        return mMovieList;

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