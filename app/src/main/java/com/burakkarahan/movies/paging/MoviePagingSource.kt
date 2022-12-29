package com.velmurugan.paging3android

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.burakkarahan.movies.model.MovieModel
import com.burakkarahan.movies.service.RetrofitAPI
import com.burakkarahan.movies.utils.Constant
import java.lang.Exception

class MoviePagingSource(private val apiService: RetrofitAPI): PagingSource<Int, MovieModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieModel> {

        return try {
            val position = params.key ?: 1
            val response = apiService.getTopRatedMovies(Constant.API_KEY,Constant.LANGUAGE,position)
            LoadResult.Page(data = response.body()!!.movieList, prevKey = if (position == 1) null else position - 1,
                nextKey = position + 1)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MovieModel>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

}
