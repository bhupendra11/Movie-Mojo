# Popular-Movies
Popular Movies  is a movies discovery app. This is the second Project for Androidd developer Nanodegree.
The first project was a primitive version of the same app.

INSTALLATION:

Create an aaccount on https://www.themoviedb.org/  and request an api key

Use this api key in your gradle.properties file as:

MyTMDBApiKey= "YOUR_TMDB_API_KEY_HERE"

##App Specifications:

1. App uses TMDB Api to fetch the data.
2. The main layout shows recent popular movies.
3. There is toggle option to see "Popular Movies" , "Highest Rated Movies" and "Favorite Movies".
4. "Favorite Movies " are movies saved offline using Content Providers.
5. The detail view shows:
        - Movie Synopsis
        -Poster
        -Rating
        - Trailers (in horizontal scrollview)
        - Reviews.
6. A button to mark a movie favorite is present in detail View.


##Libraries used:

1. [Picasso](http://square.github.io/picasso/) for handling download and caching of images.
2. [Retrofit](http://square.github.io/retrofit/) as Rest-Client for fetching data from TMDB Api.
3. [ButterKnife] (http://jakewharton.github.io/butterknife/)  for view binding.  

![App demo Gif](moviemojo_online-video-cutter.gif)

