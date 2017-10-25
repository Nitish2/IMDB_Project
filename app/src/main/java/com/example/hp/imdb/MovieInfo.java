package com.example.hp.imdb;

public class MovieInfo {

    private int movie_id;
    private int favorite;
    private int watchlist;

    public MovieInfo() {

    }

    public MovieInfo(int movie_id, int favorite, int watchlist) {
        this.movie_id = movie_id;
        this.favorite = favorite;
        this.watchlist = watchlist;
    }


    public int getMovie_id() {
        return movie_id;
    }

    public void setMovie_id(int movie_id) {
        this.movie_id = movie_id;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public int getWatchlist() {
        return watchlist;
    }

    public void setWatchlist(int watchlist) {
        this.watchlist = watchlist;
    }

}
