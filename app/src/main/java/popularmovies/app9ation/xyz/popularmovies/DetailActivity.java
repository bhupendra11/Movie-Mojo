package popularmovies.app9ation.xyz.popularmovies;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

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

       /* FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.fab);

        isFavorite = DetailActivityFragment.mIsFavorite;

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

*/

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }



    // For floating action button handling


/*
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

*/






    //For checking if movie in Favorites or not







}
