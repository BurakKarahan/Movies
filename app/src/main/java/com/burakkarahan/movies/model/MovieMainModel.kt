package com.burakkarahan.movies.model

import com.google.gson.annotations.SerializedName

data class MovieMainModel (
    @SerializedName("page")
    val currentPage: String,

    @SerializedName("results")
    val movieList: List<MovieModel>,

    @SerializedName("total_pages")
    val totalPage: String,

    @SerializedName("total_results")
    val totalMovie: String
)