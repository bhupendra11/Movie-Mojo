package moviemojo.bhupendra.xyz.moviemojo;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements PosterDisplayFragment.Callback {


    public static boolean mTwoPane ;
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

            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.movie_detail_container, new DetailActivityFragment(), DetailActivityFragment.TAG)
                        .commit();
            }

        } else {
            mTwoPane = false;
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);


    }


    @Override
    public void onItemSelected(Movie movie) {
        if (mTwoPane) {

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

            startActivity(intent);

        }

    }

    //Save and restore the mMovie object across orientation change

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putParcelable(MOVIE_BUNDLE, mMovie);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // static Movie object in DetailActivityFragment so that it can be accessed here in two-pane UI after onSaveInstanceState call in DetailActivityFragment

        mMovie = DetailActivityFragment.movie;

    }
}
