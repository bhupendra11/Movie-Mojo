package popularmovies.app9ation.xyz.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import popularmovies.app9ation.xyz.popularmovies.model.AllReviews;
import popularmovies.app9ation.xyz.popularmovies.model.AllReviews.MovieReview;

/**
 * Created by Bhupendra Singh on 17/3/16.
 */
public class ReviewAdapter extends ArrayAdapter<MovieReview> {

    private List<AllReviews.MovieReview> reviewList;


    public ReviewAdapter(Context context, List<MovieReview> movieList) {
        super(context,0 ,movieList);

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        MovieReview movieReview = getItem(position);

        View rowView = LayoutInflater.from(getContext()).inflate(R.layout.reviews_item,parent,false);
        TextView reviewAuthorTextView = (TextView) rowView.findViewById(R.id.review_author_textView);
        TextView reviewContentTextView = (TextView) rowView.findViewById(R.id.review_content_textView);


        reviewAuthorTextView.setText(movieReview.getAuthor());
        reviewContentTextView.setText(movieReview.getContent());



        return rowView;
    }
}
