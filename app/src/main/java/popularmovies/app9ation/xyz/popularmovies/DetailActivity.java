package popularmovies.app9ation.xyz.popularmovies;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle arguments = new Bundle();
        arguments.putParcelable(DetailActivityFragment.DETAIL_MOVIE,
                getIntent().getParcelableExtra(DetailActivityFragment.DETAIL_MOVIE));

        DetailActivityFragment fragment = new DetailActivityFragment();
        fragment.setArguments(arguments);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.movie_detail_container, fragment)
                .commit();


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);





        //Movie  movie = arguments.getParcelable(DetailActivityFragment.DETAIL_MOVIE);



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }



}
