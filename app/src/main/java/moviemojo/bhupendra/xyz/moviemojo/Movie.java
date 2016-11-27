package moviemojo.bhupendra.xyz.moviemojo;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import moviemojo.bhupendra.xyz.moviemojo.util.Util;

/**
 * Created by Bhupendra Shekhawat on 14/12/15.
 */
public class Movie implements Parcelable{


    @SerializedName("original_title")
    private String mTitle;

    @SerializedName("id")
    private long mMovieId;

    @SerializedName("release_date")
    private String mReleaseDate;

    @SerializedName("poster_path")
    private String mPosterPath;

    @SerializedName("backdrop_path")
    private  String mBackdropPath;

    @SerializedName("vote_average")
    private String mRating;

    @SerializedName("vote_count")
    private int mVoteCount;

    @SerializedName("overview")
    private String mOverview;

    @SerializedName("popularity")
    private String mPopularity;


    final String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/";
    final String POSTER_SIZE = "w185";
    final String BACKDROP_SIZE = "w500";


    String display_yearMonth;



    public Movie(Long id, String poster, String overview, String title, String backdrop_path, String popularity, String vote_avg,String release_year){
        this.mMovieId = id;
        this.mPosterPath = poster;
        this.mOverview =overview;
        this.mTitle=title;
        this.mBackdropPath = backdrop_path;
        this.mPopularity= popularity;
        this.mRating = vote_avg;
        this.mReleaseDate = release_year;


    }

    // get Movie object from Cursor
    public Movie(Cursor cursor) {
        this.mMovieId = cursor.getLong(PosterDisplayFragment.COL_MOVIE_ID);
        this.mTitle = cursor.getString(PosterDisplayFragment.COL_TITLE);
        this.mPosterPath = cursor.getString(PosterDisplayFragment.COL_POSTER);
        this.mBackdropPath = cursor.getString(PosterDisplayFragment.COL_BACKDROP);
        this.mOverview = cursor.getString(PosterDisplayFragment.COL_OVERVIEW);
        this.mRating = cursor.getString(PosterDisplayFragment.COL_RATING);
        this.mReleaseDate = cursor.getString(PosterDisplayFragment.COL_DATE);
    }

    protected Movie(Parcel in) {

        mMovieId = in.readLong();
        mTitle = in.readString();
        mPosterPath = in.readString();
        mBackdropPath = in.readString();
        mReleaseDate = in.readString();
        mRating = in.readString();
        mVoteCount = in.readInt();
        mOverview = in.readString();
        mPopularity = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeLong(mMovieId);
        dest.writeString(mTitle);
        dest.writeString(mPosterPath);
        dest.writeString(mBackdropPath);
        dest.writeString(mReleaseDate);
        dest.writeString(mRating);
        dest.writeInt(mVoteCount);
        dest.writeString(mOverview);
        dest.writeString(mPopularity);
    }


    // getters for all movie properties
    public String getTitle() {
        return mTitle;
    }

    public Long getMovieId() {
        return mMovieId;
    }

    public String getReleaseMonthYear() {

        display_yearMonth = Util.getMonthYear(mReleaseDate);

        return display_yearMonth;
    }

    public String getPosterPath() {
        return BASE_IMAGE_URL + POSTER_SIZE + mPosterPath;
    }

    public  String getBackdropPath(){
        return BASE_IMAGE_URL + BACKDROP_SIZE + mBackdropPath;
    }

    public String getRating() {
        return mRating;
    }

    public int getVoteCount() {
        return mVoteCount;
    }

    public String getOverview() {
        return mOverview;
    }

    public String getPopularity() {
        return mPopularity;
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
