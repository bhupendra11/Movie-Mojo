package popularmovies.app9ation.xyz.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity implements PosterDisplayFragment.Callback {


    private boolean mTwoPane =false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }


    @Override
    public void onItemSelected(Movie movie) {
        if (mTwoPane) {
            /*Bundle arguments = new Bundle();
            arguments.putParcelable(DetailActivityFragment.DETAIL_MOVIE, movie);

            DetailActivityFragment fragment = new DetailActivityFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, fragment, DetailActivityFragment.TAG)
                    .commit();
                    */
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
