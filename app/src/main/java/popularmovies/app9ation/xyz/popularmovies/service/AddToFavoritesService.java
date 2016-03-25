package popularmovies.app9ation.xyz.popularmovies.service;

import android.app.IntentService;
import android.content.ContentValues;
import android.content.Intent;

import popularmovies.app9ation.xyz.popularmovies.Movie;
import popularmovies.app9ation.xyz.popularmovies.data.MovieContract;

/**
 * Created by Bhupendra Singh on 25/3/16.
 */
public class AddToFavoritesService extends IntentService {


     Movie mMovie;

    public AddToFavoritesService() {
        super("AddToFavoritesService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        mMovie = intent.getParcelableExtra("MovieParcel");




            ContentValues values = new ContentValues();

            values.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, mMovie.getId());
            values.put(MovieContract.MovieEntry.COLUMN_TITLE , mMovie.getTitle());
            values.put(MovieContract.MovieEntry.COLUMN_POSTER, mMovie.getPoster());
            values.put(MovieContract.MovieEntry.COLUMN_BACKDROP , mMovie.getBackdrop_path());
            values.put(MovieContract.MovieEntry.COLUMN_OVERVIEW , mMovie.getOverview());
            values.put(MovieContract.MovieEntry.COLUMN_RATING , mMovie.getVote_avg());
            values.put(MovieContract.MovieEntry.COLUMN_DATE, mMovie.getRelease_year());

            getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, values);






    }
}
