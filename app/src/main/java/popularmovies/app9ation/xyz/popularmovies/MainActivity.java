package popularmovies.app9ation.xyz.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements PosterDisplayFragment.Callback {


    public static boolean mTwoPane ;
    public static final String LOG_TAG = MainActivity.class.getSimpleName();


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
        } else {
            mTwoPane = false;
        }


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }


    @Override
    public void onItemSelected(Movie movie) {
        if (mTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putParcelable(DetailActivityFragment.DETAIL_MOVIE, movie);

            DetailActivityFragment fragment = new DetailActivityFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, fragment, DetailActivityFragment.TAG)
                    .commit();
        }
        else {
            Intent intent = new Intent(this, DetailActivity.class)
                    .putExtra(DetailActivityFragment.DETAIL_MOVIE, movie);

            ImageView posterView = (ImageView) findViewById(R.id.moviePoster_image);


        /*    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                ActivityOptionsCompat options =
                        ActivityOptionsCompat.makeSceneTransitionAnimation(this,
                                posterView,   // The view which starts the transition
                                getString(R.string.transition_poster) // The transitionName of the view we’re transitioning to
                        );
                ActivityCompat.startActivity(this, intent, options.toBundle());

            }
            else{
                startActivity(intent);
            }
*/
            startActivity(intent);

        }

    }


}
