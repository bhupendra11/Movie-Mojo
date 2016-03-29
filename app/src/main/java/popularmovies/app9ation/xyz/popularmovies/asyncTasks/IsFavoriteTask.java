package popularmovies.app9ation.xyz.popularmovies.asyncTasks;

/**
 * Created by Bhupendra Singh on 29/3/16.
 */


import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;

import popularmovies.app9ation.xyz.popularmovies.Movie;
import popularmovies.app9ation.xyz.popularmovies.R;
import popularmovies.app9ation.xyz.popularmovies.data.MovieContract;

/*******************   Async Task to check if the given movie is in favorites or not    *****************/

public class IsFavoriteTask extends AsyncTask<Movie, Integer, Integer> {

    private Context mContext;
    private int isFavoriteMovie;
    private Movie mMovie;
    private Button favButton;
    private int numRows;
    private  int  mIsFavorite;
    private View rootView;

    public IsFavoriteTask(Context context, View rootView) {
        this.mContext = context;
        this.rootView = rootView;

    }


    @Override
    protected Integer doInBackground(Movie... params) {

        mMovie = params[0];

         /*
          Check  if Movie is in DB
         */

        Cursor cursor = mContext.getContentResolver().query(
                MovieContract.MovieEntry.CONTENT_URI,
                null,   //projection
                MovieContract.MovieEntry.COLUMN_MOVIE_ID + " =?",
                new String[]{String.valueOf(mMovie.getMovieId())},      // selectionArgs : gets the rows with this movieID
                null             // Sort order

        );

        if (cursor != null) {
            numRows = cursor.getCount();
            cursor.close();
        }

        if (numRows == 1) {    // Inside db
            isFavoriteMovie = 1;
        } else {             // Not inside db
            isFavoriteMovie = 0;
        }

        return isFavoriteMovie;
    }


    @Override
    protected void onPostExecute(Integer isFav) {
        super.onPostExecute(isFav);



        //Set the icon of Floating action button based on if move in favorites or not


        mIsFavorite = isFav;
        favButton = (Button) rootView.findViewById(R.id.fav_Button);

        if (mIsFavorite == 1) {

            favButton.setBackgroundResource(R.drawable.favorite);
        } else if (mIsFavorite == 0) {

            favButton.setBackgroundResource(R.drawable.not_favorite);
        }


    }

}

