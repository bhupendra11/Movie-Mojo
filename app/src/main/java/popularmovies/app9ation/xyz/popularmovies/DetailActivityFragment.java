package popularmovies.app9ation.xyz.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import popularmovies.app9ation.xyz.popularmovies.model.MovieDetail;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment {

    private static final String LOG_TAG = DetailActivityFragment.class.getSimpleName();
    private Long movieID;
    private String backdropImagePath;
    private String smallPosterPath;
    private String posterPath;
    private String movieOverview;
    private String movieTitle;
    private String movieYear;
    private String vote_avg;
    //private final String voteFull ="/10";

    private Movie movie;


    //For fething annd storing data in detailFragment
    private static final String API_BASE_URL = "http://api.themoviedb.org/3/";
    private retrofit2.Call<MovieDetail> call;
    private MovieDetail movieDetail;
    private List<MovieDetail> movieDetails;


    public DetailActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getActivity().getIntent();

        if(savedInstanceState != null && savedInstanceState.containsKey("movieParcel")){

            Log.d(LOG_TAG,"Using savedInstanceBundle ");

            movie =savedInstanceState.getParcelable("movieParcel");
        }
        else if(intent.hasExtra("MovieParcel") ) {

            Log.d(LOG_TAG, "Inside intent.hasExtra()");

            movie = intent.getParcelableExtra("MovieParcel");

            movieID = movie.id;
            movieTitle = movie.title;
            backdropImagePath = movie.backdrop_path;
            posterPath = movie.poster;
            movieOverview = movie.overview;
            movieYear = movie.release_year;
            vote_avg = movie.vote_avg;


        }


       // Retrofit for detail movie calls


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        MovieService.TMDBApi tmdbApi = retrofit.create(MovieService.TMDBApi.class);

        call = tmdbApi.getMovieDetail(movieID,"trailers,reviews");

        call.enqueue(new Callback<MovieDetail>() {
            @Override
            public void onResponse(Call<MovieDetail> call, Response<MovieDetail> response) {
                Log.d(LOG_TAG, "Returned API data : "+response.message());
            }

            @Override
            public void onFailure(Call<MovieDetail> call, Throwable t) {

                Log.d(LOG_TAG, "Response failed : " +t.getMessage());

            }
        });

    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_detail, container, false);

        Intent intent = getActivity().getIntent();

        if(intent != null){

            Log.d(LOG_TAG,"Inside intent !=null ");

            Log.d(LOG_TAG,"Backdrop Image url: "+backdropImagePath);
            Log.d(LOG_TAG,"Movie Overview : "+movieOverview);
            Log.d(LOG_TAG,"Movie Title : "+movieTitle);

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




            // For textview transitions



            View[] animatedViews = new View[] {
                    movieTitleTextView, movieYearTextView, movieRatingTextView,  movieOverviewTextview,
            };

            // see here for using the right interpolator is important:
            // http://www.google.com/design/spec/animation/authentic-motion.html#authentic-motion-mass-weight
            // and here for how to use them:
            // http://developer.android.com/guide/topics/graphics/prop-animation.html#interpolators
            Interpolator interpolator = new DecelerateInterpolator();

            for (int i = 0; i < animatedViews.length; ++i) {
                View v = animatedViews[i];

                // let's enable hardware acceleration for better performance
                // http://blog.danlew.net/2015/10/20/using-hardware-layers-to-improve-animation-performance/
                v.setLayerType(View.LAYER_TYPE_HARDWARE, null);

                // initial state: hide the view and move it down slightly
                v.setAlpha(0f);
                v.setTranslationY(75);

                v.animate()
                        .setInterpolator(interpolator)
                        .alpha(1.0f)
                        .translationY(0)
                        // this little calculation here produces the staggered effect we
                        // saw, so each animation starts a bit after the previous one
                        .setStartDelay(300 + 75 * i)
                        .start();
            }

        }

        return  rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("movieParcel", movie);
        super.onSaveInstanceState(outState);
    }



    // Fetch trailers and comments




}
