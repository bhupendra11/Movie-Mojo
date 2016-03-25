package popularmovies.app9ation.xyz.popularmovies.service;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.os.ResultReceiver;

import popularmovies.app9ation.xyz.popularmovies.Movie;
import popularmovies.app9ation.xyz.popularmovies.data.MovieContract;
import popularmovies.app9ation.xyz.popularmovies.util.Log;

/**
 * Created by Bhupendra Singh on 25/3/16.
 */
public class CheckMovieInFavoritesService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     */
    Movie mMovie;
    ResultReceiver resultReceiver;
    int isFavorite =0;
    int numRows =0;
    private static final String LOG_TAG = CheckMovieInFavoritesService.class.getSimpleName();

    public CheckMovieInFavoritesService() {
        super("CheckMovieInFavoritesService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        mMovie = intent.getParcelableExtra("MovieParcel");

        Log.d(LOG_TAG, "The id of Movie passed is "+ mMovie.getId() );


         /*
          Check  if Movie is in DB
         */

        Cursor cursor = getContentResolver().query(
                MovieContract.MovieEntry.CONTENT_URI,
                null,   //projection
                MovieContract.MovieEntry.COLUMN_MOVIE_ID +" =?",
                new String[]{String.valueOf(mMovie.getId())},      // selectionArgs : gets the rows with this movieID
                null             // Sort order

        );

        if(cursor != null) {
            numRows = cursor.getCount();
            cursor.close();
        }



        if(numRows ==1){    // Inside db so delete



           int delete  = getContentResolver().delete(
                    MovieContract.MovieEntry.CONTENT_URI,
                    MovieContract.MovieEntry.COLUMN_MOVIE_ID + " = ?",
                    new String[]{Long.toString(mMovie.getId())}
            );

            isFavorite =0;
        }
        else {             // Not inside db so insert

            ContentValues values = new ContentValues();

            values.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, mMovie.getId());
            values.put(MovieContract.MovieEntry.COLUMN_TITLE , mMovie.getTitle());
            values.put(MovieContract.MovieEntry.COLUMN_POSTER, mMovie.getPoster());
            values.put(MovieContract.MovieEntry.COLUMN_BACKDROP , mMovie.getBackdrop_path());
            values.put(MovieContract.MovieEntry.COLUMN_OVERVIEW , mMovie.getOverview());
            values.put(MovieContract.MovieEntry.COLUMN_RATING , mMovie.getVote_avg());
            values.put(MovieContract.MovieEntry.COLUMN_DATE, mMovie.getRelease_year());

            getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, values);


            isFavorite =1;
        }


    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        resultReceiver = intent.getParcelableExtra("receiver");

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        Bundle bundle = new Bundle();
        bundle.putInt("isFav" , isFavorite);
        resultReceiver.send(200, bundle);
    }
}
