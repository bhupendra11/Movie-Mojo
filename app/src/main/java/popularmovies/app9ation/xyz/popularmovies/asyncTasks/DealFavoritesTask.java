package popularmovies.app9ation.xyz.popularmovies.asyncTasks;

/**
 * Created by Bhupendra Singh on 29/3/16.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import popularmovies.app9ation.xyz.popularmovies.Movie;
import popularmovies.app9ation.xyz.popularmovies.R;
import popularmovies.app9ation.xyz.popularmovies.data.MovieContract;

/*********************************    AsyncTask to handle favButtonClicks    *********************************************** */


public class DealFavoritesTask extends AsyncTask<Movie, Integer, Integer> {

    private Context mContext;
    private Movie mMovie;
    private int numRows;
    private int isFavorite;
    private View rootView;
    private Button favButton;
    private Toast mToast;

    private boolean dealFavResults;

    public DealFavoritesTask(Context context, View view) {
        mContext = context;
        this.rootView = view;

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
                new String[]{String.valueOf(mMovie.getId())},      // selectionArgs : gets the rows with this movieID
                null             // Sort order

        );

        if (cursor != null) {
            numRows = cursor.getCount();
            cursor.close();
        }


        if (numRows == 1) {    // Inside db so delete


            int delete = mContext.getContentResolver().delete(
                    MovieContract.MovieEntry.CONTENT_URI,
                    MovieContract.MovieEntry.COLUMN_MOVIE_ID + " = ?",
                    new String[]{Long.toString(mMovie.getId())}
            );

            isFavorite = 0;
        } else {             // Not inside db so insert

            ContentValues values = new ContentValues();

            values.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, mMovie.getId());
            values.put(MovieContract.MovieEntry.COLUMN_TITLE, mMovie.getTitle());
            values.put(MovieContract.MovieEntry.COLUMN_POSTER, mMovie.getPoster());
            values.put(MovieContract.MovieEntry.COLUMN_BACKDROP, mMovie.getBackdrop_path());
            values.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, mMovie.getOverview());
            values.put(MovieContract.MovieEntry.COLUMN_RATING, mMovie.getVote_avg());
            values.put(MovieContract.MovieEntry.COLUMN_DATE, mMovie.getRelease_year());

            mContext.getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, values);


            isFavorite = 1;
        }
        return  isFavorite;

    }


    @Override
    protected void onPostExecute(Integer isFav) {
        super.onPostExecute(isFav);


        isFavorite = isFav;

        favButton = (Button) rootView.findViewById(R.id.fav_Button);

        if (isFavorite == 0) {

            if (mToast != null) {
                mToast.cancel();
            }
            mToast.makeText(mContext, R.string.movie_removed_from_favorites, Toast.LENGTH_SHORT).show();
            favButton.setBackgroundResource(R.drawable.not_favorite);


        } else if (isFavorite == 1) {

            if (mToast != null) {
                mToast.cancel();
            }
            mToast.makeText(mContext, R.string.movie_add_to_favorites, Toast.LENGTH_SHORT).show();

            favButton.setBackgroundResource(R.drawable.favorite);


        }

    }

}

