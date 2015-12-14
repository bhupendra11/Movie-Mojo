package popularmovies.app9ation.xyz.popularmovies;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import java.util.Arrays;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    private MoviePosterAdapter moviePosterAdapter;

    MoviePoster[] moviePosters = {
            new MoviePoster(R.drawable.antman),
            new MoviePoster(R.drawable.intersteller),
            new MoviePoster(R.drawable.jurrasic),
            new MoviePoster(R.drawable.antman),
            new MoviePoster(R.drawable.intersteller),
            new MoviePoster(R.drawable.jurrasic),
            new MoviePoster(R.drawable.antman),
            new MoviePoster(R.drawable.intersteller),
            new MoviePoster(R.drawable.jurrasic),
            new MoviePoster(R.drawable.antman),
            new MoviePoster(R.drawable.intersteller),
            new MoviePoster(R.drawable.jurrasic),
            new MoviePoster(R.drawable.antman),
            new MoviePoster(R.drawable.intersteller),
            new MoviePoster(R.drawable.jurrasic),
            new MoviePoster(R.drawable.antman),
            new MoviePoster(R.drawable.intersteller),
            new MoviePoster(R.drawable.jurrasic),
            new MoviePoster(R.drawable.antman),
            new MoviePoster(R.drawable.intersteller),
            new MoviePoster(R.drawable.jurrasic)

    };


    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);


        moviePosterAdapter = new MoviePosterAdapter(getActivity() , Arrays.asList(moviePosters));

        GridView gridView  = (GridView) rootView.findViewById(R.id.posters_grid);
        gridView.setAdapter(moviePosterAdapter);

        return  rootView;
    }
}
