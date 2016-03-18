package popularmovies.app9ation.xyz.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import popularmovies.app9ation.xyz.popularmovies.MovieService.TMDBApi;
import popularmovies.app9ation.xyz.popularmovies.model.AllReviews;
import popularmovies.app9ation.xyz.popularmovies.model.AllReviews.MovieReview;
import popularmovies.app9ation.xyz.popularmovies.model.AllTrailers;
import popularmovies.app9ation.xyz.popularmovies.model.AllTrailers.MovieTrailer;
import popularmovies.app9ation.xyz.popularmovies.util.Log;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailActivityFragment extends Fragment implements  View.OnClickListener{

    private static final String LOG_TAG = DetailActivityFragment.class.getSimpleName();
    private Long movieID;
    private String backdropImagePath;
    private String smallPosterPath;
    private String posterPath;
    private String movieOverview;
    private String movieTitle;
    private String movieYear;
    private String vote_avg;

    private Movie movie;


    //For fething and storing data in detailFragment
    private static final String API_BASE_URL = "http://api.themoviedb.org/3/";
    private Call<AllTrailers> callTrailer;
    private Call<AllReviews> callReviews;
    private AllTrailers allTrailers;
    private List<AllTrailers.MovieTrailer> trailerItems;
    private AllReviews allReviews;
    private List<AllReviews.MovieReview> reviewItems = new ArrayList<MovieReview>();
    private TMDBApi tmdbApi;
    private View rootView;



    //For the UI of reviews and trailers

    ViewGroup mReviewsView ;
    TextView mReviewsHeader ;

    HorizontalScrollView mTrailersScrollView;
    TextView mTrailersHeader;
    ViewGroup mTrailersView;




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


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         rootView =  inflater.inflate(R.layout.fragment_detail, container, false);

        Intent intent = getActivity().getIntent();

        if(intent != null){

            if(intent.hasExtra("MovieParcel") ) {

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

            /////////////////////

            Log.d(LOG_TAG,"Inside intent !=null ");

            Log.d(LOG_TAG,"Backdrop Image url: "+backdropImagePath);
            Log.d(LOG_TAG,"Movie Overview : "+movieOverview);
            Log.d(LOG_TAG,"Movie Title : "+movieTitle);

            getActivity().setTitle(movieTitle);


            ImageView backdropPosterView = (ImageView) rootView.findViewById(R.id.backdropPoster_image);




            ImageView smallPosterView  = (ImageView) rootView.findViewById(R.id.moviePoster_image);

            backdropPosterView.setAdjustViewBounds(true);
            backdropPosterView.setPadding(0, 0, 0, 0);

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


            Log.d(LOG_TAG, "Loaded the textViews");

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

            Log.d(LOG_TAG, "Played the animation of textviews");


            // for displaying list of reviews




            // Retrofit for detail movie calls
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(API_BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

             tmdbApi = retrofit.create(TMDBApi.class);




            // for the UI of reviews and trailers




        }

        return  rootView;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);



        // For fetching trailers
        callTrailer = tmdbApi.getTrailers(movieID);

        callTrailer.enqueue(new Callback<AllTrailers>() {
            @Override
            public void onResponse(Call<AllTrailers> call, Response<AllTrailers> response) {
                Log.d(LOG_TAG, "Returned API data : " + response.message());
                allTrailers = response.body();
                trailerItems = allTrailers.getTrailerList();

                for (AllTrailers.MovieTrailer item : trailerItems) {
                    Log.d(LOG_TAG, "Trailer title= " + item.getTrailerTitle() + "\n Trailer id = " + item.getId() + ", " +
                                    "\nKey= " + item.getKey() + ",\nSite = " + item.getSite()
                    );
                }
                Log.d(LOG_TAG, "TrailerItems size = "+trailerItems.size());

                mTrailersHeader = (TextView) rootView.findViewById(R.id.trailers_heading_textview);
                mTrailersScrollView = (HorizontalScrollView) rootView.findViewById(R.id.trailer_container);
                mTrailersView = (ViewGroup) rootView.findViewById(R.id.trailers);

                boolean hasTrailers = !trailerItems.isEmpty();
                mTrailersHeader.setVisibility(hasTrailers?View.VISIBLE :View.GONE);
                mTrailersScrollView.setVisibility(hasTrailers?View.VISIBLE:View.GONE);
                if(hasTrailers){
                    addTrailers(trailerItems);
                }


            }

            @Override
            public void onFailure(Call<AllTrailers> call, Throwable t) {

                Log.d(LOG_TAG, "Response failed : " + t.getMessage());

            }
        });


        // For fetching reviews
        callReviews = tmdbApi.getReviews(movieID);

        callReviews.enqueue(new Callback<AllReviews>() {
            @Override
            public void onResponse(Call<AllReviews> call, Response<AllReviews> response) {
                Log.d(LOG_TAG, "Returned API data : " + response.message());
                allReviews = response.body();
                reviewItems = allReviews.getReviewsList();

                   /* for (AllReviews.MovieReview item : reviewItems) {
                        Log.d(LOG_TAG, " Review id = " + item.getId() + "\n Review author= " + item.getAuthor() +
                                        "\n Content= " + item.getContent() + "\n Url = " + item.getUrl()
                        );
                    }*/

                Log.d(LOG_TAG, "ReviewItems size=" + reviewItems.size());


                mReviewsHeader = (TextView) rootView.findViewById(R.id.reviews_heading_textview);
                mReviewsView  = (ViewGroup) rootView.findViewById(R.id.reviews);

                boolean hasReviews = !reviewItems.isEmpty();
                mReviewsHeader.setVisibility(hasReviews ? View.VISIBLE : View.GONE);
                mReviewsView.setVisibility(hasReviews ? View.VISIBLE : View.GONE);
                if (hasReviews) {
                    addReviews(reviewItems);
                }

            }

            @Override
            public void onFailure(Call<AllReviews> call, Throwable t) {
                Log.d(LOG_TAG, "Response failed : " + t.getMessage());
            }
        });


    }

    // On click listener for various views in the DetailActivityFragement
    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.trailer_thumb){  // clicked on the trailer thumbnail , launch youtube intent
            String trailerUrl = (String) v.getTag();
            Intent playVideoIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailerUrl));
            startActivity(playVideoIntent);

        }

    }

    private void addTrailers(List<MovieTrailer> trailers) {
        mTrailersView.removeAllViews();

        LayoutInflater inflater = getActivity().getLayoutInflater();
        Picasso picasso = Picasso.with(getActivity());
        for(MovieTrailer trailer : trailers){
            ViewGroup trailerContainer = (ViewGroup) inflater.inflate(R.layout.trailer_item, mTrailersView , false);

            ImageView trailerThumbnail = (ImageView) trailerContainer.findViewById(R.id.trailer_thumb);
            trailerThumbnail.setTag(MovieTrailer.getTrailerUrl(trailer));
            trailerThumbnail.setOnClickListener(this);

             picasso.load(MovieTrailer.getThumbnailUrl(trailer))
                     .resizeDimen(R.dimen.trailer_width , R.dimen.trailer_height)
                     .centerCrop()
                     .into(trailerThumbnail);

        mTrailersView.addView(trailerContainer);


        }
    }

    private void addReviews(List<MovieReview> reviews) {
        mReviewsView.removeAllViews();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        for (MovieReview review : reviews) {
            ViewGroup reviewContainer = (ViewGroup) inflater.inflate(R.layout.reviews_item, mReviewsView,
                    false);


            TextView reviewAuthor = (TextView) reviewContainer.findViewById(R.id.review_author_textView);
            TextView reviewContent = (TextView) reviewContainer.findViewById(R.id.review_content_textView);
            reviewAuthor.setText(review.getAuthor());
            reviewContent.setText(review.getContent());
            /*reviewContainer.setOnClickListener(this);
            reviewContainer.setTag(review);*/
            mReviewsView.addView(reviewContainer);




        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("movieParcel", movie);
        super.onSaveInstanceState(outState);
    }



}