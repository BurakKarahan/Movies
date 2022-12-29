package com.burakkarahan.movies.service

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.burakkarahan.movies.model.MovieModel
import com.burakkarahan.movies.utils.Constant
import com.burakkarahan.movies.utils.Constant.NETWORK_PAGE_SIZE
import com.velmurugan.paging3android.MoviePagingSource

class Repository constructor(private val retrofitService: RetrofitAPI) {

    fun getNowPlaying() = retrofitService.getNowPlayingMovies(Constant.API_KEY, Constant.LANGUAGE, Constant.SLIDER_PAGE)

    fun getAllMovies(): LiveData<PagingData<MovieModel>> {

        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = 2
            ),
            pagingSourceFactory = {
                MoviePagingSource(retrofitService)
            }
        , initialKey = 1
        ).liveData
    }

    fun getDetailMovies(id : Int) = retrofitService.getDetailMovies(id, Constant.API_KEY, Constant.LANGUAGE)

}