package com.tutorizal.app.popularmovie.utilities;

/**
 * Created by aaijal on 7/7/2017.
 */

public class MovieItem {
    private int id;
    private double averageRate;
    private String title, posterPath, overview, releaseDate;

    public MovieItem() { }

    public MovieItem(int id, double averageRate, String title, String posterPath, String overview, String releaseDate) {
        this.id = id;
        this.averageRate = averageRate;
        this.title = title;
        this.posterPath = posterPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getAverageRate() {
        return averageRate;
    }

    public void setAverageRate(double averageRate) {
        this.averageRate = averageRate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return "Movie Title : "+title;
    }
}
