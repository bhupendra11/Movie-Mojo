package popularmovies.app9ation.xyz.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import popularmovies.app9ation.xyz.popularmovies.service.CheckMovieInFavoritesService;

public class DetailActivity extends AppCompatActivity {

    private int isFavorite;
    FloatingActionButton fab;
    private Movie mMovie;
    private Toast mToast;



    private static final String LOG_TAG = DetailActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putParcelable(DetailActivityFragment.DETAIL_MOVIE,
                    getIntent().getParcelableExtra(DetailActivityFragment.DETAIL_MOVIE));

            DetailActivityFragment fragment = new DetailActivityFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.movie_detail_container, fragment)
                    .commit();

        }

        // Floating action buttononClick handler

        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.fab);

        isFavorite = DetailActivityFragment.isFavorite;

        if(isFavorite ==1){  //Movie in favorites


            myFab.setImageResource(R.drawable.ic_star_selected);

        }
        else{
            myFab.setImageResource(R.drawable.ic_star_unselected);
        }

        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onFabClick(v);
            }
        });



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }



    // For floating action button handling


    public void onFabClick(View view) {

        Log.d(LOG_TAG , "Inside DetailActivity Fab onClick()");


        fab = (FloatingActionButton) view.findViewById(R.id.fab);

        //  Check if the movie is in the database, if it is in the db , delete it

        // If it is not in the database then add it to database

        // get the movie object from intent
        mMovie = getIntent().getParcelableExtra(DetailActivityFragment.DETAIL_MOVIE);



        myResultReceiver resultReceiver =  new myResultReceiver(null);

        Intent intent = new Intent(getApplicationContext(), CheckMovieInFavoritesService.class);
        // Pass this movie object to CheckMovieInFavoritesService
        intent.putExtra("MovieParcel", mMovie);
        intent.putExtra("receiver",resultReceiver);
        startService(intent);






    }


    public class myResultReceiver extends ResultReceiver {

        /**
         * Create a new ResultReceive to receive results.  Your
         * {@link #onReceiveResult} method will be called from the thread running
         * <var>handler</var> if given, or from an arbitrary thread if null.
         *
         * @param handler
         */
        public myResultReceiver(Handler handler) {
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            isFavorite = resultData.getInt("isFav");

            if(isFavorite ==0){
                fab.setImageResource(R.drawable.ic_star_unselected);


                if (mToast != null) {
                    mToast.cancel();
                }
                mToast.makeText(getApplicationContext(),R.string.movie_removed_from_favorites, Toast.LENGTH_SHORT ).show();


            }
            else if(isFavorite ==1){
                fab.setImageResource(R.drawable.ic_star_selected);


                if (mToast != null) {
                    mToast.cancel();
                }
                mToast.makeText(getApplicationContext(),R.string.movie_add_to_favorites, Toast.LENGTH_SHORT).show();

            }
        }
    }




    //For checking if movie in Favorites or not







}
