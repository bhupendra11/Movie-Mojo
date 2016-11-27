package moviemojo.bhupendra.xyz.moviemojo.util;

/**
 * Created by Bhupendra Singh on 3/18/2016.
 */

// Wrapper log class to display long string as output data

public class Log {

    public static void d(String TAG, String message) {
        int maxLogSize = 2000;
        for(int i = 0; i <= message.length() / maxLogSize; i++) {
            int start = i * maxLogSize;
            int end = (i+1) * maxLogSize;
            end = end > message.length() ? message.length() : end;
            android.util.Log.d(TAG, message.substring(start, end));
        }
    }

}