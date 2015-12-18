package popularmovies.app9ation.xyz.popularmovies;

import android.content.Intent;
import android.os.Bundle;
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
    private String movieOverview;
    private String movieTitle;


    public DetailActivityFragment() {
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

                Movie movie = intent.getParcelableExtra("MovieParcel");

                movieTitle = movie.title;
                backdropImagePath = movie.backdrop_path;
                movieOverview = movie.overview;


                Log.d(LOG_TAG,"Backdrop Image url: "+backdropImagePath);
                Log.d(LOG_TAG,"Movie Overview : "+movieOverview);

                getActivity().setTitle(movieTitle);


               ImageView backdropPosterView = (ImageView) rootView.findViewById(R.id.backdropPoster_image);

//                backdropPosterView.setAdjustViewBounds(true);
           //     backdropPosterView.setPadding(0,0,0,0);
               Picasso.with(getContext()).load(backdropImagePath).placeholder(R.drawable.backdrop_placeholder).fit().into(backdropPosterView);

                TextView movieOverviewTextview = (TextView) rootView.findViewById(R.id.movie_overview_textView);
                movieOverviewTextview.setText(movieOverview);



            }




        }

        return  rootView;
    }
}
