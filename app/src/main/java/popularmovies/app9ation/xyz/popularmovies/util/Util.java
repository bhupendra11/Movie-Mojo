package popularmovies.app9ation.xyz.popularmovies.util;

import android.util.Log;

/**
 * Created by bhupendra on 25/12/15.
 */
public class Util {

    public static String getMonthYear(String date){
        Log.d("date",date);
        int monthNo = Integer.parseInt(date.substring(5,7));
        String year = date.substring(0,4);
        String month="";
        switch (monthNo){
            case  1:
                month ="Jan";
                break;
            case 2:
                month = "Feb";
                break;
            case 3:
                month ="March";
                break;
            case 4:
                month ="April";
                break;
            case 5:
                month = "May";
                break;
            case 6:
                month ="June";
                break;
            case  7:
                month ="July";
                break;
            case 8:
                month = "August";
                break;
            case 9:
                month ="Sept";
                break;
            case  10:
                month ="Oct";
                break;
            case 11:
                month = "Nov";
                break;
            case 12:
                month ="Dec";
                break;
            default:

        }

        String result = month +" "+year;
        return  result;
    }
}

