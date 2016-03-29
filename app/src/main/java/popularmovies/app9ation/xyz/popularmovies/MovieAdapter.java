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
    {  public MovieAdapter(Activity context, List<Movie> moviePosters){

            super(context,0, moviePosters);

        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Movie movie = getItem(position);
            ViewHolder viewHolder = new ViewHolder();

            String posterUrl = movie.poster;


            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(
                        R.layout.poster_item, parent, false);
                viewHolder = new ViewHolder();
                viewHolder.posterView  =(ImageView) convertView.findViewById(R.id.moviePoster_image);
                viewHolder.movieNameTextView = (TextView) convertView.findViewById(R.id.movie_poster_name_textview);
                convertView.setTag(viewHolder);
            }
            else{
                viewHolder = (ViewHolder) convertView.getTag();
            }


            viewHolder.posterView.setAdjustViewBounds(true);
            viewHolder.posterView.setPadding(0,0,0,0);
            Picasso.with(getContext()).load(posterUrl).fit().placeholder(R.drawable.poster_placeholder).into(viewHolder.posterView);

            viewHolder.movieNameTextView.setText(movie.getTitle());


            return convertView;
        }

        // Implement viewHolder inorder to avoid expensive findViewVyId calls by every list item

        static class ViewHolder{
            ImageView posterView;
            TextView movieNameTextView;

        }
    }
