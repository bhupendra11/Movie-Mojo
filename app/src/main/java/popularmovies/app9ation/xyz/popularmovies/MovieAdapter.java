package popularmovies.app9ation.xyz.popularmovies;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by  Bhupendra Shekhawat on 14/12/15.
 */
public class MovieAdapter extends ArrayAdapter<Movie>
    {
        //private static final String LOG_TAG = MovieAdapter.class.getSimpleName();

        public MovieAdapter(Activity context, List<Movie> moviePosters){

            super(context,0, moviePosters);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Movie movie = getItem(position);

            String posterUrl = movie.poster;


            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(
                        R.layout.poster_item, parent, false);
            }

            ImageView posterView = (ImageView) convertView.findViewById(R.id.moviePoster_image);

            posterView.setAdjustViewBounds(true);
            posterView.setPadding(0,0,0,0);
            Picasso.with(getContext()).load(posterUrl).fit().placeholder(R.drawable.poster_placeholder).into(posterView);

            TextView movieNameTextView = (TextView) convertView.findViewById(R.id.movie_poster_name_textview);
            movieNameTextView.setText(movie.getTitle());


            return convertView;
        }
    }
