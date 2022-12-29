package com.burakkarahan.movies.service

import com.burakkarahan.movies.model.MovieDetailModel
import com.burakkarahan.movies.model.MovieMainModel
import com.burakkarahan.movies.utils.Constant
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitAPI {

    @GET("movie/now_playing")
    fun getNowPlayingMovies(
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ) : Call<MovieMainModel>

    @GET("movie/upcoming")
    suspend fun getTopRatedMovies(
        @Query("api_key") api_key: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): Response<MovieMainModel>

    @GET("movie/{id}")
    fun getDetailMovies(
        @Path("id") id: Int,
        @Query("api_key") api_key: String,
        @Query("language") language: String,
    ) : Call<MovieDetailModel>

    companion object {
        var retrofitService: RetrofitAPI? = null
        fun getInstance() : RetrofitAPI {
            if (retrofitService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                retrofitService = retrofit.create(RetrofitAPI::class.java)
            }
            return retrofitService!!
        }
    }
}