package popularmovies.app9ation.xyz.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    private static final String LOG_TAG = DetailActivityFragment.class.getSimpleName();
    private String backdropImagePath;
    private String smallPosterPath;
    private String posterPath;
    private String movieOverview;
    private String movieTitle;
    private String movieYear;
    private String vote_avg;
    private final String voteFull ="/10";

    private Movie movie;


    public DetailActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState == null || !savedInstanceState.containsKey("movieParcel")){

        }
        else{
            movie =savedInstanceState.getParcelable("movieParcel");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_detail, container, false);

        Intent intent = getActivity().getIntent();

        if(intent != null){

            Log.d(LOG_TAG,"Inside intent !=null ");
            if(intent.hasExtra("MovieParcel") ){

                Log.d(LOG_TAG,"Inside intent.hasExtra()");

               movie = intent.getParcelableExtra("MovieParcel");

                movieTitle = movie.title;
                backdropImagePath = movie.backdrop_path;
                posterPath = movie.poster;
                movieOverview = movie.overview;
                movieYear = movie.release_year;
                vote_avg = movie.vote_avg;



                Log.d(LOG_TAG,"Backdrop Image url: "+backdropImagePath);
                Log.d(LOG_TAG,"Movie Overview : "+movieOverview);

                getActivity().setTitle(movieTitle);


               ImageView backdropPosterView = (ImageView) rootView.findViewById(R.id.backdropPoster_image);




               ImageView smallPosterView  = (ImageView) rootView.findViewById(R.id.moviePoster_image);

                  backdropPosterView.setAdjustViewBounds(true);
                  backdropPosterView.setPadding(0,0,0,0);

                Picasso.with(getContext()).load(backdropImagePath).placeholder(R.drawable.backdrop_placeholder).fit().into(backdropPosterView);


                Picasso.with(getContext()).load(posterPath).placeholder(R.drawable.small_poster_placeholder).fit().into(smallPosterView);

                TextView movieOverviewTextview = (TextView) rootView.findViewById(R.id.movie_overview_textView);
                movieOverviewTextview.setText(movieOverview);

                TextView movieTitleTextView = (TextView) rootView.findViewById(R.id.movieName_textView);
                movieTitleTextView.setText(movieTitle);

                TextView movieYearTextView = (TextView) rootView.findViewById(R.id.movieYear_textView);
                movieYearTextView.setText(movieYear);

                TextView movieRatingTextView = (TextView) rootView.findViewById(R.id.rating_textView);
                movieRatingTextView.setText(vote_avg);

                TextView movieVoteFull = (TextView) rootView.findViewById(R.id.ratingFull_textView);
                movieVoteFull.setText(voteFull);




            }




        }

        return  rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("movieParcel", movie);
        super.onSaveInstanceState(outState);
    }
}
