# Popular-Movies
Popular Movies  is a movies discovery app. This is the second Project for Androidd developer Nanodegree.
The first project was a primitive version of the same app.

INSTALLATION:

Create an aaccount on https://www.themoviedb.org/  and request an api key

Use this api key in your gradle.properties file as:

MyTMDBApiKey= "YOUR_TMDB_API_KEY_HERE"

![App demo GIF] (https://cloud.githubusercontent.com/assets/6179888/12053298/95b2ba2e-af3d-11e5-8405-5b87bec5e643.gif)


App Specifications:

1. App uses TMDB Api to fetch the data.
2. The main layout shows recent popular movies.
3. There is toggle option to see "Popular Movies" , "Highest Rated Movies" and "Favorite Movies".
4. "Favorite Movies " are movies saved offline using Content Providers.
5. The detail view shows:
        1.Movie Synopsis
        2.Poster
        3.Rating
        4. Trailers (in horizontal scrollview)
        5. Reviews.
6. A button to mark a movie favorite is present in detail View.


Libraries used:

1. Picasso for handling download and caching of images.
2. Retrofit as Rest-Client for fetching data from TMDB Api.
3. Buttorknife ![ButterKnife] (http://jakewharton.github.io/butterknife/) for view binding.  



