package popularmovies.app9ation.xyz.popularmovies.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by Bhupendra Singh on 24/3/16.
 *
 * Content Provider for serving the saved favourite movies data
 */


public class MovieProvider extends ContentProvider{

    // The Uri matcher used by this content provider
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private MovieDbHelper mOpenHelper ;
    static final int MOVIE = 100;


    private static UriMatcher buildUriMatcher() {

        final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = MovieContract.CONENT_AUTHORITY;

        uriMatcher.addURI(authority,MovieContract.PATH_MOVIE,MOVIE);

        return  sUriMatcher;

    }


    @Override
    public boolean onCreate() {
        mOpenHelper = new MovieDbHelper(getContext());

        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

            Cursor retCursor ;

        if(MOVIE == sUriMatcher.match(uri)){
            retCursor  = mOpenHelper.getReadableDatabase().query(
                    MovieContract.MovieEntry.TABLE_NAME, projection, selection, selectionArgs,
                    null, null, sortOrder);

        }
        else {
            throw new UnsupportedOperationException("Unknown uri: " + uri);
        }


        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;

    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch (match) {
            case MOVIE:
                return MovieContract.MovieEntry.CONTENT_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
    }




    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {


        final SQLiteDatabase db =  mOpenHelper.getWritableDatabase();
        Uri returnUri;

        switch (sUriMatcher.match(uri)){
            case MOVIE : {
                long _id = db.insert(MovieContract.MovieEntry.TABLE_NAME, null, values);
                if( _id >0){
                    returnUri  = MovieContract.MovieEntry.buildMovieUri(_id);
                }
                else{
                    throw new android.database.SQLException("Failed to insert row into "+uri);
                }
                break;

            }
            default:
                throw new UnsupportedOperationException("Unknown uri : "+uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        // this makes delete all rows return the number of rows deleted
        if (null == selection) selection = "1";

        switch (match){
            case MOVIE:
                rowsDeleted = db.delete(MovieContract.MovieEntry.TABLE_NAME , selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknows uri " + uri);
        }

        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;


    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        final SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        int rowsUpdated =0;

        final int match = sUriMatcher.match(uri);

        switch (match){
            case MOVIE:
                rowsUpdated = db.update(MovieContract.MovieEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Failed to update row "+rowsUpdated);

        }

        if(rowsUpdated!=0 ){
            getContext().getContentResolver().notifyChange(uri,null);
        }

        // Student: return the actual rows updated
        return rowsUpdated;

    }
}
