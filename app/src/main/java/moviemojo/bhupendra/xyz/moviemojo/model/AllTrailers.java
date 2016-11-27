package moviemojo.bhupendra.xyz.moviemojo.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Bhupendra Singh on 17/3/16.
 */
public class AllTrailers {

    @SerializedName("results")
    public List<MovieTrailer> trailerList;

    public List<MovieTrailer> getTrailerList() {
        return trailerList;
    }

    public void setTrailerList(List<MovieTrailer> trailerList){
        this.trailerList = trailerList;

    }


    public static class MovieTrailer{
        @SerializedName("id")
        private String mId;

        @SerializedName("key")
        private String mKey;

        @SerializedName("name")
        private String mTrailerTitle;

        @SerializedName("site")
        private String mSite;

        public String getId() {
            return mId;
        }

        public String getKey() {
            return mKey;
        }

        public String getTrailerTitle() {
            return mTrailerTitle;
        }

        public String getSite() {
            return mSite;
        }

        public static String getThumbnailUrl(MovieTrailer trailer){
            return String.format("http://img.youtube.com/vi/%1$s/0.jpg", trailer.getKey());

        }

        public static String getTrailerUrl(MovieTrailer trailer){
            return String.format("http://www.youtube.com/watch?v=%1$s", trailer.getKey());

        }


    }
}