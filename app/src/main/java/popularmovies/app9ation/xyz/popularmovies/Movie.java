package popularmovies.app9ation.xyz.popularmovies;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Bhupendra Shekhawat on 14/12/15.
 */
public class Movie implements Parcelable{
    String poster;
    String overview;
    String title;
    String backdrop_path;
    String popularity;
    String vote_avg;
    String release_year;


    public Movie(String poster, String overview, String title, String backdrop_path, String popularity, String vote_avg,String release_year){
        this.poster = poster;
        this.overview =overview;
        this.title=title;
        this.backdrop_path = backdrop_path;
        this.popularity = popularity;
        this.vote_avg = vote_avg;
        this.release_year = release_year;
    }

    private Movie(Parcel in){
        poster = in.readString();
        overview = in.readString();
        title =in.readString();
        backdrop_path =in.readString();
        popularity = in.readString() ;
        vote_avg = in.readString();
        release_year = in.readString();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {

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
