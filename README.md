# Popular-Movies  (MovieMojo)
Popular Movies is a movies discovery app, optimized for tablets, to help users discover popular and highly rated movies on the web. It displays a scrolling grid of movie trailers, launches a details screen whenever a particular movie is selected, allows users to save favorites, play trailers, and read user reviews. This app utilizes core Android user interface components and fetches movie information using themoviedb.org web API.

## INSTALLATION:

Create an aaccount on https://www.themoviedb.org/  and request an api key.
Use this api key in your gradle.properties file as:

MyTMDBApiKey= "YOUR_TMDB_API_KEY_HERE"

## Features :

1. App uses TMDB Api to fetch the data.
2. The main layout shows recent popular movies.
3. There is toggle option to see "Popular Movies" , "Highest Rated Movies" and "Favorite Movies".
4. **Favorite Movies** are movies saved offline using Content Providers.
5. The detail view shows:
        a. Movie Synopsis
        b. Poster
        c. Rating
        d. Trailers (in horizontal scrollview)
        e. Reviews.
6. A button to mark a movie favorite is present in detail View.


## Libraries used :

1. [Picasso](http://square.github.io/picasso/) for handling download and caching of images.
2. [Retrofit](http://square.github.io/retrofit/) as Rest-Client for fetching data from TMDB Api.
3. [ButterKnife] (http://jakewharton.github.io/butterknife/)  for view binding.  

![App demo Gif](moviemojo_online-video-cutter.gif)

