package com.example.benktesh.popularmovies.Model;
import java.util.List;

/**
 * Created by Benktesh on 5/4/18.
 */
public class MovieItem {
/* We need followoing attributes
    original title
    movie poster image thumbnail
    A plot synopsis (called overview in the api)
    user rating (called vote_average in the api)
    release date

    */

    public String originalTitle;
    public String overview;
    public String posterPath;
    public String releaseDate;
    public double voteAverage;

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(double voteAverage) {
        this.voteAverage = voteAverage;
    }

}


