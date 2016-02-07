package com.portfolio.priyank.popularmovies;

/**
 * Created by Priyank on 05-Feb-16.
 */
public class Movie {
    private String backdropPath;
    private String posterPath;
    private String originalTitle;
    private int id;
    private String overview;
    private String releaseDate;
    private float voteAverage;

    public Movie(String backdropPath, String posterPath, String originalTitle, int id, String overview, String releaseDate, float voteAverage) {
        this.backdropPath = backdropPath;
        this.posterPath = posterPath;
        this.originalTitle = originalTitle;
        this.id = id;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }


}
