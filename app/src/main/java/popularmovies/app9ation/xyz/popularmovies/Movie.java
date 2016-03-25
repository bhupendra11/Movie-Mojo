package popularmovies.app9ation.xyz.popularmovies;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Bhupendra Shekhawat on 14/12/15.
 */
public class Movie implements Parcelable{


    Long id;
    String poster;
    String overview;
    String title;
    String backdrop_path;
    String popularity;
    String vote_avg;
    String release_year;



    public Movie(Long id, String poster, String overview, String title, String backdrop_path, String popularity, String vote_avg,String release_year){
        this.id = id;
        this.poster = poster;
        this.overview =overview;
        this.title=title;
        this.backdrop_path = backdrop_path;
        this.popularity = popularity;
        this.vote_avg = vote_avg;
        this.release_year = release_year;
    }

    // get Movie object from Cursor
    public Movie(Cursor cursor) {
        this.id = cursor.getLong(PosterDisplayFragment.COL_MOVIE_ID);
        this.title = cursor.getString(PosterDisplayFragment.COL_TITLE);
        this.poster = cursor.getString(PosterDisplayFragment.COL_POSTER);
        this.backdrop_path = cursor.getString(PosterDisplayFragment.COL_BACKDROP);
        this.overview = cursor.getString(PosterDisplayFragment.COL_OVERVIEW);
        this.vote_avg = cursor.getString(PosterDisplayFragment.COL_RATING);
        this.release_year = cursor.getString(PosterDisplayFragment.COL_DATE);
    }

    private Movie(Parcel in){
        id = in.readLong();
        poster = in.readString();
        overview = in.readString();
        title =in.readString();
        backdrop_path =in.readString();
        popularity = in.readString() ;
        vote_avg = in.readString();
        release_year = in.readString();

    }

    // getters for all movie properties
    public Long getId() {
        return id;
    }

    public String getPoster() {
        return poster;
    }

    public String getOverview() {
        return overview;
    }

    public String getTitle() {
        return title;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public String getPopularity() {
        return popularity;
    }

    public String getVote_avg() {
        return vote_avg;
    }

    public String getRelease_year() {
        return release_year;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeLong(id);
        parcel.writeString(poster);
        parcel.writeString(overview);
        parcel.writeString(title);
        parcel.writeString(backdrop_path);
        parcel.writeString(popularity);
        parcel.writeString(vote_avg);
        parcel.writeString(release_year);

    }

    public static final Parcelable.Creator<Movie> CREATOR  = new Parcelable.Creator<Movie>()
    {

        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }
    };


}
