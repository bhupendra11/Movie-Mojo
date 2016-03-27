package popularmovies.app9ation.xyz.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements PosterDisplayFragment.Callback {


    public static boolean mTwoPane ;
    public static final String LOG_TAG = MainActivity.class.getSimpleName();

    //For floating action button
    private int isFavorite;
    private Toast mToast;
    FloatingActionButton fab;
    private Movie mMovie;


    public static final String MOVIE_BUNDLE = "Movie_Bundle";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (findViewById(R.id.movie_detail_container) != null ) {
            // The detail container view will be present only in the large-screen layouts
            // (res/layout-sw600dp). If this view is present, then the activity should be
            // in two-pane mode.
            mTwoPane = true;
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.

            Log.d(LOG_TAG,"Inside MainActivity two pane UI");

            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.movie_detail_container, new DetailActivityFragment(), DetailActivityFragment.TAG)
                        .commit();
            }

            // Floating action button onClick handler
/*

            FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.fab);
            myFab.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                   onFabClick(v);
                }
            });
*/


        } else {
            mTwoPane = false;
        }





        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);



    }


    @Override
    public void onItemSelected(Movie movie) {
        if (mTwoPane) {

            Log.d(LOG_TAG, "Two pane view");

            Bundle arguments = new Bundle();
            arguments.putParcelable(DetailActivityFragment.DETAIL_MOVIE, movie);

            mMovie = movie;

            DetailActivityFragment fragment = new DetailActivityFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, fragment, DetailActivityFragment.TAG)
                    .commit();
        }
        else {
            Intent intent = new Intent(this, DetailActivity.class)
                    .putExtra(DetailActivityFragment.DETAIL_MOVIE, movie);

                 /*   ImageView posterView = (ImageView) findViewById(R.id.moviePoster_image);
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                        ActivityOptionsCompat options =
                                ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                                        posterView,   // The view which starts the transition
                                        getString(R.string.transition_poster) // The transitionName of the view we’re transitioning to
                                );
                        ActivityCompat.startActivity(this, intent, options.toBundle());

                    }
                    else{
                        startActivity(intent);
                    } */

            startActivity(intent);

        }

    }


    public void onFabClick(View view) {

        Log.d(LOG_TAG , "Inside MainActivity Fab onClick()");

      //  fab = (FloatingActionButton) view.findViewById(R.id.fab);

        //  Check if the movie is in the database, if it is in the db , delete it

        // If it is not in the database then add it to database

       /* myResultReceiver resultReceiver =  new myResultReceiver(null);

        Intent intent = new Intent(getApplicationContext(), CheckMovieInFavoritesService.class);
        // Pass this movie object to CheckMovieInFavoritesService
        intent.putExtra("MovieParcel", mMovie);
        intent.putExtra("receiver",resultReceiver);
        startService(intent);


*/



    }


   /* @SuppressLint("ParcelCreator")
    public class myResultReceiver extends ResultReceiver {

       *//* *
         * Create a new ResultReceive to receive results.  Your
         * {@link #onReceiveResult} method will be called from the thread running
         * <var>handler</var> if given, or from an arbitrary thread if null.
         *
         * @param handler
         *//*
        public myResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            isFavorite = resultData.getInt("isFav");

            if(isFavorite ==0){

                if (mToast != null) {
                    mToast.cancel();
                }
                mToast.makeText(getApplicationContext(),R.string.movie_removed_from_favorites, Toast.LENGTH_SHORT ).show();

                fab.setImageResource(R.drawable.ic_star_unselected);


            }
            else if(isFavorite ==1){

                if (mToast != null) {
                    mToast.cancel();
                }
                mToast.makeText(getApplicationContext(),R.string.movie_add_to_favorites, Toast.LENGTH_SHORT).show();

                fab.setImageResource(R.drawable.ic_star_selected);



            }
        }
    }
*/

    //Save and restore the mMovie object across orientation change


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putParcelable(MOVIE_BUNDLE, mMovie);
        Log.d(LOG_TAG," Inside onSaveInstanceState");
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // static Movie object in DetailActivityFragment so that it can be accessed here in two-pane UI after onSaveInstanceState call in DetailActivityFragment

        mMovie = DetailActivityFragment.movie;

        Log.d(LOG_TAG , "Inside onRestoreInstanceState");
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy Called");
    }




}
