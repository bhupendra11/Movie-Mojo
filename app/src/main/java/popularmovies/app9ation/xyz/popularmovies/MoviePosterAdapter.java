package popularmovies.app9ation.xyz.popularmovies;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by  Bhupendra Shekhawat on 14/12/15.
 */
public class MoviePosterAdapter extends ArrayAdapter<MoviePoster>
    {
        private static final String LOG_TAG = MoviePosterAdapter.class.getSimpleName();

        public MoviePosterAdapter(Activity context, List<MoviePoster> moviePosters){

            super(context,0, moviePosters);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MoviePoster moviePoster = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(
                        R.layout.poster_item, parent, false);
            }

            ImageView posterView = (ImageView) convertView.findViewById(R.id.moviePoster_image);
            posterView.setImageResource(moviePoster.image);


            return convertView;
        }
    }
