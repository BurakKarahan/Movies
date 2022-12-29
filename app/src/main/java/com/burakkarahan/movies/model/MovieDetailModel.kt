package com.burakkarahan.movies.model

import com.google.gson.annotations.SerializedName

data class MovieDetailModel (

    @SerializedName("id")
    val movieId: String,

    @SerializedName("backdrop_path")
    val movieImge: String,

    @SerializedName("vote_average")
    val movieVoteAverage: String,

    @SerializedName("vote_count")
    val movieVote_count: String,

    @SerializedName("title")
    val movieTitle: String,

    @SerializedName("tagline")
    val movieTagline: String,

    @SerializedName("release_date")
    val movieReleaseDate: String,

    @SerializedName("overview")
    val movieOverview: String
)