package com.benktesh.popularmovies.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Benktesh on 5/4/18.
 */
public class MovieItem implements Parcelable {
    public String originalTitle;
    public String overview;
    public String posterPath;
    public String releaseDate;
    public double voteAverage;

    public MovieItem(String originalTitle, String overview,
                     String posterPath, String releaseDate, double voteAverage) {
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
    }

    public MovieItem() {
    }

    protected MovieItem(Parcel in) {
        originalTitle = in.readString();
        overview = in.readString();
        posterPath = in.readString();
        releaseDate = in.readString();
        voteAverage = in.readDouble();
    }

    public static final Creator<MovieItem> CREATOR = new Creator<MovieItem>() {
        @Override
        public MovieItem createFromParcel(Parcel in) {
            return new MovieItem(in);
        }

        @Override
        public MovieItem[] newArray(int size) {
            return new MovieItem[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(originalTitle);
        dest.writeString(overview);
        dest.writeString(posterPath);
        dest.writeString(releaseDate);
        dest.writeDouble(voteAverage);
    }
}


