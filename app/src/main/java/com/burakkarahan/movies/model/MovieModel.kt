package com.burakkarahan.movies.model

import com.google.gson.annotations.SerializedName

class MovieModel (
    @SerializedName("id")
    val movieId: String,

    @SerializedName("backdrop_path")
    val movieImagePath: String,

    @SerializedName("title")
    val movieTitle: String,

    @SerializedName("release_date")
    val movieReleaseDate: String,

    @SerializedName("overview")
    val movieOverview: String,

    @SerializedName("vote_average")
    val movieVoteAverage: String,

    @SerializedName("vote_count")
    val movieVoteCount: String,
)